<?php
// Include the database connection file (replace with your actual database connection code)
require("conn.php");

// Check if the request is a POST request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON data from the request body
    $json_data = file_get_contents("php://input");

    // Parse the JSON data into an associative array
    $data = json_decode($json_data, true);

    // Check if the JSON data was successfully parsed
    if ($data !== null) {
        // Extract data from the JSON
        $name = $data['name'];
        $phno = $data['phno'];
        $did = $data['did']; // assuming this is the primary key
        $pass = $data['pass'];
        $gender = $data['gender'];
        $spec = $data['spec'];

        // Prepare the SQL statement
        $sql = "UPDATE adddoctor SET name=?, phno=?, pass=?, gender=?, speciality=? WHERE did=?";
        $stmt = $conn->prepare($sql);

        if ($stmt) {
            // Bind parameters
            $stmt->bind_param("ssssss", $name, $phno, $pass, $gender, $spec, $did);

            // Execute the statement
            if ($stmt->execute()) {
                // Data updated successfully
                echo json_encode(array("status" => "success", "message" => "Data updated successfully"));
            } else {
                // Error occurred during the update
                echo json_encode(array("status" => "error", "message" => "Failed to update data"));
            }

            // Close the statement
            $stmt->close();
        } else {
            // Error preparing SQL statement
            echo json_encode(array("status" => "error", "message" => "Error preparing SQL statement"));
        }
    } else {
        // Invalid JSON data
        echo json_encode(array('status' => 'error', 'message' => 'Invalid JSON data'));
    }
} else {
    // Invalid request method
    echo json_encode(array('status' => 'error', 'message' => 'Invalid request method'));
}

// Close the database connection
$conn->close();
?>
