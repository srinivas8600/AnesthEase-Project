<?php
require("conn.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST["did"]) && isset($_FILES["profile_photo"])) {
        $did = $_POST["did"];
        $fileName = $did . ".jpg"; // Constructing the file name using $did
        $folder = "doctor_images/" . $fileName; // Directory where the image will be stored

        // Debugging: Print received parameters
        echo "Received parameters: ";
        print_r($_POST);

        // Check if a record with the given doctor ID already exists
        $check_sql = "SELECT * FROM adddoctor WHERE did = '$did'";
        $check_result = $conn->query($check_sql);

        if ($check_result && $check_result->num_rows > 0) {
            // Update the existing record
            $update_sql = "UPDATE adddoctor SET img = '$folder' WHERE did = '$did'";
            
            if ($conn->query($update_sql) === TRUE) {
                // Move the uploaded file to the specified directory
                if (move_uploaded_file($_FILES["profile_photo"]["tmp_name"], $folder)) {
                    $response['status'] = 'success';
                    $response['message'] = 'Profile image uploaded successfully.';
                } else {
                    $response['status'] = 'error';
                    $response['message'] = 'Failed to move the uploaded file.';
                }
            } else {
                $response['status'] = 'error';
                $response['message'] = 'Data not updated. Error: ' . $conn->error;
            }
        } else {
            // Record doesn't exist, handle accordingly
            $response['status'] = 'error';
            $response['message'] = 'Record with the provided doctor ID does not exist.';
        }
    } else {
        $response['status'] = 'error';
        $response['message'] = 'Invalid request. Doctor ID or profile photo not provided.';
    }
} else {
    $response['status'] = 'error';
    $response['message'] = 'Invalid request method.';
}

header('Content-Type: application/json');
echo json_encode($response);

// Debugging: Print out file upload errors
if ($_FILES["profile_photo"]["error"] !== UPLOAD_ERR_OK) {
    // Print out the error code
    echo "File upload failed with error code: " . $_FILES["profile_photo"]["error"];
    // Terminate the script or handle the error as needed
    exit();
}
?>
