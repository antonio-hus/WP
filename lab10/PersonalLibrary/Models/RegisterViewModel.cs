using System.ComponentModel.DataAnnotations;

namespace PersonalLibrary.Models
{
    public class RegisterViewModel
    {
        [Required, StringLength(100)]
        public string Username { get; set; }

        [Required, DataType(DataType.Password), StringLength(100, MinimumLength = 6)]
        public string Password { get; set; }

        [Required, DataType(DataType.Password), Compare("Password")]
        public string ConfirmPassword { get; set; }
    }
}