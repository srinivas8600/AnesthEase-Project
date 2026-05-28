<?php
// Include the database connection file (replace with your actual database connection code)
require("conn.php");

// Check if the request is a POST request
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Check if all required fields are present in the POST data
    if (
        isset($_POST['field0']) &&
        isset($_POST['field1']) &&
        isset($_POST['field2']) &&
        isset($_POST['field3']) &&
        isset($_POST['field4'])
    ) {
        // Extract data from the POST
        $field0 = $_POST['field0'];
        $field1 = $_POST['field1'];
        $field2 = $_POST['field2'];
        $field3 = $_POST['field3'];
        $field4 = $_POST['field4'];

        // Perform the database update (replace with your database update code)
        $sql = "UPDATE adminprofile SET name ='$field1', phno = '$field2', gender = '$field3', email = '$field4' WHERE id='$field0'";
        $stmt = mysqli_query($conn, $sql);
        
        // Check if the update was successful
        if ($stmt) {
            $response = array('status' => 'success', 'message' => 'Data updated successfully');
        } else {
            $response = array('status' => 'failure', 'message' => 'Data not updated');
        }
    } else {
        $response = array('status' => 'failure', 'message' => 'Missing fields in the request');
    }
} else {
    $response = array('status' => 'failure', 'message' => 'Invalid request method');
}

// Send the JSON response
echo json_encode($response);
?>
