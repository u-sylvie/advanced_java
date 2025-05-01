# Java Quiz App

A simple Java-based quiz application that interacts with a PostgreSQL database. The app allows users to take a quiz, tracks their scores, and stores the results in a database. It also demonstrates how to connect Java to PostgreSQL and manage databases programmatically.

## Features

- **Database Management**: Automatically creates a PostgreSQL database if it doesn't exist and sets up necessary tables (`questions` and `scores`).
- **Dynamic Quiz**: The app fetches quiz questions from the database and presents them to the user.
- **Score Tracking**: After completing the quiz, the user's score is saved to the `scores` table.
- **Error Handling**: Includes error handling for various SQL exceptions, such as when the database already exists.

## Requirements

- **Java**: Version 8 or later
- **PostgreSQL**: Version 12 or later
- **JDBC Driver**: PostgreSQL JDBC driver

## Setup and Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/QuizApp.git
   cd QuizApp
