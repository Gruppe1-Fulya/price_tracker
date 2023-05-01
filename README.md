# Price Tracker Application Source Code

This is a Java desktop application built with a server-client model. This desktop application enables users to track prices on their watchlist and receive email alerts when certain conditions are met. Users can add items to their watchlist, and the app will periodically check the prices against the user's specified criteria. When the price meets the conditions, the app sends an email alert to notify the user. The app is useful for automating the price tracking process and saving time and effort for users who want to stay informed about price changes.

## Features

The application provides the following features:

- Server and client communication through RESTful API
- User authentication and authorization
- Scraping data from websites using Jsoup
- Storing data in a MySQL database
- Displaying data in a user-friendly way using JavaFX

## Technologies Used

The application is built using the following technologies:

- Java JDK 20
- JavaFX
- Spring
- MySQL
- Jsoup
- Google SMTP Server

## Installation and Setup

To run the application, follow these steps:

1. Install Java JDK 20 and MySQL on your system.
2. Clone the repository to your local machine.
3. Open the project in your Intellij Ultimate IDE. (others may require configuration.) Opening the server and client as separate projects produces healthier results.
4. Set up the MySQL database and configure the database properties in the `application.properties` file.
5. Build the project and run the server and client applications.

## Usage

Once the application is running, you can use the following steps to use the application:

1. Sign up or sign in to your account.
2. Search for a product you want to track and add it to your watchlist.
3. Set an alarm for the product by specifying the desired price or availability condition.
4. Get notified when the condition is met.

## License

This project is licensed under the MIT license. See the `LICENSE` file for more information.

## Notification Mail Template

![E-mail template](https://raw.githubusercontent.com/Gruppe1-Fulya/price_tracker/main/server/src/main/resources/static/mail_template.png)
