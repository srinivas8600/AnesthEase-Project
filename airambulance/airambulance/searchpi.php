<?php
require_once "conn.php";
header("Content-Type: application/json");

$response = array(); // Initialize response array

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $q1 = "SELECT pid,name,gender,phno,img FROM pdetails";
    $result = mysqli_query($conn, $q1);

    if ($result) {
        $patientList = array(); // Initialize an array to hold patient details

        // Fetch data from the database and store it in the array
        while ($row = mysqli_fetch_assoc($result)) {
            $patientList[] = $row;
        }

        // Add patient details to response
        $response['status'] = 'success';
        $response['data'] = $patientList;
    } else {
        // Error occurred while executing query
        $response['status'] = 'error';
        $response['message'] = 'Error fetching patient details from the database.';
    }
} else {
    // Invalid request method
    $response['status'] = 'error';
    $response['message'] = 'Invalid request method.';
}

// Convert the PHP array to JSON format
$jsonResponse = json_encode($response);

// Output the JSON data
echo $jsonResponse;
?>
