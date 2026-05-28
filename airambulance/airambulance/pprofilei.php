<?php
// Include the database connection file
include 'conn.php';

// Initialize response array
$response = array();

// Fetch patient data based on the received ID
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST["id"];

    $sql = "SELECT * FROM pdetails WHERE pid = '$id'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();

        // Populate response with patient data
        $response["status"] = "success";
        $response["message"] = "Patient data retrieved successfully";
        $response["data"] = array(
            "id" => $row["pid"],
            "name" => $row["name"],
            "phno" => $row["phno"],
            "age" => $row["age"],
            "gender" => $row["gender"],
            "totalscore" => $row["total"],
            "atype" => $row["atype"],
            "img" => $row["img"]
        );
    } else {
        // No patient found with the given ID
        $response["status"] = "error";
        $response["message"] = "Patient not found";
        $response["data"] = null;
    }
} else {
    // Invalid request method
    $response["status"] = "error";
    $response["message"] = "Invalid request method";
    $response["data"] = null;
}

// Close the database connection
$conn->close();

// Return response as JSON
echo json_encode($response);
?>
