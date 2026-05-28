<?php
require_once "conn.php";

// Check if POST data is received
if ($_SERVER["REQUEST_METHOD"] === "POST") {
    // Retrieve data from the POST request
    // Assuming patient_id is a unique identifier for each patient
    $sql = "SELECT pid, name, gender, phno, img FROM pdetails ORDER BY timestamp  DESC LIMIT 5";

    $result = $conn->query($sql);

    if ($result === false) {
        // Handle query execution error
        echo json_encode(array('status' => 'failure', 'message' => 'Query execution error: ' . $conn->error));
    } else {
        if ($result->num_rows > 0) {
            $patients = array();

            while ($row = $result->fetch_assoc()) {
                $patients[] = $row;
            }

            echo json_encode(array('status' => 'success', 'data' => $patients));
        } else {
            echo json_encode(array('status' => 'failure', 'message' => 'No recent patients found'));
        }
    }
} else {
    echo json_encode(array('status' => 'failure', 'message' => 'Invalid request method'));
}
?>
