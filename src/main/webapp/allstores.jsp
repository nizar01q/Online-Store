<%@ page import="org.example.onlinestore.entity.Store" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>All Stores</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .table-container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .user-table {
            background-color: #fff;
            border-collapse: collapse;
            width: 80%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .user-table th, .user-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .user-table th {
            background-color: #4CAF50;
            color: white;
        }
        .user-table tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <div class="table-container">
        <table class="user-table">
            <thead>
                <tr>
                    <th>Store ID</th>
                </tr>
            </thead>
            <tbody>
                <% List<Store> stores = (List<Store>) request.getAttribute("stores");
                for (Store store: stores){
                %>
                <tr>
                    <td><%= store.getStoreID()%></td>
                </tr>
            <% } %>

            </tbody>
        </table>
    </div>
</body>
</html>



