# Kitchen Danger Guard (KDG)

Kitchen Danger Guard (KDG) is an innovative solution designed to monitor kitchen environments, detect potential hazards, and notify users in real time. The system integrates IoT devices, a robust backend, and an intuitive web interface to ensure kitchen safety.

---

## Team Name: **Team 7**

**Team Members:**
- **Khaled Asfour**
- **Viktória Dobišová**
- **Nang Cherry Naw**
- **Deren Ozen**
- **Alec Tuffin**

---

# **Features**
- **Real-Time Monitoring**: Receives motion and temperature data from Arduino devices.
- **Notifications**: Alerts users via Email, and Web for safety-critical events. SMS in the future.
- **Predictive Analytics**: Flask-based machine learning models predict hazardous conditions.
- **Historical Insights**: Visualizes historical data using interactive charts.
- **User Management**: Provides user profile management with configurable notification preferences.

---

## **System Architecture**

### **1. Arduino Device**
- Sends motion and temperature data to the backend via HTTP.
- JSON Payload Example:
  ```json
  {
      "motionStatus": true,
      "temperatureValue": 75.5,
      "deviceId": 1
  }
  ```

### **2. Spring Boot Backend**
- Handles data ingestion, processing, and storage.
- Provides RESTful APIs for retrieving historical data and user notifications.
- Integrates with the Flask-based predictive analytics service.

### **3. Flask Prediction Service**
- Hosts machine learning models to predict hazardous conditions.
- Trains models on historical data for each device and provides predictions for future conditions.
- API Endpoint: `/prediction_data`

### **4. Frontend**
- Built with Thymeleaf and integrated with Chart.js for data visualization.
- Allows users to view real-time and historical data, and configure notification preferences.

---

## **Installation and Setup**

### **1. Prerequisites**
- **Java Development Kit (JDK):** Version 17 or higher.
- **Python:** Version 3.9 or higher.
- **PostgreSQL Database:** Version 8.0 or higher.
- **Arduino Device**: Configured with the provided sketch for data transmission.

### **2. Clone the Project**
```bash
git clone <https://github.com/dobisovaviktoria/KitchenDangerGuard-backend>
cd <project-directory>
```

### **3. Database Configuration**
- Create a PostgreSQL database:
  ```sql
  CREATE DATABASE postgres;
  ```
- Update the database credentials in `src/main/resources/application.properties`:
  ```properties
   spring.datasource.url=jdbc:postgresql://10.134.178.157:5432/postgres
   spring.datasource.username=team7
   spring.datasource.password=team7password!
  ```

### **4. Flask Prediction Service Setup**
- Navigate to the Flask service directory.
- Install dependencies:
  ```bash
  pip install -r requirements.txt
  ```
- Configure the PostgreSQL database in `database.py`:
  ```python
  DB_CONFIG = {
      "host": "10.134.178.157",
      "user": "team7",
      "password": "team7password!",
      "dbname": "postgres"
  }
  ```
- Start the Flask service:
  ```bash
  python app.py
  ```

### **5. Run the Spring Boot Backend**
- Build and run the project:
  ```bash
  ./mvnw clean install
  ./mvnw spring-boot:run
  ```

### **6. Access the Application**
- Web Application: [http://10.134.178.157](http://10.134.178.157)
---

## **Key Endpoints**

### **Data Ingestion**
- **POST** `/sensor-data`
   - Description: Receives motion and temperature data from Arduino devices.
   - Payload Example:
     ```json
     {
         "motionStatus": true,
         "temperatureValue": 75.5,
         "deviceId": 1
     }
     ```

### **Data Retrieval**
- **GET** `/api/history/stove-durations`
   - Description: Fetches hourly stove usage durations.
   - Parameters: `date` (String), `userId` (Integer).
- **GET** `/api/notifications/hourly`
   - Description: Retrieves notifications grouped by hour.
   - Parameters: `userId` (Integer), `date` (String).

### **Predictive Analytics**
- **GET** `/predict/<arduino_id>`
   - Description: Fetches predictive insights from the Flask API.
   - Response Example:
     ```json
     {
         "arduino_id": "1",
         "future_prediction": {
             "temperature": 80.0,
             "timeframe": "2 hours"
         }
     }
     ```

---

## **Dependencies**

### **Core Dependencies**
- **Spring Boot 3.x**: Core framework for building the backend application.
- **Flask**: Lightweight framework for hosting predictive models.
- **Spring Data JPA**: Simplifies database operations.
- **Python Libraries**:
   - `pandas`: Data preprocessing.
   - `scikit-learn`: Model training and prediction.
   - `joblib`: Model serialization.

### **Frontend**
- **Thymeleaf**: Server-side template engine.
- **Chart.js**: Renders interactive data visualizations.

### **Database**
- **PostgreSQL**

---

## **Integration with Arduino**

### **Hardware Requirements**
- Arduino ESP32/ESP8266
- MLX90614 Infrared Temperature Sensor
- PIR Motion Sensor

### **Arduino to Backend Communication**
- The Arduino device sends sensor data via HTTP to the backend's `/sensor-data` endpoint.
- Refer to the Arduino sketch for Wi-Fi configuration and data formatting.

---

## **Features by Role**

### **Admin**
- Manage users and devices.
- Monitor system logs and health.

### **Standard User**
- View real-time sensor data.
- Configure notification preferences.
- Analyze historical and predictive data.

---

## **Troubleshooting**

### **Common Issues**
1. **Backend Does Not Start**:
   - Verify that `application.properties` is correctly configured.
   - Ensure PostgreSQL service is running.
2. **Arduino Fails to Connect**:
   - Check Wi-Fi credentials and server IP.
   - Ensure the backend server is accessible from the Arduino device.
3. **Flask Service Fails to Respond**:
   - Verify PostgreSQL database connectivity.
   - Ensure the Flask service is running and accessible.
