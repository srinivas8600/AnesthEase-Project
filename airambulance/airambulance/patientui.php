<?php
require("conn.php"); // Assuming this file contains your database connection

$response = array();

if (isset($_POST["patient_id"])) {
  $patient_id = $_POST["patient_id"];
  $name = isset($_POST["name"]) ? $_POST["name"] : '';
  $phno = isset($_POST["phno"]) ? $_POST["phno"] : '';
  $gender = isset($_POST["gender"]) ? $_POST["gender"] : '';
  $age = isset($_POST["age"]) ? $_POST["age"] : ''; // Corrected typo here

  // Check if a record with the given patient_id already exists
  $check_sql = "SELECT * FROM pdetails WHERE pid = '$patient_id'";
  $check_result = $conn->query($check_sql);

  if ($check_result && $check_result->num_rows > 0) {
    // Update the existing record
    $update_sql = "UPDATE pdetails SET 
    name = '$name', 
    phno = '$phno', 
    gender = '$gender', 
    age = '$age' 
  WHERE pid = '$patient_id'";

    if ($conn->query($update_sql) === TRUE) {
      $response['status'] = 'success';
      $response['message'] = 'Profile details updated successfully.';

      if (isset($_FILES["profile_photo"])) {
        $fileName = $_FILES["profile_photo"]["name"];
        $tempName = $_FILES["profile_photo"]["tmp_name"];
        $base_dir = "img/";

        if (!file_exists($base_dir)) {
          mkdir($base_dir, 0755, true);
        }

        $folder = $base_dir . $fileName;

        if (move_uploaded_file($tempName, $folder)) {
          $conn->query("UPDATE pdetails SET img = '$folder' WHERE pid = '$patient_id'");
          $response['message'] .= ' Image updated successfully.';
        } else {
          $response['message'] .= ' Image update failed.';
        }
      }
    } else {
      $response['status'] = 'error';
      $response['message'] = 'Failed to update profile details. Error: ' . $conn->error;
    }
  } else {
    $response['status'] = 'error';
    $response['message'] = 'Patient record not found.';
  }
} else {
  $response['status'] = 'error';
  $response['message'] = 'Invalid request.';
}

header('Content-Type: application/json');
echo json_encode($response);
?>
