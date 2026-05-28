<?php
require "conn.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $pid = $_POST['pid'];
    $phno = $_POST['phno'];

    // Secure SQL query using prepared statements (optional but recommended)
    $stmt = $conn->prepare("SELECT * FROM patientlogin WHERE pid = ? AND phno = ?");
    $stmt->bind_param("ss", $pid, $phno);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result && $result->num_rows > 0) {
        // Fetch full row (optional if you want more data like name, etc.)
        $row = $result->fetch_assoc();

        echo json_encode([
            'status' => 'success',
            'message' => 'Login successful',
            'pid' => $row['pid'],  // returning pid explicitly
            'phno' => $row['phno'] // optional, if you need it on dashboard
        ]);
    } else {
        echo json_encode([
            'status' => 'failure',
            'message' => 'Invalid PID or Phone Number'
        ]);
    }

    $stmt->close();
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'Invalid request method'
    ]);
}

$conn->close();
?>
