<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Store - Homepage</title>
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-image: url("images/homebackground.jpg");
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .header .search-bar {
            flex-grow: 1;
            margin-left: 20px;
            margin-right: 20px;
        }
        .search-bar input[type="text"] {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: none;
            box-sizing: border-box;
        }
        .icons {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        .icons a {
            color: white;
            text-decoration: none;
            font-size: 18px;
        }
        .icons a:hover {
            color: #ddd;
        }
        .container {
            display: flex;
            justify-content: center;
            padding: 20px;
        }
        .category-card {
            background-color: white;
            padding: 20px;
            margin: 10px;
            border-radius: 8px;
            width: 300px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .category-card h2 {
            margin: 0 0 10px;
        }
        .category-card a {
            display: block;
            margin-top: 10px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 10px;
            border-radius: 4px;
        }
        .category-card a:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>EchoShop</h1>
        <div class="search-bar">
            <input type="text" placeholder="Search for products...">
        </div>
        <div class="icons">
            <a href="manageUsers.jsp" title="Login">
                &#128100;
            </a>
            <a href="cart.jsp" title="Cart">
                &#128722;
            </a>
        </div>
    </div>

    <div class="container">
        <div class="category-card">
            <h2>Electronics</h2>
            <p>Browse our latest gadgets and devices.</p>
            <a href="category.jsp?category=electronics">Shop Now</a>
        </div>
        <div class="category-card">
            <h2>Clothing</h2>
            <p>Explore our fashion collection.</p>
            <a href="category.jsp?category=clothing">Shop Now</a>
        </div>
        <div class="category-card">
            <h2>Home & Kitchen</h2>
            <p>Upgrade your home essentials.</p>
            <a href="category.jsp?category=home">Shop Now</a>
        </div>
    </div>
</body>
</html>
