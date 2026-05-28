<?php
// Include the database connection file
include 'conn.php';

// Fetch patient data based on the received ID
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id"];

    $sql = "SELECT * FROM pdetails WHERE pid = '$id'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();

        // Return data in JSON format
        $response = array(
            "tx1" => $row["pid"],
            "tx2" => $row["name"],
            "tx3" => $row["phno"],
            "tx4" => $row["age"],
            "tx5" => $row["gender"],
            "tx6" => $row["total"],
            "tx7" => $row["atype"],
            "img1" => $row["img"]
        );

        echo json_encode($response);
    } else {
        // No patient found with the given ID
        echo "Patient not found";
    }
} else {
    // Invalid request method
    echo "Invalid request method";
}

// Close the database connection
$conn->close();
?>
