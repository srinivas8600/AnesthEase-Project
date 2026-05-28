<?php
require("conn.php");
// Include your database connection script

// Perform a database query

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    $json = file_get_contents('php://input');
    $data = json_decode($json, true);

    if(isset($_POST['P_id'])){
        $pid = $_POST['P_id'];
    
    
    $query = "SELECT * FROM adminprofile where id='$pid'";
$result = mysqli_query($conn, $query);
    

if ($result) {
    $data = mysqli_fetch_assoc($result);

    // Close the database connection
   

    // Return data in JSON format

    

    echo json_encode($data);
} else {
    echo "Error fetching data";
}
}
}

mysqli_close($conn);
?>