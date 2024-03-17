Car OrderedList Manager

The Car OrderedList Manager is a Java-based application designed to manage a dynamically sized ordered list of car objects. This application allows for the addition and deletion of car objects based on user input from a file, and supports operations through a generic aOrderedList class that handles objects implementing the Comparable interface. This README provides a comprehensive guide on the project structure, setup, usage, and customization.

Project Structure

The project consists of the following main components:

Prog01_aOrderedList.java: The entry point of the application. It includes the main method, file input/output handling, and the logic to process add and delete operations from the input file.

Car.java: Defines the Car class with properties make, year, and price. Implements the Comparable<Car> interface to allow ordering in the aOrderedList.

aOrderedList.java: A generic class that manages a dynamically sized ordered list of objects that extend Comparable<T>. It supports add, delete, and iterator functionalities.

Setup

To set up the Car OrderedList Manager, ensure you have Java installed on your system. This application was developed and tested with Java SE 11, but it should be compatible with Java SE 8 and newer versions.

Clone the repository or download the source code.

Open the project in your favorite Java IDE (e.g., Eclipse, IntelliJ IDEA) or compile the code from the command line.
Compilation
To compile the application from the command line, navigate to the project directory and run:

javac Prog01_aOrderedList.java
This will generate .class files for each Java file.

Usage

The application is designed to read from an input file containing operations (add or delete) and car information (make, year, price), and then output the resulting list to a specified output file.

Input File Format

The input file should contain one operation per line:

To add a car: A,<Make>,<Year>,<Price>
To delete a car by index: D,<Make>,<Year>
Example input file:

css
Copy code
A,Kia,2007,4000
A,Honda,2009,10000
D,Kia,2007
A,Toyota,1999,1800
D,Toyota,1999
Running the Application
Run the application by executing the Prog01_aOrderedList class. If using the command line, navigate to the project directory and run:

Copy code

java Prog01_aOrderedList
The application will prompt you for the input and output file names. Ensure the input file is formatted correctly and exists in the specified location.

Output

The application writes the final list of cars to the output file in the following format:

makefile
Copy code
Number of cars: <size>
Make: <Make>
Year: <Year>
Price: $<Price>

...
Each car is separated by a blank line for clarity.

Customization

You can customize the Car class to include additional properties or implement a different ordering by modifying the compareTo method. The aOrderedList class can be extended or modified to include additional list operations as needed.

Contributing

Contributions to the Car OrderedList Manager are welcome. Please feel free to fork the repository, make changes, and submit pull requests.

License
This project is open-sourced under the MIT License. See the LICENSE file for more details.
