<?php
require "conn.php";
error_reporting(E_ALL);
ini_set('display_errors', 1);
date_default_timezone_set('Asia/Kolkata');
header('Content-Type: application/json');

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id     = isset($_POST['id']) ? trim(mysqli_real_escape_string($conn, $_POST['id'])) : '';
    $name   = isset($_POST['name']) ? trim(mysqli_real_escape_string($conn, $_POST['name'])) : '';
    $gender = isset($_POST['gender']) ? trim(mysqli_real_escape_string($conn, $_POST['gender'])) : '';
    $age    = isset($_POST['age']) ? trim(mysqli_real_escape_string($conn, $_POST['age'])) : '';
    $height = isset($_POST['height']) ? trim(mysqli_real_escape_string($conn, $_POST['height'])) : '';
    $weight = isset($_POST['weight']) ? trim(mysqli_real_escape_string($conn, $_POST['weight'])) : '';
    $phno   = isset($_POST['phno']) ? trim(mysqli_real_escape_string($conn, $_POST['phno'])) : '';
    $bmi    = isset($_POST['bmi']) ? trim(mysqli_real_escape_string($conn, $_POST['bmi'])) : '';
    $dob    = isset($_POST['dob']) ? trim(mysqli_real_escape_string($conn, $_POST['dob'])) : '';
    $currentDate = date("Y-m-d H:i:s");

    if (empty($id) || empty($name) || empty($phno)) {
        echo json_encode(['status' => 'error', 'message' => 'ID, name, and phone number are required']);
        exit;
    }

    $checkQuery = "SELECT * FROM pdetails WHERE pid = '$id'";
    $result = mysqli_query($conn, $checkQuery);
    if (mysqli_num_rows($result) > 0) {
        echo json_encode(['status' => '2', 'message' => 'ID already exists']);
        exit;
    }

    function uploadImages($fieldName) {
        $targetDirectory = "img/";
        $result = [];

        if (isset($_FILES[$fieldName]) && is_array($_FILES[$fieldName]["name"])) {
            foreach ($_FILES[$fieldName]["name"] as $key => $value) {
                $targetFile = $targetDirectory . basename($_FILES[$fieldName]["name"][$key]);
                if (move_uploaded_file($_FILES[$fieldName]["tmp_name"][$key], $targetFile)) {
                    $result[] = $targetFile;
                } else {
                    $result[] = null;
                }
            }
        } elseif (isset($_FILES[$fieldName])) {
            $targetFile = $targetDirectory . basename($_FILES[$fieldName]["name"]);
            if (move_uploaded_file($_FILES[$fieldName]["tmp_name"], $targetFile)) {
                $result[] = $targetFile;
            } else {
                $result[] = null;
            }
        }
        return $result;
    }

    if (!isset($_FILES["profile_pic"])) {
        echo json_encode(['status' => 'error', 'message' => 'Profile picture not uploaded']);
        exit;
    }

    $profilePicPaths = uploadImages("profile_pic");
    if (empty($profilePicPaths) || $profilePicPaths[0] === null) {
        echo json_encode(['status' => 'error', 'message' => 'Failed to save the image']);
        exit;
    }
    $profilePicPath = $profilePicPaths[0];

    mysqli_begin_transaction($conn);

    try {
        // Step 1: Insert into patientlogin
        $insertLogin = "INSERT INTO patientlogin (pid, phno) VALUES ('$id', '$phno')";
        if (!mysqli_query($conn, $insertLogin)) {
            throw new Exception("patientlogin insert failed: " . mysqli_error($conn));
        }

        // Step 2: Insert into pdetails
        $query = "INSERT INTO pdetails (
            pid, name, phno, age, gender, height, weight, bmi, date, img,
            Crhm, Cwt, Cds, Cmmp, Cmoi, Ctmd, Ctmj, Cnm, Cnc, Cbmi,
            Ushb, Utt, Uttd, Uhd, Uhsk, Uesk, Uask, Upr, Uva,
            Ctotal, Utotal, total, atype, timestamp
        ) VALUES (
            '$id', '$name', '$phno', '$age', '$gender', '$height', '$weight', '$bmi', '$dob', '$profilePicPath',
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '0', '0', '0', '0', '$currentDate'
        )";

        if (!mysqli_query($conn, $query)) {
            throw new Exception("pdetails insert failed: " . mysqli_error($conn));
        }

        mysqli_commit($conn);
        echo json_encode(['status' => '1', 'message' => 'Patient and login info saved successfully']);

    } catch (Exception $e) {
        mysqli_rollback($conn);
        error_log("Transaction failed: " . $e->getMessage());
        echo json_encode(['status' => 'error', 'message' => $e->getMessage()]);
    }

} else {
    echo json_encode(['status' => 'error', 'message' => 'Invalid request method. Use POST.']);
}

mysqli_close($conn);
?>
