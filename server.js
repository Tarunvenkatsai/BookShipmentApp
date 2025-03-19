require("dotenv").config();
const express = require("express");
const mysql = require("mysql");
const cors = require("cors");

const app = express();
app.use(cors());
app.use(express.json()); // Allow JSON data in requests

// MySQL Connection Setup
const db = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "tarunsai!2003",  // If you set a password in MySQL, enter it here
    database: "shippingdb", // Change to your database name
});

// Connect to MySQL
db.connect((err) => {
    if (err) {
        console.error("Database connection failed:", err);
        return;
    }
    console.log("✅ Connected to MySQL Database");
});

// API Endpoint to Fetch Shipping Rates
app.get("/getRates", (req, res) => {
    const query = "SELECT * FROM shipping_rates"; // Ensure this table exists in MySQL
    db.query(query, (err, results) => {
        if (err) {
            res.status(500).send(err);
            return;
        }
        res.json(results);
    });
});

// Start the server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
    console.log(`🚀 Server running on http://localhost:${PORT}`);
});
