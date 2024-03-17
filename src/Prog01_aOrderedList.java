// Necessary imports for file I/O, array management, and handling specific exceptions.
import java.io.File;                      // Used for file operations, to represent files and directories in the file system.
import java.io.FileNotFoundException;     // Exception thrown when attempting to read from a file that does not exist.
import java.io.PrintWriter;               // Enables writing formatted data to an output stream, such as a file.
import java.util.Scanner;                 // Facilitates reading input from various sources, including files.
import java.util.Arrays;                  // Provides static methods to manipulate arrays, such as sorting and searching.
import java.util.NoSuchElementException;  // Thrown by various accessor methods to indicate the element being requested does not exist.
/**
 * Main class for the Car OrderedList Manager. Handles file input/output and processes lines to perform add or delete operations on a list of cars.
 *
 * CSC 1351 Programming Project No 1
 * Section 05
 *
 * @author Keaton Hodgson
 * @since 03/17/2024
 */
public class Prog01_aOrderedList {
    public static void main(String[] args) {
        try {
            Scanner fileScanner = GetInputFile("Enter input filename: ");
            aOrderedList<Car> orderedList = new aOrderedList<>();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                processLine(orderedList, line);
            }

            PrintWriter output = GetOutputFile("Enter output filename: ");
            outputCustomFormat(output, orderedList);
            output.close();

            System.out.println("Operations completed and output successfully written to file.");
        } catch (FileNotFoundException e) {
            System.out.println("Program execution cancelled or file not found.");
        }
    }
    /**
     * Processes a line from the input file to either add a new Car to the list or delete one based on its make and year.
     *
     * CSC 1351 Programming Project No 1
     * Section 05
     *
     * @author Keaton Hodgson
     * @since 03/17/2024
     *
     * @param orderedList The ordered list of cars.
     * @param line The line read from the input file.
     */
    private static void processLine(aOrderedList<Car> orderedList, String line) {
        String[] tokens = line.split(",");
        if ("A".equalsIgnoreCase(tokens[0])) {
            Car car = new Car(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
            orderedList.add(car);
        } else if ("D".equalsIgnoreCase(tokens[0]) && tokens.length == 3) {
            String make = tokens[1];
            int year = Integer.parseInt(tokens[2]);
            orderedList.reset();
            while (orderedList.hasNext()) {
                Car car = orderedList.next();
                if (car.getMake().equals(make) && car.getYear() == year) {
                    orderedList.removeLastIterated(); // Correct method call to remove the last iterated element
                    break;
                }
            }
        }
    }
    /**
     * Outputs the final list of cars to a specified PrintWriter in a custom format.
     *
     * CSC 1351 Programming Project No 1
     * Section 05
     *
     * @author Keaton Hodgson
     * @since 03/17/2024
     *
     * @param output The PrintWriter to write output to.
     * @param orderedList The ordered list of cars.
     */
    private static void outputCustomFormat(PrintWriter output, aOrderedList<Car> orderedList) {
        output.println("Number of cars: " + orderedList.size());
        orderedList.reset();
        while (orderedList.hasNext()) {
            Car car = orderedList.next();
            output.println("Make: " + car.getMake());
            output.println("Year: " + car.getYear());
            output.printf("Price: $%,d\n\n", car.getPrice());
        }
    }
    /**
     * Prompts the user for the filename of an input file and returns a Scanner object for reading from it.
     * If the file does not exist, the user is prompted to re-enter a filename or cancel the operation.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author Keaton Hodgson
     * @since 03/17/2024
     *
     * @param userPrompt The prompt to display to the user.
     * @return Scanner object for reading the input file.
     * @throws FileNotFoundException If the user cancels the operation or file cannot be found/opened.
     */
    public static Scanner GetInputFile(String userPrompt) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        System.out.println(userPrompt);
        while (true) {
            String filename = userInput.nextLine();
            File file = new File(filename);
            if (file.exists()) {
                return new Scanner(file);
            } else {
                System.out.println("File specified <" + filename + "> does not exist. Would you like to continue? <Y/N>");
                if (userInput.nextLine().equalsIgnoreCase("N")) {
                    throw new FileNotFoundException("User cancelled the operation.");
                }
                System.out.println("Please enter a valid filename:");
            }
        }
    }
    /**
     * Prompts the user for the filename of an output file and returns a PrintWriter object for writing to it.
     * If the file cannot be created or opened, the user is prompted to re-enter a filename or cancel the operation.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @param userPrompt The prompt to display to the user.
     * @return PrintWriter object for writing to the output file.
     * @throws FileNotFoundException If the user cancels the operation or file cannot be found/opened.
     */
    public static PrintWriter GetOutputFile(String userPrompt) throws FileNotFoundException {
        Scanner userInput = new Scanner(System.in);
        System.out.println(userPrompt);
        while (true) {
            String filename = userInput.nextLine();
            try {
                return new PrintWriter(filename);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file specified <" + filename + ">. Would you like to continue? <Y/N>");
                if (userInput.nextLine().equalsIgnoreCase("N")) {
                    throw new FileNotFoundException("User cancelled the operation.");
                }
                System.out.println("Please enter a valid filename:");
            }
        }
    }
}


/**
 * Represents a car with make, year, and price attributes. Allows for comparison based on make and year.
 *
 * CSC 1351 Programming Project No 1
 * Section 02
 *
 * @author Keaton Hodgson
 * @since 03/17/2024
 */
class Car implements Comparable<Car> {
    // Member variables with in-line comments
    private String make; // The manufacturer of the car
    private int year; // The manufacturing year of the car
    private int price; // The price of the car in USD

