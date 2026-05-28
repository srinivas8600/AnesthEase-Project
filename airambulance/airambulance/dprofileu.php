<?php
// Include your database connection file
include 'conn.php';

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Check if the required parameters are set
    $json_data = file_get_contents("php://input");

    // Parse the JSON data into an associative array
    $data = json_decode($json_data, true);
    if ($data != null) {
        // Retrieve the values from POST parameters
        $P_id = $data['P_id'];
        $name = $data['name'];
        $speciality = $data['speciality'];
        $gender = $data['gender'];
        $phno = $data['phno'];
        
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
} else {
    // Invalid request method
    $response = array(
        'status' => 'error',
        'message' => 'Invalid request method'
    );
    echo json_encode($response);
}
?>
