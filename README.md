# 🚍 Public Transport System

Hệ thống quản lý và tra cứu giao thông công cộng, bao gồm:
- **Backend**: Spring (Web MVC, ORM, Security) build dưới dạng WAR deploy lên Tomcat/Jetty/Glassfish...
- **Frontend**: React + Bootstrap + Leaflet (hiển thị bản đồ, tra cứu tuyến xe buýt).


## 📂 Cấu trúc repo
```

PublicTransport/
│── backend/    # Java Maven (Spring MVC, Hibernate, Security, MySQL)
│
│── frontend/   # React (CRA)
│
├── .gitignore
└── README.md

---

## ⚙️ Yêu cầu môi trường

### Backend (Java)
- JDK **17+**
- Maven **3.9+**
- MySQL **8.0+**
- Application server (Tomcat/Jetty) **hoặc** chạy Spring Boot trực tiếp (`spring-boot-starter` nếu cấu hình embedded)

**Cấu hình DB (MySQL):**


Trong `src/main/resources/application.properties` (nếu có):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/public_transport
spring.datasource.username=transport_user
spring.datasource.password=password123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```


### Frontend (React)
- Node.js **20+**
- Yarn **hoặc** npm

---

## ▶️ Cách chạy

### Backend
```bash
cd backend
# build WAR
mvn clean package


Server sẽ chạy ở:  
👉 [http://localhost:8080](http://localhost:8080)

---

### Frontend
```bash
cd frontend
# cài dependencies
npm install
# hoặc
yarn install

# chạy dev server
npm start
```

Server sẽ chạy ở:  
👉 [http://localhost:3000](http://localhost:3000)

---


## 🔐 Tích hợp bảo mật
- Sử dụng **Spring Security 6** (JWT + Session).
- Thư viện `nimbus-jose-jwt` để xử lý token.
- `thymeleaf-extras-springsecurity6` hỗ trợ UI bảo mật với Thymeleaf.

---

## 🗺️ Tích hợp bản đồ
- Frontend dùng **React-Leaflet** + **Google Map React** để hiển thị vị trí trạm, tuyến đường.
- Config API Key qua file `.env`:
```env
REACT_APP_GOOGLE_MAPS_KEY=your_api_key_here
```

---

## 🛠️ Công nghệ chính

### Backend
- Spring MVC, Spring ORM, Spring Security
- Hibernate
- MySQL
- Jakarta EE APIs (Servlet, JSP)
- Cloudinary SDK (upload hình ảnh)
- Google API Client

### Frontend
- React 19
- Bootstrap 5
- React Router v7
- React-Leaflet + Google Map React
- Axios
- React Toastify

---
---

## 👥 Contributors
- HFing (Nguyễn Thái Hoàng) – Backend (Team Lead)
- ThangTB5 – Frontend
