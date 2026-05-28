<?php
require_once "conn.php";
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $response = array(); // Initialize response array

    $q1 = "SELECT did, name, gender, phno, img FROM adddoctor";
    $result = mysqli_query($conn, $q1);

    if ($result) {
        $patientList = array(); // Initialize an array to hold patient data

        // Fetch data from the database and store it in the array
        while ($row = mysqli_fetch_assoc($result)) {
            $patientList[] = $row;
        }

        // Set status to success and include data in response
        $response["status"] = "success";
        $response["data"] = $patientList;
    } else {
        // Set status to error if query fails
        $response["status"] = "error";
        $response["message"] = "Failed to fetch data";
    }

    // Convert the PHP array to JSON format
    $jsonResponse = json_encode($response);

    // Output the JSON response
    echo $jsonResponse;
}
?>
