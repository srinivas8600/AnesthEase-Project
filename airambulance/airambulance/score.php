<?php
// Your database credentials
require("conn.php");

// Enable error reporting and display errors for debugging (remove in production)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Log received POST parameters for debugging
file_put_contents('post_params.log', print_r($_POST, true), FILE_APPEND);

// Retrieve data from the POST request
$s1 = isset($_POST['s1']) ? $_POST['s1'] : null;
$s2 = isset($_POST['s2']) ? $_POST['s2'] : null;
$s11 = isset($_POST['s11']) ? $_POST['s11'] : null;
$s12 = isset($_POST['s12']) ? $_POST['s12'] : null;
$value = isset($_POST['value']) ? $_POST['value'] : null;

try {
    // Ensure that all required data is provided
    if ($s1 !== null && $s2 !== null && $s11 !== null && $s12 !== null && $value !== null) {
        // SQL query to insert the data into the database
        $sql = "UPDATE pdetails 
                SET  Ctotal='$s1', Utotal='$s2', total='$s11', atype='$s12' 
                WHERE pid='$value'";

        if ($conn->query($sql) === TRUE) {
            // If the data is successfully inserted
            $response = array('status' => 'success', 'message' => 'Data updated successfully');
            echo json_encode($response);
        } else {
            // If there's an error in the SQL query
            $response = array('status' => 'error', 'message' => 'Error updating data: ' . $conn->error);
            echo json_encode($response);
        }
    } else {
        // If any of the required keys are not set
        $response = array('status' => 'error', 'message' => 'Missing required POST parameters');
        echo json_encode($response);
    }
} catch (Exception $e) {
    // Handle exceptions
    $response = array('status' => 'error', 'message' => 'Exception: ' . $e->getMessage());
    echo json_encode($response);
} finally {
    // Close the database connection
    $conn->close();
}
?>
