---

# Calculator Web App

This is a simple web application that allows users to perform basic arithmetic calculations using AJAX requests. The user interface provides input fields for operands, radio buttons to select the operation, and a "Calculate" button to trigger the calculation. The result of the calculation is displayed on the page.

## Technologies Used

- HTML
- CSS
- JavaScript
- Java (Server-side)

## Features

- User can enter numeric values for left and right operands.
- User can select an arithmetic operation (addition, subtraction, multiplication, division, or remainder).
- User can click the "Calculate" button to perform the calculation using AJAX.
- The result of the calculation is displayed on the page.

## Installation and Setup

1. **Clone the Repository:** Clone this repository to your local machine using the following command:

   ```
   git clone https://github.com/PooriaT/webApp_calculator.git
   ```

2. **Navigate to the Project Directory:** Use the `cd` command to navigate to the project directory:

   ```
   cd cwebApp_calculator
   ```

3. **Run the Java Server:** Compile and run the Java server code to handle AJAX requests. Open a terminal and execute the following commands:

   ```
   javac -cp json.jar RequestProcessor.java
   javac Main.java
   java -cp .:json.jar Main
   ```

   The server will start on port 8080.

4. **Access the Web App:** Open a web browser and navigate to the following URL to access the calculator web app:

   ```
   http://localhost:8080/calculator.html
   ```
