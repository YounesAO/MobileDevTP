<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Position API</title>
</head>
<body>
    <h2>Test Position API</h2>
    <form action="createPosition.php" method="post">
        <label for="latitude">Latitude:</label>
        <input type="text" id="latitude" name="latitude" required>
        <br><br>
        
        <label for="longitude">Longitude:</label>
        <input type="text" id="longitude" name="longitude" required>
        <br><br>

        <label for="date">Date:</label>
        <input type="datetime-local" id="date" name="date" required>
        <br><br>

        <label for="imei">IMEI:</label>
        <input type="text" id="imei" name="imei" required>
        <br><br>
        
        <button type="submit">Submit</button>
    </form>
</body>
</html>
