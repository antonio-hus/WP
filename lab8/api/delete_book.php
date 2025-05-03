<?php

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

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