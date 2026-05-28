<?php
require("conn.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get the JSON data from the request body
    $json_data = file_get_contents("php://input");
    // Decode the JSON data into an associative array
    

    if(isset($_POST['P_id'])){
        // Extract the patient ID from the JSON data
        $pid = $_POST['P_id'];
 
        // Query to select profile data based on patient ID
        $query = "SELECT * FROM adddoctor WHERE did='$pid'";
        $result = mysqli_query($conn, $query);
        
        if ($result) {
            // Fetch profile data as an associative array
            $data = array();
            while ($row = mysqli_fetch_assoc($result)) {
                $data = $row;
            }
            // Encode the profile data as JSON and echo it
            echo json_encode($data);
        } else {
            echo "Error fetching data";
        }
    } else {
        echo "Patient ID not provided";
    }
} else {
    echo "Invalid request method";
}

// Close database connection
mysqli_close($conn);
