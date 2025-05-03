<?php

/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Prepare Delete Statement
$stmt = $pdo->prepare('DELETE FROM books WHERE id=?');

// Execute with Form POST Variables
$stmt->execute([$_POST['id']]);

// Return status as JSON
echo json_encode(['status' => 'ok']);