# TeamBuilder - Automated Team Formation for Competitions

Welcome to **TeamBuilder**, a Spring Boot application designed to automate the process of forming teams for competitions such as hackathons. This tool automatically creates balanced teams based on participant data and sends notifications about team assignments, all with minimal manual intervention.

## Features

- **Automatic Team Formation**: Organize participants into balanced teams.
- **Participant Notifications**: Automatically notify participants of their team assignments via email.
- **Customizable Settings**: Configure various parameters for team formation and notifications such as size of the team.
- **User-Friendly Interface**: Web-based interface for managing participants and team formation.

### Technical Details 

The application is build using strategy pattern to add multiple strategies for building team. At current supports domain specific strategy i.e team is build taking one individual from each domain one-by-one till required size of team is achieved.
Can be easily extended to Years Of Experience based strategy.

The application uses Kafka for notfication thus making it loosely coupled and highly extensible by allowing asynchronous processing and buffering at the same time it being highy reliable.



## Getting Started

To get started with TeamBuilder, follow these steps:

### Prerequisites

- **Java 17+**: Ensure you have Java 17 or later installed on your system.
- **Maven**: This application uses Maven for dependency management and build.

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/sanakamal1405/teamBuilder.git
   cd teambuilder

2. **Clone again for notfication Service**
    ```bash
   git clone https://github.com/sanakamal1405/teamBuilder.git notificationService
   cd notificationService
   

3. **Open from IntelliJ or any Code Editor.**

4. **Run Kafka on your System .**

5. **Run both services seperately.**

6. **Add partcipants details and buildTeam of desired size !!**

   ## Screenshots

   **1. Db Schema :**

   
![db_schema](https://github.com/user-attachments/assets/bef2bd07-6d41-4b5b-a30b-960f48a33c69)



**2. Populated Data :**


![db1_1](https://github.com/user-attachments/assets/ae4d6309-e154-41f5-a15f-fad397b1bd2d)


![team_table](https://github.com/user-attachments/assets/8fc6e21b-20e6-4bd4-bc6e-d78c925e9194)


**3. Hitting API :**


![participantAPI](https://github.com/user-attachments/assets/783ae46d-313f-4067-9736-919efb4e367d)



![buildTeamAPI](https://github.com/user-attachments/assets/46666fc0-912f-41c8-8954-4c4de0e20b74)



**4. Notification Alert :**


![notification](https://github.com/user-attachments/assets/47ea4cca-40dd-4e3b-bdb6-a5d46eee9409)








