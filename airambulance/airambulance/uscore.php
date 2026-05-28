<?php
// Your database credentials
require("conn.php");

// Retrieve data from the POST request
$sh = isset($_POST['sh']) ? $_POST['sh'] : null;
$tt = isset($_POST['tt']) ? $_POST['tt'] : null;
$ttm = isset($_POST['ttm']) ? $_POST['ttm'] : null;
$hmd = isset($_POST['hmd']) ? $_POST['hmd'] : null;
$dshb = isset($_POST['dshb']) ? $_POST['dshb'] : null;
$dse = isset($_POST['dse']) ? $_POST['dse'] : null;
$dsac = isset($_POST['dsac']) ? $_POST['dsac'] : null;
$preE = isset($_POST['preE']) ? $_POST['preE'] : null;
$va = isset($_POST['va']) ? $_POST['va'] : null;
$value = isset($_POST['value']) ? $_POST['value'] : null;

// Ensure that all required data is provided
if ($sh !== null && $tt !== null && $ttm !== null && $hmd !== null && $dshb !== null && $dse !== null && $dsac !== null && $preE !== null && $va !== null && $value !== null) {
    // SQL query to insert the data into the database
    $sql = "UPDATE pdetails 
            SET  Ushb='$sh', Utt='$tt', Uttd='$ttm', Uhd='$hmd', Uhsk='$dshb',     
                Uesk='$dse', Uask='$dsac', Upr='$preE', Uva='$va' 
            WHERE pid='$value'";

    if ($conn->query($sql) === TRUE) {
        // If the data is successfully inserted
        $response = array('status' => 'success', 'message' => 'Data inserted successfully');
        echo json_encode($response);
    } else {
        // If there's an error in the SQL query
        $response = array('status' => 'error', 'message' => 'Error: ' . $sql . '<br>' . $conn->error);
        echo json_encode($response);
    }
} else {
    // If any of the required keys are not set
    $response = array('status' => 'error', 'message' => 'Missing required POST parameters');
    echo json_encode($response);
}

// Close the database connection
$conn->close();
?>
