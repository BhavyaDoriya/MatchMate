# MatchMate | Core Java Matchmaking Engine 💘

A pure Core Java backend matchmaking engine that processes user profiles, handles robust input validations, stores data via JDBC/MySQL, and features automated email notifications.

## 🚀 Features
- **Smart Matchmaking Logic:** Filters and pairs user profiles based on gender preferences (by default) and [city, age] (Add-ons as per user input).
- **Custom Data Structures & Exceptions:** Implements a tailored `LinkedList` data structure for efficient profile management and utilizes custom exception handling for graceful error management.
- **Robust Input Validation:** Ensures clean and secure user data entry across all input fields.
- **Secure Storage:** Manages persistent user data across normalized MySQL tables using JDBC Prepared Statements to prevent SQL injection.
- **Advanced Database Operations:** Utilizes `CallableStatement` to trigger custom Stored Procedures directly within the MySQL database.
- **Automated Emails & OTPs:** Uses Jakarta Mail to generate OTPs for user verification and send out real-time match notifications.
- **Secure Configuration:** Utilizes `dotenv-java` to keep database credentials and app passwords completely hidden from version control.

---

## 🛠️ Prerequisites
Before running this project locally, ensure you have the following installed:
1. **Java JDK 8 or higher** 
2. **XAMPP** (or any local MySQL server)
3. An IDE (IntelliJ IDEA, Eclipse, or VS Code)

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone [https://github.com/BhavyaDoriya/MatchMate.git](https://github.com/BhavyaDoriya/MatchMate.git)
cd MatchMate
```

### 2. Database Setup
This project uses a provided schema file to instantly build the required tables and stored procedures.
1. Open XAMPP and start both **Apache** and **MySQL**.
2. Open your browser and navigate to `http://localhost/phpmyadmin`.
3. Create a new database named `matchmate`.
4. Click on your new `matchmate` database, and navigate to the **Import** tab at the top.
5. Upload the `schema.sql` file provided in the root directory of this repository and click **Import**.

### 3. Environment Variables (.env)
This project uses environment variables to secure sensitive credentials.
1. In the root directory, locate the `.env.example` file.
2. Copy it and rename the copy to strictly `.env`.
3. Open `.env` and fill in your XAMPP MySQL credentials and your Gmail App Password.
    * *Note: If using default XAMPP, the `DB_USERNAME` is usually `root` and `DB_PASSWORD` is left blank.*

### 4. Attach the External Libraries
Because this is a pure Core Java project, you must attach the external `.jar` files to your IDE. All required libraries are conveniently included in the `/lib` folder:
* `mysql-connector-j-9.3.0.jar` (JDBC Driver)
* `jakarta.mail-2.0.1.jar` (Email Client)
* `jakarta.activation-2.0.1.jar` (Mail Dependency)
* `dotenv-java-3.0.0.jar` (Environment Variables)

**How to add them:**
* **IntelliJ IDEA:** Right-click the `lib` folder -> Select **"Add as Library..."** -> Click OK.
* **VS Code:** Open the "Java Projects" tab in the bottom-left Explorer -> Expand "Referenced Libraries" -> Click the `+` icon -> Select all 4 `.jar` files in the `lib` folder.
* **Eclipse:** Right-click Project -> Build Path -> Configure Build Path -> Libraries Tab -> Add JARs -> Select the 4 JARs from the `lib` folder.

### 5. Run the Engine
Navigate to `src/Main.java` and run the file to start the console application.

---

## 👥 Team & Contributions
This project was collaboratively built by:
* **Bhavya Doriya:** Project architecture, Database Design & Integration (JDBC/MySQL), Matchmaking Engine logic, and robust input validations in user registration.
* **Akshita:** `EmailUtils` integration (Jakarta Mail), OTP generation, user input field strategy, login, forgot password and Matches View UI/logic.
* **Mahek:** Implementation of Custom Exception Handling and customized `LinkedList` Data Structure integration.

---

## 📄 License & Ownership
This is an academic group project created for educational purposes. It is open-source and available under the MIT License. See the [LICENSE](LICENSE) file for more details. Copyright © 2025 Bhavya Doriya, Akshita, and Mahek.
