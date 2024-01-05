// This class has methods for saving and loading arrays from files.
import java.io.*; // imports all of the input/output classes, including PrintWriter,
// FileWriter, BufferedReader, FileReader, etc. They will help us work with the files.
public class Files {
    //************** String Array Methods *********************//
    public static void saveFile(String filename, String[] array) {
// This method takes the name of the file you will save to and the array
// you will be saving as parameters.
        try { // try allows java to try to execute some code, and if an error occurs,
// instead of halting the entire program, you run whatever is in catch.
// The technical term for these errors is Java throwing an exception,
// so the 'catch' code catches the exception and does something with it.
            PrintWriter file = new PrintWriter(new FileWriter(filename));
// This line creates a FileWriter object which, as the name suggests,
// allows you to write to a file. However, we use the PrintWriter
// class and the FileWriter object we just created to make a PrintWriter
// object called "file". Basically, this allows us to use regular print
// commands to format what we want to print to put in the file.
            for(int i=0; i<array.length; i++){ // Cycle through the elements in the array and...
                file.println(array[i]); // print each element to the file.
// Note: We usually print to "System.out", the console, but
// here we are printing to "file".
            }
            file.close(); // Once the data has been sent to the file, close it.
        } catch (IOException e) { // e is the exception
            System.out.println(e); // this line just prints the exception to the console
        }
    }//end saveFile
    public static void saveString(String filename, String array) {
// This method takes the name of the file you will save to and the array
// you will be saving as parameters.
        try { // try allows java to try to execute some code, and if an error occurs,
// instead of halting the entire program, you run whatever is in catch.
// The technical term for these errors is Java throwing an exception,
// so the 'catch' code catches the exception and does something with it.
            PrintWriter file = new PrintWriter(new FileWriter(filename));
// This line creates a FileWriter object which, as the name suggests,
// allows you to write to a file. However, we use the PrintWriter
// class and the FileWriter object we just created to make a PrintWriter
// object called "file". Basically, this allows us to use regular print
// commands to format what we want to print to put in the file.
            file.println(array); // print each element to the file.
// Note: We usually print to "System.out", the console, but
// here we are printing to "file".
            file.close(); // Once the data has been sent to the file, close it.
        } catch (IOException e) { // e is the exception
            System.out.println(e); // this line just prints the exception to the console
        }
    }//end saveFile
    public static String[] loadStringArr(String filename) {
//Note: This is not a void method. We will be returning a String array.
//Also, the filename of the file you will load into the array you are
// going to return is required.
        String addLines="";
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
// As the name suggests, FileReader is a class that allows you to read
// files. BufferedReader just speeds up the reading process.
            while (file.ready()) { // .ready() just checks to see if the file
// has data ready to be read
                addLines += file.readLine()+","; // The lines from the file are
//added to the addLines string, separated by commas.
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return addLines.split(","); // .split(",") takes a string and splits it into
// a string array whereever a comma is present. We placed commas between
// each line read from the file, so each line in the final is turned into
// an element in the array this method returns.
    }//end loadStringArr
    //********************** Number Array Methods ******************//
    public static void saveFile(String filename, int[] array) {
        try {
            PrintWriter file = new PrintWriter( new FileWriter(filename) );
            for(int i=0; i<array.length; i++){
                file.println("" + array[i]);
// An empty string is added to each integer element in array
// to convert it to a string. It isn't necessary for things to
// run, but it probably isn't a bad idea to have.
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }//end saveFile
    public static void saveInt(String filename, int array) {
        try {
            PrintWriter file = new PrintWriter( new FileWriter(filename) );
            file.println("" + array);
// An empty string is added to each integer element in array
// to convert it to a string. It isn't necessary for things to
// run, but it probably isn't a bad idea to have.
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }//end saveFile
    public static void saveFile(String filename, double[] array) {
        try {
            PrintWriter file = new PrintWriter(new FileWriter(filename));
            for (int i = 0; i < array.length; i++) {
                file.println("" + array[i]);
            }
            file.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }//end saveFile
    public static int[] loadIntArr(String filename) {
        String addLines = "";
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            while (file.ready()) {
                addLines += file.readLine() + ",";
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] tempStringArray = addLines.split(",");
// .split() gives us a string array, but we have to convert it to
// an integer array.
        int[] tempIntArray = new int[tempStringArray.length]; // make a temporary
// integer array the same length as the temp string array
        for (int i = 0; i < tempIntArray.length; i++) {
            tempIntArray[i] = Integer.parseInt(tempStringArray[i]);
// Go through each element in the string array, convert it to an
// integer, then add it to the integer array.
        }
        return tempIntArray; // return your newly made integer array.
    }//end loadIntArr
    public static double[] loadDoubleArr(String filename) {
        String addLines = "";
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));
            while (file.ready()) {
                addLines += file.readLine() + ",";
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String[] tempStringArray = addLines.split(",");
        double[] tempDoubleArray = new double[tempStringArray.length];
        for (int i = 0; i < tempDoubleArray.length; i++) {
            tempDoubleArray[i] = Double.parseDouble(tempStringArray[i]);
        }
        return tempDoubleArray;
    }//end loadIntArr
}//end Files Class