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
        $did = $postData['did'];
        $name = $postData['name'];
        $gender = $postData['gender'];
        $password = $postData['password'];
        $speciality = $postData['speciality'];
        $phno = $postData['phno'];
       
        // Directory to save uploaded profile pictures
        $uploadDirectory = "doctor_images/" . $did . '.jpg';

        // Get the uploaded file details
        $profilePicBase64 = $postData['profile_pic'];
        $profilePicBinary = base64_decode($profilePicBase64);

        // Save the image
        if (file_put_contents($uploadDirectory, $profilePicBinary)) {
            // Update the database with the new information including the profile picture path
            $profilePicPath = $uploadDirectory;
            $query = "INSERT INTO adddoctor (did,name,phno,pass,gender,speciality, img)
                      VALUES ('$did', '$name', '$phno', '$password', '$gender', '$speciality', '$profilePicPath')";

            if (mysqli_query($conn, $query)) {
                echo json_encode(['status' => 'success']);
            } else {
                echo json_encode(['status' => 'error', 'message' => mysqli_error($conn)]);
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
