<?php
require("conn.php");

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON data from the request
    $json_data = file_get_contents("php://input");
    // Decode JSON data
    $data = json_decode($json_data, true);

    // Check if JSON data is valid
    if ($data !== null) {
        // Retrieve data from JSON
        $field1 = $data['field1'];
        $field2 = $data['field2'];
        $field3 = $data['field3'];
        $field4 = $data['field4'];
        $field5 = $data['field5'];
        $field6 = $data['field6'];
        $profile_img = $data['profile'];

        // Query to fetch old image path
        $q1 = "SELECT img FROM adddoctor WHERE did='$field4'";
        $result1 = mysqli_query($conn, $q1);
        $oldImagePath = '';
        // Check if old image path exists
        if ($row = mysqli_fetch_assoc($result1)) {
            $oldImagePath = $row['img'];
        }
        
        // Generate new image path
        $imagePath = 'doctor_images/' . uniqid() . '.jpg';

        // Save the new image
        if (file_put_contents($imagePath, base64_decode($profile_img))) {
            // If old image exists, delete it
            if (!empty($oldImagePath) && file_exists($oldImagePath)) {
                unlink($oldImagePath);
            }

            // Update database with new data
            $sql = "UPDATE adddoctor SET name ='$field1', phno = '$field2', pass = '$field3', gender = '$field5', speciality = '$field6', img = '$imagePath' WHERE did = '$field4' ";
            $stmt=mysqli_query($conn, $sql);
            // Send success response
            echo json_encode(array("status"=> "success","message"=> "Data updated successfully"));
        } else {
            // Send error response if failed to save image
            $response = array('message' => 'Failed to save profile image');
            echo json_encode($response);
        }
    } else {
        // Send error response if JSON data is invalid
        $response = array('message' => 'Invalid JSON data');
        echo json_encode($response);
    }
} else {
    // Send error response if request method is not POST
    $response = array('message' => 'Invalid request method');
    echo json_encode($response);
}
?>