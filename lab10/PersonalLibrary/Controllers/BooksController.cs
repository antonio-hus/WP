using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using PersonalLibrary.Data;
using PersonalLibrary.Models;

namespace PersonalLibrary.Controllers
{
    public class BooksController : Controller
    {
        private readonly LibraryContext _context;
        public BooksController(LibraryContext context)
        {
            _context = context;
        }
        
        public async Task<IActionResult> Index(string genre)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            // If a genre filter was passed, apply it
            var query = _context.Books.AsQueryable();
            if (!string.IsNullOrWhiteSpace(genre))
            {
                query = query.Where(b => b.Genre.Contains(genre));
                ViewData["LastGenreFilter"] = genre;
            }
            else
            {
                ViewData["LastGenreFilter"] = "";
            }

            var list = await query.ToListAsync();
            return View(list);
        }

        public IActionResult Create()
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Create(BookViewModel vm)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            if (!ModelState.IsValid)
                return View(vm);

            var book = new BookViewModel
            {
                Title = vm.Title,
                Author = vm.Author,
                Pages = vm.Pages,
                Genre = vm.Genre,
                LentTo = vm.LentTo,
                LentDate = vm.LentDate
            };

            _context.Books.Add(book);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> Edit(int id)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            var entity = await _context.Books.FindAsync(id);
            if (entity == null) return NotFound();

            var vm = new BookViewModel
            {
                Id = entity.Id,
                Title = entity.Title,
                Author = entity.Author,
                Pages = entity.Pages,
                Genre = entity.Genre,
                LentTo = entity.LentTo,
                LentDate = entity.LentDate
            };
            return View(vm);
        }

        [HttpPost]
        public async Task<IActionResult> Edit(int id, BookViewModel vm)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");
            if (id != vm.Id) return BadRequest();
            if (!ModelState.IsValid) return View(vm);

            var entity = await _context.Books.FindAsync(id);
            if (entity == null) return NotFound();

            entity.Title = vm.Title;
            entity.Author = vm.Author;
            entity.Pages = vm.Pages;
            entity.Genre = vm.Genre;
            entity.LentTo = vm.LentTo;
            entity.LentDate = vm.LentDate;

            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> Delete(int id)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            var book = await _context.Books.FindAsync(id);
            if (book == null) return NotFound();
            return View(book);
        }

        [HttpPost, ActionName("Delete")]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            var book = await _context.Books.FindAsync(id);
            if (book != null)
            {
                _context.Books.Remove(book);
                await _context.SaveChangesAsync();
            }
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> Lend(int id)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            var book = await _context.Books.FindAsync(id);
            if (book == null) return NotFound();
            return View(book);
        }

        [HttpPost]
        public async Task<IActionResult> Lend(int id, string lentTo)
        {
            if (HttpContext.Session.GetString("Username") == null)
                return RedirectToAction("Login", "Auth");

            var book = await _context.Books.FindAsync(id);
            if (book == null) return NotFound();

            book.LentTo = lentTo;
            book.LentDate = DateTime.Now;
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }
    }
}
