using System.ComponentModel.DataAnnotations;

namespace PersonalLibrary.Models
{
    public class UserViewModel
    {
        public int Id { get; set; }

        [Required, StringLength(100)]
        public string Username { get; set; }

        [Required]
        public string PasswordHash { get; set; }
    }
}