    // Constructor
    /**
     * Constructs a Car instance with specified make, year, and price.
     *
	 * CSC 1351 Programming Project No 1
	 * Section 02
	 *
	 * @author Keaton Hodgson
	 * @since 03/17/2024
     *
     * @param make The make of the car
     * @param year The year the car was made
     * @param price The price of the car
     */
    public Car(String make, int year, int price) {
        this.make = make;
        this.year = year;
        this.price = price;
    }
    // Getters

    /**
     * Retrieves the make of the car.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return The make of the car.
     */
    public String getMake() {
        return make;
    }
    /**
     * Retrieves the manufacturing year of the car.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return The year of the car.
     */
    public int getYear() {
        return year;
    }
    /**
     * Retrieves the price of the car.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return The price of the car in USD.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Provides a string representation of the Car object, including its make, year, and price.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author Keaton Hodgson
     * @since 03/17/2024
     *
     * @return A string that represents the Car object.
     */
    @Override
    public String toString() {
        return "Make: " + make + ", Year: " + year + ", Price: $" + price + ";";
    }

    /**
     * Compares this Car object with another Car object for order. Cars are first compared by their make. 
     * If the makes are the same, then their years are compared.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author Keaton Hodgson
     * @since 03/17/2024
     *
     * @param other The Car object to be compared.
     * @return A negative integer, zero, or a positive integer as this Car is less than, equal to, 
     *         or greater than the specified Car.
     */
    @Override
    public int compareTo(Car other) {
        int makeCompare = this.make.compareTo(other.make);
        if (makeCompare != 0) return makeCompare;
        return Integer.compare(this.year, other.year);
    }
}
/**
 * A generic ordered list class for managing a dynamic array of Comparable objects.
 * This class supports adding and removing elements while maintaining the order of elements.
 * It also provides iterator methods for traversing through the list.
 *
 * CSC 1351 Programming Project No 1
 * Section 02
 *
 * @author Keaton Hodgson
 * @since 03/17/2024
 */
class aOrderedList<T extends Comparable<T>> {
    final int SIZE_INCREMENTS = 20; // Defines the size increments for the array expansion.
    private T[] oList; // The array storing the ordered list of Comparable objects.
    private int numObjects; // Tracks the number of objects in the ordered list.
    private int curr; // Iterator index, indicating the current position for iteration.

    /**
     * Constructs an empty ordered list with an initial capacity.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     */
    @SuppressWarnings("unchecked")
    public aOrderedList() {
        this.oList = (T[]) new Comparable[SIZE_INCREMENTS];
        this.numObjects = 0;
    }
    /**
     * Adds a new object to the ordered list in its correct position to maintain order.
     * 
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @param newObject The new Comparable object to be added.
     */
    public void add(T newObject) {
        if (numObjects == oList.length) {
            oList = Arrays.copyOf(oList, oList.length + SIZE_INCREMENTS);
        }
        int insertIndex = 0; // Index to insert the new object at

        while (insertIndex < numObjects && oList[insertIndex].compareTo(newObject) < 0) {
            insertIndex++;
        }
        System.arraycopy(oList, insertIndex, oList, insertIndex + 1, numObjects - insertIndex);
        oList[insertIndex] = newObject;
        numObjects++;
    }
    /**
     * Returns the element at the specified position in this list
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @param index
     * @return
     */
    public T get(int index) {
        if (index >= 0 && index < numObjects) {
            return oList[index];
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + numObjects);
        }
    }
    /**
     *  Returns the number of elements in this list
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return
     */
    public int size() {
        return numObjects;
    }
    /**
     * Checks if the list is empty.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return boolean True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numObjects == 0;
    }

    /**
     * Determines if there are more elements in the list to iterate over.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return boolean True if there is at least one more element, false otherwise.
     */
    public boolean hasNext() {
        return curr < numObjects;
    }
    
    /**
     * Returns the next element in the iteration and increments the iterator index.
     * Throws NoSuchElementException if the iteration has no more elements.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return T The next element in the list.
     * @throws NoSuchElementException If there are no more elements to iterate.
     */
    public T next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements.");
        return oList[curr++];
    }

    /**
     * Removes the element at the specified index in the list. Adjusts the array accordingly to fill the gap.
     * Throws IndexOutOfBoundsException if the index is out of bounds.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @param index The index of the element to remove.
     * @throws IndexOutOfBoundsException If index is outside the list bounds.
     */
    public void remove(int index) {
        if (index < 0 || index >= numObjects) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + numObjects);
        }
        System.arraycopy(oList, index + 1, oList, index, numObjects - index - 1);
        numObjects--;
        oList[numObjects] = null; // Help with garbage collection by nulling the last element.
    }

    /**
     * Removes the last element returned by the next() method from the list.
     * Throws IllegalStateException if this method is called without calling next() first.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @throws IllegalStateException If next() was not called before this remove method.
     */
    public void removeLastIterated() {
        if (curr <= 0) {
            throw new IllegalStateException("removeLastIterated() called without a next() call");
        }
        this.remove(--curr);
    }

    /**
     * Provides a string representation of the list, formatting the elements enclosed in brackets and separated by commas.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     * 
     * @return String A string representation of the list elements.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < numObjects; i++) {
            sb.append(oList[i]);
            if (i < numObjects - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Resets the iterator to the start of the list, so the next call to next() will return the first element.
     *
     * CSC 1351 Programming Project No 1
     * Section 02
     *
     * @author keatonhodgson
     * @since 03/17/2024
     */
    public void reset() {
        curr = 0;
    }
}



