<?php
/// Global Variables Section
global $pdo;

/// Imports Section
include '../config.php';

/// Code Section
// Prepare Update Statement
$stmt = $pdo->prepare('UPDATE books SET lent_to=?, lent_date=CURDATE() WHERE id=?');

// Execute Statement with POST Variables
$stmt->execute([$_POST['lent_to'], $_POST['id']]);

// Return status as JSON
echo json_encode(['status' => 'ok']);