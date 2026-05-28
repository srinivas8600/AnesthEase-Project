<?php
// Your database credentials
require("conn.php");

// Retrieve data from the POST request
$rhm = isset($_POST['rhm']) ? $_POST['rhm'] : null;
$ds = isset($_POST['ds']) ? $_POST['ds'] : null;
$wt = isset($_POST['wt']) ? $_POST['wt'] : null;
$mmp = isset($_POST['mmp']) ? $_POST['mmp'] : null;
$mi = isset($_POST['mi']) ? $_POST['mi'] : null;
$tmd = isset($_POST['tmd']) ? $_POST['tmd'] : null;
$tmj = isset($_POST['tmj']) ? $_POST['tmj'] : null;
$nm = isset($_POST['nm']) ? $_POST['nm'] : null;
$nc = isset($_POST['nc']) ? $_POST['nc'] : null;
$bmi = isset($_POST['bmi']) ? $_POST['bmi'] : null;
$value = isset($_POST['value']) ? $_POST['value'] : null;

// Ensure that all required data is provided
if ($rhm !== null && $ds !== null && $wt !== null && $mmp !== null && $mi !== null && $tmd !== null && $tmj !== null && $nm !== null && $nc !== null && $bmi !== null && $value !== null) {
    // SQL query to insert the data into the database
    $sql = "UPDATE pdetails 
            SET  Crhm='$rhm',Cds='$ds', Cwt='$wt', Cmmp='$mmp', Cmoi='$mi',     
                Ctmd='$tmd', Ctmj='$tmj', Cnm='$nm', Cnc='$nc', Cbmi='$bmi'
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
