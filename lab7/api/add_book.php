<?php

/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Prepare Insert Statement
$stmt = $pdo->prepare('INSERT INTO books (title, author, pages, genre) VALUES (?, ?, ?, ?)');

// Execute with Form POST Variables
$stmt->execute([$_POST['title'], $_POST['author'], $_POST['pages'], $_POST['genre']]);

// Return status and id as JSON
echo json_encode(['status' => 'ok', 'id' => $pdo->lastInsertId()]);