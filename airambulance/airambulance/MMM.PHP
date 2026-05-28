<?php
require("conn.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $json_data = file_get_contents("php://input");
    $data = json_decode($json_data, true);

    if ($data !== null) {
        $field1 = $data['field1'];
        $field2 = $data['field2'];
        $field3 = $data['field3'];
        $field4 = $data['field4'];
        $field5 = $data['field5'];
        $field6 = $data['field6'];
        $profile_img = $data['profile'];

        $q1 = "SELECT img FROM adddoctor WHERE did='$field4'";
        $result1 = mysqli_query($conn, $q1);
        $oldImagePath = '';
        if ($row = mysqli_fetch_assoc($result1)) {
            $oldImagePath = $row['img'];
        }
        
        $imagePath = 'doctor_images/' . uniqid() . '.jpg';

        if (file_put_contents($imagePath, base64_decode($profile_img))) {
            if (!empty($oldImagePath) && file_exists($oldImagePath)) {
                unlink($oldImagePath);
            }

            $sql = "UPDATE adddoctor SET name ='$field1', phno = '$field2', pass = '$field3', gender = '$field5', speciality = '$field6', img = '$imagePath' WHERE did = '$field4' ";
            $stmt=mysqli_query($conn, $sql);
            echo json_encode(array("status"=> "success","message"=> "Data updated successfully"));
        } else {
            $response = array('message' => 'Failed to save profile image');
            echo json_encode($response);
        }
    } else {
        $response = array('message' => 'Invalid JSON data');
        echo json_encode($response);
    }
} else {
    $response = array('message' => 'Invalid request method');
    echo json_encode($response);
}
?>
