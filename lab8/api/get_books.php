<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Get genre from GET Variables if available
$genre = $_GET['genre'] ?? '';

// Return books by genre or all books
if ($genre) {

    // Prepare Select Statement
    $stmt = $pdo->prepare('SELECT * FROM books WHERE genre LIKE ?');

    // Execute Select Statement with genre GET Variable
    $stmt->execute(["%$genre%"]);
} else {

    // Prepare & Execute Select Statement
    $stmt = $pdo->query('SELECT * FROM books');
}

// Get books from return of the statement
$books = $stmt->fetchAll();

// Return books as JSON
echo json_encode($books);