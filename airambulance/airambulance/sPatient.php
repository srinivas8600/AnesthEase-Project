<?php
// Include your database connection file
require "conn.php";

// Check if the necessary parameters are provided
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON input
    $jsonInput = file_get_contents("php://input");

    // Decode JSON data into an associative array
    $postData = json_decode($jsonInput, true);

    // Check if JSON decoding was successful
    if ($postData !== null) {
        // Sanitize input data to prevent SQL injection
        $id = $postData['id'];
        $name = $postData['name'];
        $gender = $postData['gender'];
        $age = $postData['age'];
        $height = $postData['height'];
        $weight = $postData['weight'];
        $phno = $postData['phno'];
        $bmi = $postData['bmi'];
        $dob = $postData['dob'];

        // Directory to save uploaded profile pictures
        $uploadDirectory = "img/" . $id . '.jpg';

        // Get the uploaded file details
        $profilePicBase64 = $postData['profile_pic'];
        $profilePicBinary = base64_decode($profilePicBase64);

        // Save the image
        if (file_put_contents($uploadDirectory, $profilePicBinary)) {
            // Image saved, now prepare profile picture path
            $profilePicPath = $uploadDirectory;

            // Insert into pdetails
            $query1 = "INSERT INTO pdetails (pid, name, phno, age, gender, height, weight, bmi, date, img)
                       VALUES ('$id', '$name', '$phno', '$age', '$gender', '$height', '$weight', '$bmi', '$dob', '$profilePicPath')";

            // Insert into patientlogin
            $query2 = "INSERT INTO patientlogin (pid, phno) VALUES ('$id', '$phno')";

            // Execute both queries
            if (mysqli_query($conn, $query1)) {
                if (mysqli_query($conn, $query2)) {
                    echo json_encode(['status' => 'success']);
                } else {
                    echo json_encode(['status' => 'partial_success', 'message' => 'pdetails inserted but patientlogin failed: ' . mysqli_error($conn)]);
                }
            } else {
                echo json_encode(['status' => 'error', 'message' => 'Failed to insert into pdetails: ' . mysqli_error($conn)]);
            }
        } else {
            echo json_encode(['status' => 'error', 'message' => 'Failed to save the image']);
        }
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Invalid JSON format']);    
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Invalid request method']);
}

// Close the database connection
mysqli_close($conn);
?>
