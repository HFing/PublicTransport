# 🚍 Public Transport System

A public transport management and lookup system, including:
- **Backend**: Spring (Web MVC, ORM, Security), packaged as a WAR for deployment on Tomcat/Jetty/Glassfish.
- **Frontend**: React + Bootstrap + Leaflet for displaying maps and bus routes.

---

## 📂 Repository Structure
```
PublicTransport/
│── backend/    # Java Maven (Spring MVC, Hibernate, Security, MySQL)
│
│── frontend/   # React (CRA)
│
├── .gitignore
└── README.md
```

---

## ⚙️ Requirements

### Backend (Java)
- JDK **17+**
- Maven **3.9+**
- MySQL **8.0+**
- Application server (Tomcat/Jetty) **or** run directly with Spring Boot (`spring-boot-starter` if configured)

**Database setup (MySQL):**


In `src/main/resources/application.properties` (if available):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/public_transport
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### Frontend (React)
- Node.js **20+**
- Yarn or npm

---

## ▶️ How to Run

### Backend
```bash
cd backend
# build the WAR
mvn clean package

# run the app (if Spring Boot embedded is configured)
mvn spring-boot:run
```

Backend will run at:  
👉 http://localhost:8080

---

### Frontend
```bash
cd frontend
# install dependencies
npm install
# or
yarn install

# run dev server
npm start
```

Frontend will run at:  
👉 http://localhost:3000

---

## 🔐 Security
- Uses **Spring Security 6** (JWT + Session).
- `nimbus-jose-jwt` for token handling.
- `thymeleaf-extras-springsecurity6` for secure Thymeleaf integration.

---

## 🗺️ Map Integration
- Frontend uses **React-Leaflet** + **Google Map React** to display stations and routes.
- Configure API Key in `.env`:
```env
REACT_APP_GOOGLE_MAPS_KEY=your_api_key_here
```

---

## 🛠️ Tech Stack

### Backend
- Spring MVC, Spring ORM, Spring Security
- Hibernate
- MySQL
- Jakarta EE APIs (Servlet, JSP)
- Cloudinary SDK (image upload)
- Google API Client

### Frontend
- React 19
- Bootstrap 5
- React Router v7
- React-Leaflet + Google Map React
- Axios
- React Toastify

---

## 👥 Contributors
- **HFing (Nguyễn Thái Hoàng)** – Backend (Team Lead)
- **ThangTB5** – Frontend
