using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using PersonalLibrary.Models;

namespace PersonalLibrary.Data
{
    public class LibraryContext : DbContext
    {
        public LibraryContext(DbContextOptions<LibraryContext> options) : base(options) {}

        public DbSet<BookViewModel> Books { get; set; }
        public DbSet<UserViewModel> Users { get; set; }
    }
}