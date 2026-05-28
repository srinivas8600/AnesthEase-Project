<?php
// Your database connection parameters
require("conn.php");

// SQL query to fetch patient information
$sql = "SELECT did, name, gender, phno,img FROM adddoctor";

$result = $conn->query($sql);

$response = array(); // Initialize response array

if ($result->num_rows > 0) {
    $data = array();
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }
    $response["status"] = "success"; // Add status to response
    $response["data"] = $data; // Add data to response
} else {
    $response["status"] = "error"; // Set status to error if no results found
    $response["message"] = "No results found";
}

echo json_encode($response); // Return JSON response
$conn->close();
?>
