<?php include 'components/header.php'; ?>

<h1>Delete Book</h1>
<div id="delete-container" style="display:none;">
    <p>Are you sure you want to delete: <strong id="book-title"></strong>?</p>
    <button id="confirm-delete">Delete</button>
    <button id="cancel-delete">Cancel</button>
</div>
<p id="error-msg" style="color:red;"></p>
<script>
    const bookId = new URLSearchParams(window.location.search).get('id');
    if (!bookId) {
        window.location = 'index.php';
    }

    // Fetch book details
    $.get(`api/get_book.php?id=${bookId}`, function(response) {
        const book = JSON.parse(response);
        if (book.error) {
            $('#error-msg').text(book.error);
        } else {
            $('#book-title').text(book.title);
            $('#delete-container').show();
        }
    });
</script>

<?php include 'components/footer.php'; ?>