import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays; // Import Arrays utility class
import java.util.NoSuchElementException;

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


// Car class definition
class Car implements Comparable<Car> {
    private String make;
    private int year;
    private int price;

    public Car(String make, int year, int price) {
        this.make = make;
        this.year = year;
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public int getPrice() {
        return price;
    }

    public String toString() {
        return "Make: " + make + ", Year: " + year + ", Price: " + price + ";";
    }
    public int compareTo(Car other) {
        int makeCompare = this.make.compareTo(other.make);
        if (makeCompare != 0) return makeCompare;
        return Integer.compare(this.year, other.year);
    }
}

// aOrderedList class definition updated for generic Comparable type
class aOrderedList<T extends Comparable<T>> {
    final int SIZE_INCREMENTS = 20;
    private T[] oList;
    private int numObjects;
    private int curr; // Iterator current position

    @SuppressWarnings("unchecked")
    public aOrderedList() {
        this.oList = (T[]) new Comparable[SIZE_INCREMENTS];
        this.numObjects = 0;
    }

    public void add(T newObject) {
        if (numObjects == oList.length) {
            oList = Arrays.copyOf(oList, oList.length + SIZE_INCREMENTS);
        }
        int insertIndex = 0;
        while (insertIndex < numObjects && oList[insertIndex].compareTo(newObject) < 0) {
            insertIndex++;
        }
        System.arraycopy(oList, insertIndex, oList, insertIndex + 1, numObjects - insertIndex);
        oList[insertIndex] = newObject;
        numObjects++;
    }
    // Returns the element at the specified position in this list
    public T get(int index) {
        if (index >= 0 && index < numObjects) {
            return oList[index];
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + numObjects);
        }
    }
    // Returns the number of elements in this list
    public int size() {
        return numObjects;
    }
    // Returns true if the array is empty and false otherwise
    public boolean isEmpty() {
        return numObjects == 0;
    }
    public boolean hasNext() { return curr < numObjects; }
    
    public T next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements.");
        return oList[curr++];
    }

    // Removes the element at the specified position in this list
    public void remove(int index) {
        if (index < 0 || index >= numObjects) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + numObjects);
        }
        System.arraycopy(oList, index + 1, oList, index, numObjects - index - 1);
        numObjects--;
        oList[numObjects] = null; // Help GC
    }
    public void removeLastIterated() {
        if (curr <= 0) {
            throw new IllegalStateException("removeLastIterated() called without a next() call");
        }
        this.remove(--curr);
    }

    // toString method to return the list contents in the specified format
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
    public void reset() { curr = 0; }

}


