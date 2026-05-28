<?php
require("conn.php");
if($_SERVER["REQUEST_METHOD"] == "POST" ) {
    // Decode the JSON data
    $json = file_get_contents('php://input');//data1["pid"];
    $data1 = json_decode($json, true);
    if(isset($_POST["did"]) && isset($_POST["password"])){
        $did = $_POST["did"];
        $password = $_POST["password"];
        $name = $_POST["name"];
      
        $pno = $_POST["phno"];
        $spe = $_POST["speciality"];
        $gender = $_POST["gender"];
        $bsql = "select * from doctorlogin where id='$did'";
        $bres =mysqli_query($conn,$bsql);

        if(mysqli_num_rows($bres) === 0){
   
        $sql = "insert into doctorlogin values('$did','$password')";
        // $sql1 = "INSERT INTO patient_info(pid, name, age, sex, cause) VALUES ('$pid', '$name', '$age', '$sex', '$cause')";

    
       
     $sql1 = "insert into adddoctor values('$did','$name','$pno','$password','$gender','$spe')";
     
    
     $q1 = "select * from a_profile where did='$did'";
        $result1 = mysqli_query($conn, $q1);
        if(mysqli_num_rows($result1) === 0)
{    $query1 = "insert into a_profile values('$did','name','speciality','m/f','@gmail.com')";
    mysqli_query($conn, $query1);
}
    
    
    


        if(mysqli_query($conn, $sql) && mysqli_query($conn,$sql1)){
            $response = array('status' => 'success', 'message' => 'data inserted succesfully');
          echo json_encode($response);
    }
    else{
        $response = array('status'=> 'fail','message'=>"data not intrested");
        echo json_encode($response);
    }
    
    }
    else{

        $response = array('status'=> 'fail','message'=>"id already exist");
        echo json_encode($response);
    
    
    }

}

}

?>