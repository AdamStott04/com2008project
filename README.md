# COM2008 Group Project - Team 25
This repository contains the code for the COM2008 Group Project which tasked us to create a Java Swing application
that allows users to sign up, login, view different products, and order them. The application also allows the staff to fulfill these
orders and view the stock of the products. Managers are also responsible for staff promotion and demotion.
When the application is first run there will be 1 staff member and 1 manager in the system. To log in as staff the email is staff@staffmail.com and the password is password
To log in as a manager the email is manager@managermail.com and the password is password. 

### Instructions for installation:
1. Clone the repository
2. Open the project
3. Install Java 20 SDK
4. Install mysql-connector-java-8.0.27.jar as a library
5. Connect to the University VPN
6. Run App/App.java

### Common issues:
1. If when running App.java it says there is a problem with the drivers, then you need to reinstall your mysql-connector-java-8.0.27.jar in the libraries of the project.
2. If you are unable to connect to the VPN, then you will be unable to connect to the database. App.java will not run without a connection to the database.
3. If none of the project files are showing up then you need to go to project structure, then modules and add the root directory as a module for the project and then press Apply.
4. If you are asked to choose a compiler output, select the folder called "out".
