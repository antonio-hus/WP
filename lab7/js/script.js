$(document).ready(function() {

    // Variables Section
    let lastGenre = '';

    // Code Section
    function loadBooks(genre = '') {

        // Fetch books from the api with the specified genre
        $.get('api/get_books.php', { genre: genre }, function(data) {

            console.log(data)

            // Get books from the JSON response
            const books = JSON.parse(data);

            // Create table rows using the received books
            let rows = '';
            books.forEach(book => {
                rows += `<tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.pages}</td>
                    <td>${book.genre}</td>
                    <td>${book.lent_to || ''}</td>
                    <td>${book.lent_date || ''}</td>
                    <td>
                        <a href="edit_book.php?id=${book.id}">Edit</a> |
                        <a href="delete_book.php?id=${book.id}">Delete</a> |
                        <a href="lend_book.php?id=${book.id}">Lend</a>
                    </td>
                </tr>`;
            });

            // Add rows to the table's body
            $('#books-table tbody').html(rows);

            // Update last queried genre
            lastGenre = genre;
            $('#current-filter').text(genre ? `Filtered by genre: ${genre}` : 'Showing all books');
        });
    }

    // Initial load
    loadBooks();

    // API Calls
    // Browsing Page
    // Filter form for loading books by genre
    $('#filter-form').on('submit', function(e) {
        e.preventDefault();
        const genre = $('#filter-genre').val();
        loadBooks(genre);
    });

    // Add book Page
    $('#add-book-form').on('submit', function(e) {
        e.preventDefault();
        $.post('api/add_book.php', $(this).serialize(), function(response) {
            const res = JSON.parse(response);
            if (res.status === 'ok') window.location = 'index.php';
            else alert('Error adding book');
        });
    });

    // Delete book Page
    // Cancel
    $('#cancel-delete').on('click', function() {
        window.location = 'index.php';
    });

    // Confirm delete
    $('#confirm-delete').on('click', function() {
        $.post('api/delete_book.php', { id: bookId }, function(resp) {
            const res = JSON.parse(resp);
            if (res.status === 'ok') {
                window.location = 'index.php';
            } else {
                alert('Error deleting book');
            }
        });
    });

    // Edit Book Page
    $('#edit-book-form').on('submit', function(e) {
        e.preventDefault();
        $.post('api/edit_book.php', $(this).serialize(), function(response) {
            const res = JSON.parse(response);
            if (res.status === 'ok') window.location = 'index.php';
            else alert('Error updating book');
        });
    });

    // Lend Book Page
    $('#lend-book-form').on('submit', function(e) {
        $.post('api/lend_book.php', $(this).serialize(), function(response) {
            const res = JSON.parse(response);
            if (res.status === 'ok') window.location = 'index.php';
            else alert('Error lending');
        });
    });
});