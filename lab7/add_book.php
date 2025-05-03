<?php include 'components/header.php'; ?>

    <h1>Add Book</h1>
    <form id="add-book-form">
        <label>Title:</label>
        <input type="text" minlength="3" maxlength="255" name="title" required>

        <label>Author:</label>
        <input type="text" minlength="3" maxlength="255" name="author" required>

        <label>Pages:</label>
        <input type="number" name="pages" required>

        <label>Genre:</label>
        <input type="text" minlength="3" maxlength="100" name="genre" required>

        <button type="submit">Add</button>
    </form>

<?php include 'components/footer.php'; ?>