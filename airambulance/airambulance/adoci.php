<?php
// Include your database connection file
require "conn.php";
date_default_timezone_set('Asia/Kolkata');

// Check if all necessary parameters are provided via POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Sanitize input data to prevent SQL injection
    $did = $_POST['did'];
    $password = $_POST['password'];
    $name = $_POST['name'];
    $pno = $_POST['pno'];
    $spe = $_POST['spe'];
    $gender = $_POST['gender'];

    // Function to upload image and return the paths
    function uploadImages($fieldName) {
        global $targetDirectory;
        $result = [];
        $targetDirectory = "doctor_images/";

        // Check if the field name exists and if it's an array
        if (isset($_FILES[$fieldName]) && is_array($_FILES[$fieldName]["name"])) {
            foreach ($_FILES[$fieldName]["name"] as $key => $value) {
                $targetFile = $targetDirectory . basename($_FILES[$fieldName]["name"][$key]);
                if (move_uploaded_file($_FILES[$fieldName]["tmp_name"][$key], $targetFile)) {
                    $result[] = $targetFile;
                } else {
                    $result[] = null;
                }
            }
        } elseif (isset($_FILES[$fieldName])) {
            // If there's only one file, treat it as an array
            $targetFile = $targetDirectory . basename($_FILES[$fieldName]["name"]);
            if (move_uploaded_file($_FILES[$fieldName]["tmp_name"], $targetFile)) {
                $result[] = $targetFile;
            } else {
                $result[] = null;
            }
        }

        return $result;
    }

    // Check if profile picture is uploaded
    if (isset($_FILES["profile_pic"])) {
        $profilePicPaths = uploadImages("profile_pic");

        if (!empty($profilePicPaths)) {
            // Choose the first image path if multiple files are uploaded
            $profilePicPath = $profilePicPaths[0];

            // Check if DID already exists
            $checkQuery = "SELECT * FROM adddoctor WHERE did = '$did'";
            $checkResult = mysqli_query($conn, $checkQuery);

            if (mysqli_num_rows($checkResult) > 0) {
                echo json_encode(['status' => '2', 'message' => 'Doctor ID already exists']);
            } else {
                // Insert new record into database
                $insertQuery = "INSERT INTO adddoctor (did, name, phno, pass, gender, speciality, img)
                                VALUES ('$did', '$name', '$pno', '$password', '$gender', '$spe', '$profilePicPath')";

                if (mysqli_query($conn, $insertQuery)) {
                    echo json_encode(['status' => '1', 'message' => 'Id created  successfully']);
                } else {
                    echo json_encode(['status' => 'error', 'message' => mysqli_error($conn)]);
                }
            }
        } else {
            echo json_encode(['status' => 'error', 'message' => 'Failed to save the image']);
        }
    } else {
        echo json_encode(['status' => 'error', 'message' => 'Profile picture not uploaded']);
    }
} else {
    echo json_encode(['status' => 'error', 'message' => 'Missing or invalid parameters']);
}

// Close the database connection
mysqli_close($conn);
?>
