<?php
// Include your database connection script
require("conn.php");

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Check if the 'P_id' parameter is provided in the POST request
    if (isset($_POST['P_id'])) {
        // Retrieve the 'P_id' from the POST parameters
        $pid = $_POST['P_id'];

        // Prepare and execute the SQL query to fetch admin profile data
        $query = "SELECT * FROM adddoctor WHERE did='$pid'";
        $result = mysqli_query($conn, $query);

        if ($result) {
            // Check if any data was found for the specified admin ID
            if (mysqli_num_rows($result) > 0) {
                // Fetch the admin profile data
                $data = mysqli_fetch_assoc($result);

                // Close the database connection
                mysqli_close($conn);

                // Prepare the success response
                $response = array(
                    "status" => "success",
                    "message" => "Profile retrieved successfully",
                    "profileData" => $data
                );

                // Return the response as JSON
                echo json_encode($response);
            } else {
                // Close the database connection
                mysqli_close($conn);

                // Prepare the error response (no data found for the specified admin ID)
                $response = array(
                    "status" => "error",
                    "message" => "No details found for the respective admin",
                    "profileData" => null
                );

                // Return the response as JSON
                echo json_encode($response);
            }
        } else {
            // Close the database connection
            mysqli_close($conn);

            // Prepare the error response (query execution error)
            $response = array(
                "status" => "error",
                "message" => "Error executing query",
                "profileData" => null
            );

            // Return the response as JSON
            echo json_encode($response);
        }
    } else {
        // Prepare the error response (missing 'P_id' parameter)
        $response = array(
            "status" => "error",
            "message" => "P_id parameter is missing",
            "profileData" => null
        );

        // Return the response as JSON
        echo json_encode($response);
    }
} else {
    // Prepare the error response (invalid request method)
    $response = array(
        "status" => "error",
        "message" => "Invalid request method",
        "profileData" => null
    );

    // Return the response as JSON
    echo json_encode($response);
}
?>
