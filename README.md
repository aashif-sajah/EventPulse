
# EventPulse Simulation System

## Overview
EventPulse is a simulation system designed for the production and consumption of tickets, tracking logs, and managing multiple simulations. This system is built as part of an **Object-Oriented Programming (OOP) Assignment**. The project integrates a robust backend developed with Java Spring Boot and an interactive frontend built with Angular.

**Student ID:** w2052938 | 20222329

---

## Project Structure

```plaintext
root/
│
├── backend/                # Backend directory (Java Spring Boot)
│   ├── src/                # Backend source code
│   ├── pom.xml             # Maven configuration for the backend
│   └── README.md           # Backend-specific README
│
├── frontend/               # Frontend directory (Angular)
│   ├── src/                # Frontend source code
│   ├── angular.json        # Angular configuration
│   └── README.md           # Frontend-specific README
│
└── README.md               # Root README
```

---

## Backend Setup (Spring Boot)

### Prerequisites
Ensure the following tools are installed on your system:
- [Java 17+](https://openjdk.java.net/) (JDK 17 or higher)
- [Maven](https://maven.apache.org/) for dependency management
- [MySQL](https://www.mysql.com/) for the database

### Running the Backend
1. Navigate to the `backend/` directory.
2. Ensure your MySQL database is running. Use the following configuration in your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventpulse_db
spring.datasource.username=root
spring.datasource.password=root
```

3. Build and run the Spring Boot application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

4. The backend server will start on [http://localhost:8080](http://localhost:8080).

### API Endpoints
- **POST /start-simulation**: Start a new ticket simulation.
- **GET /fetch-simulation-id**: Fetch the current simulation ID.
- **GET /fetch-logs/{simulationId}**: Fetch logs for a specific simulation.
- **GET /fetch-all-simulation-ids**: Fetch all simulation IDs.

### Database Schema
Ensure your MySQL database includes the following tables:
- **Ticket**: Stores ticket data.
- **LogEntry**: Stores logs generated during the simulation.

---

## Frontend Setup (Angular)

### Prerequisites
Ensure the following tools are installed on your system:
- [Node.js](https://nodejs.org/) (version 14 or higher)
- [Angular CLI](https://angular.io/cli) for managing Angular projects

### Running the Frontend
1. Navigate to the `frontend/` directory.
2. Install the dependencies:
```bash
npm install
```

3. Start the Angular development server:
```bash
ng serve
```

4. The frontend will start on [http://localhost:4200](http://localhost:4200).

### Connecting the Frontend to the Backend
By default, the frontend is configured to interact with the backend running on [http://localhost:8080](http://localhost:8080). If the backend runs on a different server or port, update the environment configuration in `environment.ts`:

```typescript
export const environment = {
  production: false,
  backendUrl: 'http://localhost:8080'
};
```

### Frontend Features
- **Start Simulation**: Initiate a simulation of ticket production and consumption.
- **View Logs**: View logs for any simulation based on the simulation ID.
- **View All Simulations**: View all past simulations.

---

## Testing

### Backend Tests
Run unit and integration tests for the backend using Maven:

```bash
mvn test
```

### Frontend Tests
Run unit and end-to-end tests for the frontend using Angular CLI:

```bash
ng test   # For unit tests
ng e2e    # For end-to-end tests
```

---

## Contribution Guidelines
1. Fork this repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and write appropriate tests.
4. Create a pull request with a detailed description of your changes.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

---

## Contact
If you have any questions, feel free to open an issue or contact the maintainers.

---

## Screenshots
Include relevant screenshots here to illustrate the system's functionality.

---

## Future Improvements
- Enhance the ticket production and consumption simulation logic.
- Implement real-time analytics for ticket simulations.
- Add user authentication and role-based access control.
- Optimize database schema for scalability.

---

**Developed as part of Object-Oriented Programming Assignment.**
