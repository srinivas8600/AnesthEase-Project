<?php
// Include your database connection file
include 'conn.php';

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Retrieve the values from POST parameters
    $P_id = $_POST['P_id'];
    $name = $_POST['name'];
    $speciality = $_POST['speciality'];
    $gender = $_POST['gender'];
    $phno = $_POST['phno'];
    
    // Perform database update query based on patient ID
    $query = "UPDATE adddoctor SET name = '$name', speciality = '$speciality', gender = '$gender', phno = '$phno' WHERE did = '$P_id'";
    $result = mysqli_query($conn, $query);
    
    if ($result) {
        // Update successful
        $response = array(
            'status' => 'success',
            'message' => 'Doctor profile updated successfully'
        );
        echo json_encode($response);
    } else {
        // Update failed
        $response = array(
            'status' => 'error',
            'message' => 'Failed to update doctor profile'
        );
        echo json_encode($response);
    }
} else {
    // Required parameters not set
    $response = array(
        'status' => 'error',
        'message' => 'Required parameters not set'
    );
    echo json_encode($response);
}
?>
