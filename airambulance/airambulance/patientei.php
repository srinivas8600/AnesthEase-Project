<?php
require("conn.php"); // Assuming this file contains your database connection

$response = array();

if (isset($_POST["patient_id"]) && isset($_FILES["profile_photo"])) {
  $patient_id = $_POST["patient_id"];
  $name = isset($_POST["name"]) ? $_POST["name"] : '';
  $phno = isset($_POST["phno"]) ? $_POST["phno"] : '';
  $gender = isset($_POST["gender"]) ? $_POST["gender"] : '';
  $email = isset($_POST["age"]) ? $_POST["age"] : '';

  $fileName = $_FILES["profile_photo"]["name"];
  $tempName = $_FILES["profile_photo"]["tmp_name"];

  // Base directory for storing images (outside "admin/") for security
  $base_dir = "img/";

  // Check if the base directory exists (optional)
  if (!file_exists($base_dir)) {
    mkdir($base_dir, 0755, true); // Create the directory with appropriate permissions
  }

  $folder = $base_dir . $fileName; // Construct the full file path

  // Check if a record with the given patient_id already exists
  $check_sql = "SELECT * FROM pdetails WHERE pid = '$patient_id'";
  $check_result = $conn->query($check_sql);

  if ($check_result && $check_result->num_rows > 0) {
    $existing_record = $check_result->fetch_assoc();
    $old_image_path = $existing_record['img'];

    // Delete the existing image file if it exists
    if (file_exists($old_image_path)) {
      unlink($old_image_path);
    }

    // Update the existing record
    $update_sql = "UPDATE pdetails SET 
                      img = '$folder', 
                      name = '$name', 
                      phno = '$phno', 
                      gender = '$gender', 
                      age = '$email' 
                    WHERE pid = '$patient_id'";

    if ($conn->query($update_sql) === TRUE) {
      // Move the uploaded file to the specified directory
      if (move_uploaded_file($tempName, $folder)) {
        $response['status'] = 'success';
        $response['message'] = 'Profile updated successfully.';
      } else {
        $response['status'] = 'error';
        $response['message'] = 'Failed to move the uploaded file.';
      }
    } else {
      $response['status'] = 'error';
      $response['message'] = 'Data not updated. Error: ' . $conn->error;
    }
  } else {
    // Insert a new record
    $insert_sql = "INSERT INTO pdetails (pid, img, name, phno, gender, email) 
                    VALUES ('$patient_id', '$folder', '$name', '$phno', '$gender', '$email')";

    if ($conn->query($insert_sql) === TRUE) {
      // Move the uploaded file to the specified directory
      if (move_uploaded_file($tempName, $folder)) {
        $response['status'] = 'success';
        $response['message'] = 'New patient record created.';
      } else {
        $response['status'] = 'error';
        $response['message'] = 'Failed to move the uploaded file.';
      }
    } else {
      $response['status'] = 'error';
      $response['message'] = 'Data not inserted. Error: ' . $conn->error;
    }
  }
} else {
  $response['status'] = 'error';
  $response['message'] = 'Invalid request.';
}

header('Content-Type: application/json');
echo json_encode($response);
?>
