<?php
// Include the database connection file
include 'conn.php';

// Fetch patient data based on the received ID
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id"];

    $sql = "SELECT * FROM adddoctor WHERE did = '$id'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();

        // Return data in JSON format
        $response = array(
            "did" => $row["did"],
            "name" => $row["name"],
            "phno" => $row["phno"],
            "pass" => $row["pass"],
            "gender" => $row["gender"],
            "speciality" => $row["speciality"], 
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
