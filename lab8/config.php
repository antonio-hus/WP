<?php

// Database Connection Variables
$host = 'localhost';
$db   = 'personal_library';
$user = 'library_user';
$pass = 'strong_password';
$charset = 'utf8mb4';
$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION, // Set error mode to throw exceptions on errors
    PDO::ATTR_EMULATE_PREPARES   => false, // Disable emulation of prepared statements to prevent SQL injections
];

// Database Connection using PDOs
try {
    $pdo = new PDO($dsn, $user, $pass, $options);
} catch (PDOException $e) {
    throw new PDOException($e->getMessage(), (int)$e->getCode());
}
