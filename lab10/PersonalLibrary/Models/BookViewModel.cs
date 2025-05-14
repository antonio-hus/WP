using System.ComponentModel.DataAnnotations;

namespace PersonalLibrary.Models;

public class BookViewModel
{
    public int Id { get; set; }
    
    [Required]
    [StringLength(255, MinimumLength = 3)]
    public string Title { get; set; }
    
    [Required]
    [StringLength(255, MinimumLength = 3)]
    public string Author { get; set; }
    
    [Required]
    [Range(1, int.MaxValue)]
    public int Pages { get; set; }
    
    [Required]
    [StringLength(100, MinimumLength = 3)]
    public string Genre { get; set; }

    // Lending info
    public string? LentTo { get; set; }
    public DateTime? LentDate { get; set; }
}