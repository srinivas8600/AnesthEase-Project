<?php
require "conn.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $pid = $_POST['pid'] ?? '';
    $phno = $_POST['phno'] ?? '';

    if (!empty($pid) && !empty($phno)) {
        $query = "SELECT 
            name, age, gender, phno, height, weight, bmi, date AS surgery,
            Crhm AS 'ASA Score',
            Cmmp AS 'Mallampatti Score',
            Ctmd AS 'Thyromental Distance',
            Cmoi AS 'Upper Lip Bite Test',
            Cnm AS 'Neck Movement',
            Ctmj AS 'Jaw Structure',
            Cnc AS 'Teeth Condition',
            Cbmi AS 'Beard Evaluation',
            Cds AS 'Sedation Level',
            total AS 'Airway Score',
            timestamp
            FROM pdetails 
            WHERE pid='$pid' AND phno='$phno'";

        $result = mysqli_query($conn, $query);

        if ($result && mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
            echo json_encode([
                "status" => "success",
                "data" => $row
            ]);
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "No patient found"
            ]);
        }
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "Missing pid or phno in request"
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Invalid request"
    ]);
}

mysqli_close($conn);
?>
