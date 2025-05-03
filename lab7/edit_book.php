<?php include 'components/header.php'; ?>

    <h1>Edit Book</h1>
    <div id="edit-container" style="display:none;">
        <form id="edit-book-form">
            <input type="hidden" name="id" id="book-id">
            <label>Title:</label>
            <input type="text" minlength="3" maxlength="255" name="title" id="book-title" required>

            <label>Author:</label>
            <input type="text" minlength="3" maxlength="255" name="author" id="book-author" required>

            <label>Pages:</label>
            <input type="number" name="pages" id="book-pages" required>

            <label>Genre:</label>
            <input type="text" minlength="3" maxlength="100" name="genre" id="book-genre" required>

            <button type="submit">Save</button>
        </form>
    </div>
    <p id="error-msg" style="color: red;"></p>
    <script>
        const bookId = new URLSearchParams(window.location.search).get('id');
        if (!bookId) {
            window.location = 'index.php';
        }

        $.get(`api/get_book.php?id=${bookId}`, function(response) {
            const book = JSON.parse(response);
            if (book.error) {
                $('#error-msg').text(book.error);
            } else {
                $('#book-id').val(book.id);
                $('#book-title').val(book.title);
                $('#book-author').val(book.author);
                $('#book-pages').val(book.pages);
                $('#book-genre').val(book.genre);
                $('#edit-container').show();
            }
        });
    </script>

<?php include 'components/footer.php'; ?>