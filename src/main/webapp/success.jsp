<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Success</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .message-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .message-box {
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }
        h2 {
            margin: 0 0 20px;
            color: #333;
        }
        p {
            color: #555;
            font-size: 18px;
            margin: 0;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
        }
        a:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="message-container">
        <div class="message-box">
            <h2>Success!</h2>
            <p><%=request.getAttribute("newOrNot")%> has been <%=request.getAttribute("addedOrRemoved")%></p>
            <a href="management.jsp">Back to Home</a>
        </div>
    </div>
</body>
</html>
