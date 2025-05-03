<?php include 'components/header.php'; ?>

    <h1>Browse Books</h1>
    <form id="filter-form">
        <label for="filter-genre">Genre:</label>
        <input type="text" id="filter-genre" name="genre" placeholder="Enter genre...">
        <button type="submit">Filter</button>
    </form>
    <p id="current-filter"></p>
    <table id="books-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Pages</th>
            <th>Genre</th>
            <th>Lent To</th>
            <th>Lent Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>

<?php include 'components/footer.php'; ?>