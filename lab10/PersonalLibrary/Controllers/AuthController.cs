using Microsoft.AspNetCore.Mvc;
using PersonalLibrary.Data;
using PersonalLibrary.Models;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace PersonalLibrary.Controllers
{
    public class AuthController : Controller
    {
        private readonly LibraryContext _context;
        public AuthController(LibraryContext context) => _context = context;
        
        public IActionResult Login() => View();

        [HttpPost]
        public IActionResult Login(LoginViewModel model)
        {
            if (!ModelState.IsValid)
                return View(model);

            var hash = ComputeSha256Hash(model.Password);
            var user = _context.Users
                .FirstOrDefault(u => u.Username == model.Username && u.PasswordHash == hash);

            if (user == null)
            {
                ModelState.AddModelError("", "Invalid credentials");
                return View(model);
            }

            HttpContext.Session.SetString("Username", user.Username);
            return RedirectToAction("Index", "Books");
        }
        
        public IActionResult Register() => View();

        [HttpPost]
        public IActionResult Register(RegisterViewModel model)
        {
            if (!ModelState.IsValid)
                return View(model);

            if (_context.Users.Any(u => u.Username == model.Username))
            {
                ModelState.AddModelError("Username", "Username already exists");
                return View(model);
            }

            var user = new UserViewModel
            {
                Username = model.Username,
                PasswordHash = ComputeSha256Hash(model.Password)
            };
            _context.Users.Add(user);
            _context.SaveChanges();

            HttpContext.Session.SetString("Username", user.Username);
            return RedirectToAction("Index", "Books");
        }
        
        public IActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Login");
        }

        private static string ComputeSha256Hash(string raw)
        {
            using var sha = SHA256.Create();
            var bytes = sha.ComputeHash(Encoding.UTF8.GetBytes(raw));
            var sb = new StringBuilder();
            foreach (var b in bytes) sb.Append(b.ToString("x2"));
            return sb.ToString();
        }
    }
}