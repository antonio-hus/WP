<?php

/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Prepare Update Statement
$stmt = $pdo->prepare('UPDATE books SET title=?, author=?, pages=?, genre=? WHERE id=?');

// Execute with Form POST Variables
$stmt->execute([$_POST['title'], $_POST['author'], $_POST['pages'], $_POST['genre'], $_POST['id']]);

// Return status as JSON
echo json_encode(['status' => 'ok']);