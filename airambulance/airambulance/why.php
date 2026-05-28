<?php
// Include the file for database connection
include 'conn.php';

// Get the value parameter from the POST request
$value = $_POST['value'];

// SQL query to fetch the values for specific columns (crmh1 to crmh19) in the pdetails table
$sql = "SELECT Crhm,Cwt,Cds,Cmmp,Cmoi,Ctmd,Ctmj,Cnm,Cnc,Cbmi,Ushb,Utt,Uttd,Uhd,Uhsk,Uesk,Uask,Upr,Uva  FROM pdetails WHERE pid = '$value'";
$result = $conn->query($sql);

// Check if there are any rows
if ($result->num_rows > 0) {
    // Fetch data as an associative array
    $row = $result->fetch_assoc();

    // Initialize an associative array to store column values
    $response = array();

    // Specify each column name and associate it with the corresponding ID (w1 to w19)
    $response['w1'] = $row['Crhm'];
    $response['w2'] = $row['Cwt'];
    $response['w3'] = $row['Cds'];
    $response['w4'] = $row['Cmmp'];
    $response['w5'] = $row['Cmoi'];
    $response['w6'] = $row['Ctmd'];
    $response['w7'] = $row['Ctmj'];
    $response['w8'] = $row['Cnm'];
    $response['w9'] = $row['Cnc'];
    $response['w10'] = $row['Cbmi'];
    $response['w11'] = $row['Ushb'];    
    $response['w12'] = $row['Utt'];
    $response['w13'] = $row['Uttd'];
    $response['w14'] = $row['Uhd'];
    $response['w15'] = $row['Uhsk'];
    $response['w16'] = $row['Uesk'];
    $response['w17'] = $row['Uask'];
    $response['w18'] = $row['Upr'];
    $response['w19'] = $row['Uva'];

    // Send the JSON response
    echo json_encode($response);
} else {
    // No rows found with the specified condition
    echo "No data found";
}

// Close the database connection
$conn->close();
?>