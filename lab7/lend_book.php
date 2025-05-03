<?php include 'components/header.php'; ?>

    <h1 id="lend-title">Lend Book</h1>
    <form id="lend-book-form" style="display:none;">
        <input type="hidden" name="id" id="book-id">
        <label>Borrower Name:</label>
        <input type="text" minlength="3" maxlength="100" name="lent_to" required>
        <button type="submit">Lend</button>
    </form>
    <p id="error-msg" style="color:red;"></p>
    <script>
        const bookId = new URLSearchParams(window.location.search).get('id');
        if (!bookId) window.location = 'index.php';

        $.get(`api/get_book.php?id=${bookId}`, function(response) {
            const book = JSON.parse(response);
            if (book.error) {
                $('#error-msg').text(book.error);
            } else {
                $('#lend-title').text(`Lend: ${book.title}`);
                $('#book-id').val(book.id);
                $('#lend-book-form').show();

                // Book already lent: disable form submission
                if (book['lent_to']) {
                    $('#lend-book-form').prepend(`<p style="color:red;">This book is already lent to ${book.lent_to}.</p>`);
                    $('#lend-book-form button[type="submit"]').prop('disabled', true);
                }
            }
        });
    </script>

<?php include 'components/footer.php'; ?>