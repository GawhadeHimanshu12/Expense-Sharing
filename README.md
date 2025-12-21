# Expense Sharing System - Backend

A robust backend REST API for managing shared expenses, groups, and settlements (similar to Splitwise). Built with **Java Spring Boot** and **MySQL**, designed to handle complex splitting logic and automatic balance simplification.

## Tech Stack
* **Language:** Java 17
* **Framework:** Spring Boot 3 (Web, Data JPA)
* **Database:** MySQL 8
* **Tools:** Maven, Postman, Lombok

---

## API Workflow & Testing
Here is a walkthrough of the core features using Postman.

### 1. User Management
First, we create users who will participate in the expense sharing.

**Create User 1 (Himanshu)**
![Create User 1]()

**Create User 2 (Rohit)**
![Create User 2](screenshots/Create%20User%202.png)

---

### 2. Group Management
We create a group to hold our expenses and add the users to it.

**Create Group "Summer Vacation"**
![Create Group](screenshots/Create%20Group%201.png)

**Add User 2 to the Group**
![Add Member](screenshots/Add%20User%202%20to%20Group%201.png)

---

### 3. Adding Expenses
The system supports multiple split strategies.

**Scenario A: Exact Split**
User 1 pays 100. User 1 owes 40, User 2 owes 60.
![Exact Split](screenshots/Sharing%20an%20Expense%20Exact.png)

**Scenario B: Percentage Split**
User 1 pays 200. Split 60% (User 1) and 40% (User 2).
![Percentage Split](screenshots/Sharing%20an%20Expense%20Percentage.png)

---

### 4. Balance Calculation
The system automatically calculates the net amount owed. Since `User 2` owed money in both scenarios, the system aggregates the debt.

**View Group Balances**
![View Balances](screenshots/Viewing%20Balances.png)

---

### 5. Settlement
User 2 pays User 1 to clear the debt.

**Settle Up**
![Settlement](screenshots/Settlement.png)

**Verify Zero Balance**
After settlement, the balance list returns empty, confirming the debt is cleared.
![Verify Debt](screenshots/Verify%20the%20Debt.png)

---

## ⚙️ How to Run
1. Clone the repo: `git clone https://github.com/YOUR_USERNAME/expense-sharing-backend.git`
2. Update `application.properties` with your MySQL username/password.
3. Run `mvn spring-boot:run`.
4. API will be active at `http://localhost:8080`.
