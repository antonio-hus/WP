<?php
/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Check for id in GET Variables
$id = $_GET['id'] ?? null;
if (!$id) {
    echo json_encode(['error' => 'Book ID is required']);
    exit;
}

// Prepare Select Statement
$stmt = $pdo->prepare('SELECT * FROM books WHERE id = ?');

// Execute with GET Variables
$stmt->execute([$id]);

// Get Book from Results
$book = $stmt->fetch();

// Return Book Information as JSON
if ($book) {
    echo json_encode($book);
} else {
    echo json_encode(['error' => 'Book not found']);
}
