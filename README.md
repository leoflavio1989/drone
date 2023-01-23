# Build and Run
The Project can be build and run with the following maven commands:
- mvn clean install.
- mvn spring-boot:run.

# Database (DB)
The DB will run in memory using 'H2database' configured in the application.yml file and can be accessed from [DB](http://localhost:8080/h2-console/login.jsp).
- JDBC URL: jdbc:h2:mem:mydb
- user: musala
- password: musala

The initial data and structure of the DB will be loaded via 'Flyway' when the application starts, loading the files V0__init_create_tables.sql and V1__insert_default_values.sql from resources/db/migration.

By default the application load 4 drone (IDs: 1,2,3,4) and 8 medications (IDs: 1,2,3,4,5,6,7,8).

# OpenApi
- In the project is used openapi and all controller class is generated automatically by spring, in folder 'delegate' we overwrite the methods to implement the logical of the application.

# Test
- You can use the attached postman collection to test the application or through [swagger](http://localhost:8080/swagger-ui.html).

# Conjecture
- The drones available to load are the drones with state 'IDLE' and 'LOADING'.
- When the drone goes to 'DELIVERED' status, the drone's medications list is 0.
- The order of the states of a drone is (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING) so a drone cannot go to a state that is not the one consecutive to the current state.
- The application will check the battery level of the drones every 5 minutes and save this information the DB.