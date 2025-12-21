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
![Create User 1](<img width="1920" height="1080" alt="Create User 1" src="https://github.com/user-attachments/assets/b5bcfdcf-d6f0-444d-9efc-a7564f89fbbe" />
)

**Create User 2 (Rohit)**
![Create User 2](<img width="1919" height="1079" alt="Create User 2" src="https://github.com/user-attachments/assets/d00d3416-dea3-41e2-acf8-5989b67ced95" />
)

---

### 2. Group Management
We create a group to hold our expenses and add the users to it.

**Create Group "Summer Vacation"**
![Create Group](<img width="1920" height="1080" alt="Create Group 1" src="https://github.com/user-attachments/assets/142a3010-916f-44af-be8d-baaabdf70039" />
)

**Add User 2 to the Group**
![Add Member](<img width="1918" height="1079" alt="Add User 2 to Group 1" src="https://github.com/user-attachments/assets/499ad483-5a26-4f6d-9374-4232cc436ac4" />
)

---

### 3. Adding Expenses
The system supports multiple split strategies.

**Scenario A: Exact Split**
User 1 pays 100. User 1 owes 40, User 2 owes 60.
![Exact Split](<img width="1919" height="1079" alt="Sharing an Expense Exact" src="https://github.com/user-attachments/assets/8698cfca-6271-4d56-b78f-8a6a584d6e98" />
)

**Scenario B: Percentage Split**
User 1 pays 200. Split 60% (User 1) and 40% (User 2).
![Percentage Split](<img width="1914" height="1079" alt="Sharing an Expense Percentage" src="https://github.com/user-attachments/assets/944dd610-f0f9-469d-a3d6-aa387406ab72" />
)

---

### 4. Balance Calculation
The system automatically calculates the net amount owed. Since `User 2` owed money in both scenarios, the system aggregates the debt.

**View Group Balances**
![View Balances](<img width="1919" height="1079" alt="Viewing Balances" src="https://github.com/user-attachments/assets/a8ffde5f-902d-46eb-b7e0-84915fd3159d" />
)

---

### 5. Settlement
User 2 pays User 1 to clear the debt.

**Settle Up**
![Settlement](<img width="1915" height="1079" alt="Settlement" src="https://github.com/user-attachments/assets/db10ab22-4df7-40e8-9341-e310e1869550" />
)

**Verify Zero Balance**
After settlement, the balance list returns empty, confirming the debt is cleared.
![Verify Debt](<img width="1916" height="1079" alt="Verify the Debt" src="https://github.com/user-attachments/assets/fad32346-f19c-4c2d-ab24-d021750bb823" />
)

---

## ⚙️ How to Run
1. Clone the repo: `git clone https://github.com/YOUR_USERNAME/expense-sharing-backend.git`
2. Update `application.properties` with your MySQL username/password.
3. Run `mvn spring-boot:run`.
4. API will be active at `http://localhost:8080`.
