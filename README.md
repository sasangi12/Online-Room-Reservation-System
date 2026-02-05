🏨 Ocean View Resort – Automated Room Reservation System

An end-to-end web-based Automated Room Reservation System developed to replace the manual paper-based reservation process at Ocean View Resort, Galle.

The system improves efficiency by enabling digital reservation handling, billing automation, user authentication, reporting, and error handling using modern software development practices.


📌 Features
🔐 Authentication Module

-Secure login for staff users

-Session-based access control

-Unauthorized access handling (401 responses)


🏨 Reservation Management

-Create new reservations

-Validate dates and contact numbers

-Prevent duplicate reservation numbers

-View reservation details by reservation number


💰 Billing System

Automatically calculates:

-Number of nights

-Rate per night based on room type

-Total amount

Generates bill using reservation number


📊 Reports Module

-Reservation report by date range

-Revenue report by date range


⚠️ Global Exception Handling

-Validation errors (400)

-Not found errors (404)

-Unauthorized login (401)

-Clean JSON error responses


🎨 Modern Frontend UI

-HTML, CSS, JavaScript

-Neat dark-themed interface (no blue/green)

-Connected to backend using Fetch API

-Session protection and logout system


🧪 Automated Testing

JUnit & Mockito unit tests

Covers:

-Reservation service

-Billing service

-Authentication service

All tests passing (green results)


🛠️ Technologies Used
-Backend

-Java

-Spring Boot

-Spring Data JPA

-MySQL

-Hibernate

-Maven

-Frontend

-HTML5

-CSS3 (modern responsive design)

-JavaScript (Fetch API)

Testing

-JUnit 5

-Mockito

Tools

-Eclipse IDE

-MySQL Workbench 8.0


Project Structure

room-reservation-backend/
│
├── src/main/java/lk/icbt/ovr/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── exception/
│   └── config/
│
├── src/test/java/lk/icbt/ovr/service/
│   ├── ReservationServiceImplTest.java
│   ├── BillingServiceImplTest.java
│   └── AuthServiceImplTest.java
│
└── frontend/
    ├── css/
    ├── js/
    ├── login.html
    ├── reservation.html
    ├── view.html
    ├── bill.html
    ├── reports.html
    └── help.html


🚀 How to Run the System
1️⃣ Backend (Spring Boot)

Open project in Eclipse

Run:

RoomReservationBackendApplication.java


Backend runs on:

http://localhost:8080


2️⃣ Database

Create database:

ocean_view_resort_db

Run SQL scripts for:

users

reservations

room_rates

(Default admin user included)


3️⃣ Frontend

Open frontend/ folder using VS Code

Run with Live Server (recommended)

Start from:

login.html
