<?php
if (!empty($_POST['data']) && !empty($_POST['number']) && !empty($_POST['username'])) {
    $data = $_POST['data'];
    $number = $_POST['number'];
    $username = $_POST['username'];
    
    $con = mysqli_connect('lesterintheclouds.com', 'enrolment_user', 'youHNNM_Wm=}', 'db_enrolment_app');
    
    if ($con) {
        $sql = "INSERT INTO crud_table (data, number, username) VALUES ('$data', '$number', '$username')";
        
        if(mysqli_query($con, $sql)){
            echo "Success";
        } else {
            echo "Failed to insert data";
        }
    } else {
        echo "Failed to connect to database";
    }
} else {
    echo "All fields are required";
}
?>
