import java.util.*;
import java.io.*;
public class Main{
    
    // Define all Scanner, ArrayList, and other variables
    public static Scanner wordScan = new Scanner(System.in);
    public static Scanner numScan = new Scanner(System.in);
    public static ArrayList<List> lists = new ArrayList<List>();
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static int choice;
    public static int listNum;
    public static boolean access, chrono, alpha, cate;
    public static String[] category = {"Produce", "Meat", "Dairy", "Seafood", "Bread & Bakery", "Deli",
            "Other Foods", "Health Care", "Household", "Miscellaneous"};
    
    public static void main(String[] args){
        // Create a string array to hold the options of the menu
        String[] menu = {"Access a list", "Create a new list", "Delete an existing list", "Merge two lists together", "Settings", "Quit"};
        // Create string array for second menu options
        String[] menu2 = {"Return to menu", "Quit"};
        // Create string array for third menu options
        String[] menu3 = {"Sort in chronological order", "Sort alphabetically", "Sort by category"};
        // While loop that runs for the entire functionality of the program
        while(true){
            access = false;
            // Calls function that extracts all files from local Lists folder and creates a list holding the files
            updateArrayList();
            // Calls function that extracts all items from items.txt file and creates a list holding the hitems
            updateItem();
            // Function that updates the settings of the shopping list program
            settings();
            System.out.println("\nWelcome to your multifunctional shopping list!\n");
            // Loop for the selection menu
            while(true){
                System.out.println("What would you like to do? (Enter Number)\n");
                printIndexedArr(menu);
                choice = numScan.nextInt();
                System.out.println();
                // If a valid choice is selected, the program will proceed by breaking the loop. Else it will loop again
                if (choice<1 || choice > menu.length){
                    System.out.println("Invalid input. Please try again.\n");
                } else{
                    break;
                }
            }

            // Accessing a list
            if (choice == 1){
                // If no lists exist: No list can be accessed
                if(lists.size() == 0){
                    System.out.println("\nYou have no lists. Would you like to create a new list? (Yes/No)");
                    String ans = wordScan.nextLine();
                    // If a new list is created, createList function is called
                    if (ans.toLowerCase().startsWith("y")){
                        System.out.println();
                        createList();
                    // Otherwise access is set to false since we are not accessing a list
                    } else{
                        access = false;
                    }
                // Case where lists do exist so they can be accessed
                } else {
                    accessList();
                }
            // choice to create list, so the function to create a list is called
            } else if(choice == 2){
                createList();
            // Choosing to delete a list
            } else if (choice == 3){
                // If no lists exist
                if(lists.size() == 0){
                    System.out.println("\nYou have no lists to delete. Would you like to create a new list? (Yes/No)");
                    String ans = wordScan.nextLine();
                    // If the user chooses to create a new list, createlist() is called, otherwise access is set to false
                    if (ans.toLowerCase().startsWith("y")){
                        System.out.println();
                        createList();
                    } else{
                        access = false;
                    }
                // Case where lists do exist so the deleteList function is called with -1 as a parameter (see below)
                } else {
                    deleteList(-1);
                }
            // Choice to merge two lists together
            } else if(choice == 4){
                // Cases where list number is insufficient to merge
                if(lists.size() < 2){
                    // Case where there are no lists
                    if(lists.size() == 0){
                        System.out.println("\nYou have no lists to merge. Would you like to create a new list? (Yes/No)");
                    // Case where only one list exists
                    } else {
                        System.out.println("\nTwo lists are required for merging. You currently only have one list. Would you like to create a new list? (Yes/No)");
                    }
                    String ans = wordScan.nextLine();
                    // If user chooses to create a new list, createList function is called, otherwise access is set to false
                    if (ans.toLowerCase().startsWith("y")){
                        System.out.println();
                        createList();
                    } else{
                        access = false;
                    }
                // Case where there are enough lists for merge function to be used so mergeList is called
                } else {
                    mergeList();
                }
            // User chooses settings option, which allows the selection of how lists are sorted
            } else if(choice == 5){
                while(true){
                    System.out.println("How would you like to sort your lists? (Enter Number)\n");
                    printIndexedArr(menu3);
                    choice = numScan.nextInt();
                    System.out.println();
                    // Updates settings.txt file accordingly to how lists are sorted so that the settings are saved upon rerun
                    if (choice<1 || choice > menu3.length){
                        System.out.println("Invalid input. Please try again.\n");
                    } else if(choice == 1){
                        Files.saveString("Data/settings.txt", "0");
                        break;
                    } else if(choice == 2){
                        Files.saveString("Data/settings.txt", "1");
                        break;
                    } else if(choice == 3){
                        Files.saveString("Data/settings.txt", "2");
                        break;
                    }
                }
                // Calls settings function to update settings
                settings();
                System.out.println("Settings Updated");
            // User choses to quit
            } else {
                System.exit(0);
            }
            System.out.println();
            // The following section only runs if access is false, since now the user has to decibe if they want to quit or return to menu
            while(access == false){
                System.out.println("What would you like to do?\n");
                printIndexedArr(menu2);
                choice = numScan.nextInt();
                if(choice < 1 || choice > menu2.length){
                    System.out.println("\nInvalid input. Please try again.\n");
                } else if (choice == 2){
                    System.exit(0);
                } else{
                    break;
                }
            }
        }
}// end main
        // Settings function
        public static void settings(){
            String[] mode = Files.loadStringArr("Data/settings.txt");
            // 0 indicates chronological order
            if(mode[0].equals("0")){
                chrono = true;
                alpha = false;
                cate = false;
            // 1 indicates alphabetical order
            } else if (mode[0].equals("1")){
                chrono = false;
                alpha = true;
                cate = false;
            // Last case is 2, which indicates sorting by category
            } else{
                chrono = false;
                alpha = false;
                cate = true;
            }
        }//end settings

        // Accessing a list
        public static void accessList(){
            access = true;
            boolean fill = true;
            while(true){
                printArrayList();
                // Asks user what list to access
                System.out.println("\nWhich list do you want to access?");
                choice = numScan.nextInt();
                System.out.println();
                // If statement to make sure a valid list is selected
                if(choice < 1 || choice > lists.size()){
                    System.out.println("Invalid list selected. Please try again.");
                // Case where selected list exists however it is empty
                } else if(lists.get(choice-1).arrayList()[0].equals("") && lists.get(choice-1).arrayList().length ==
                        1){
                    listNum = choice;
                    fill = false;
                    System.out.println("Your list is empty. Would you like to add to your list? (Yes/No)");
                    String ans = wordScan.nextLine();
                    // If user wants to add to the list, addList is called with the listNum passed as a parameter
                    if (ans.toLowerCase().startsWith("y")){
                        System.out.println();
                        addList(listNum);
                    // If not, access is set to false and the loop breaks
                    } else{
                        access = false;
                    }
                    break;
                // Case where valid list is selected and it has contents
                } else{
                    listNum = choice;
                    System.out.println(lists.get(listNum-1).name + ":");
                    // Case where cate is false, meaning the lists are not sorted by category
                    if(cate == false){
                        printArr(lists.get(listNum-1).arrayList());
                    // Case where cate is true and the lists are sorted by category
                    } else{
                        printCategoryArr(lists.get(listNum-1));
                    }
                    break;
                }
            }
            // Loop that runs while fill is true
            while(fill){
                // Asks user what they want to do
                System.out.println("\nWhat would you like to do?\n");
                System.out.println("1-Add to list\n2-Remove items from list\n3-Return to menu\n4-Quit");
                choice = numScan.nextInt();
                // If statement to make sure choice is valid, and also calls the respective functions for user's choice
                if(choice < 1 || choice > 4){
                    System.out.println("\nInvalid input. Please try again.\n");
                } else if (choice == 1){
                    addList(listNum);
                    break;
                } else if (choice == 2){
                    removeList(lists.get(listNum-1));
                    break;
                } else if (choice == 4){
                    System.exit(0);
                } else{
                    break;
                }
            }
        }// end accessList

        // Creating a list
        public static void createList(){
            try{
                // Ask user for the name of list
                System.out.println("\nWhat is the name of your new list?");
                String input = wordScan.nextLine();
                File newFile = new File("Data/Lists/"+input+".txt");
                System.out.println();
                // Case where list is successfully created and file is added into Lists folder
                if(newFile.createNewFile()){
                    System.out.println("\nNew list successfully created: " + newFile.getName());
                    lists.add(new List(newFile.getName(), newFile.getAbsolutePath()));
                // List already exists
                } else{
                    System.out.println("List already exists.");
                }
            // Catch error
            } catch (IOException e){
                System.out.println("An error occured.");
                e.printStackTrace();
            }
        }// end createList

        // Deleting list, which takes in the index of the list to be deleted as a parameter
        public static void deleteList(int x){
            boolean restart = true;
            // If x is -1 that means the list to be deleted has yet to be specified, so this case obtains the list to be deleted
            if(x == -1){
                while(true){
                    printArrayList();
                    System.out.println("\nWhich list do you want to delete?");
                    choice = numScan.nextInt();
                    if(choice < 1 || choice > lists.size()){
                        System.out.println("Invalid list selected. Please try again.");
                    } else {
                        listNum = choice-1;
                        break;
                    }
                }
            // Case where list to be deleted is already known, so we assign that index to listNum
            } else{
                listNum = x;
            }
            // While loop which deletes each item belonging to the list
            while(restart == true){
                int match = 0;
                for (int i = 0; i < items.size(); i++){
                    if(items.get(i).getIndex() == listNum){
                        int index = items.indexOf(items.get(i));
                        items.remove(index);
                        match++;
                        restart = true;
                        break;
                    }
                }
                if(match == 0){
                    restart = false;
                }
            }
            // Deleting the list and removing it completely
            String fileLocation = lists.get(listNum).getLocation();
            File listToDelete = new File(fileLocation);
            listToDelete.delete();
            lists.remove(listNum);
            String[] voidArr = new String[0];
            Files.saveFile("Data/Items/items.txt", voidArr);
            for(int i = 0; i < items.size(); i++){
                if(items.get(i).getIndex() > listNum){
                    items.get(i).listIndex--;
                }
                items.get(i).saveItem();
            }
            System.out.println("\nList successfully deleted.");
        }// end deleteList

        // Merging lists
        public static void mergeList(){
            String[] mergeList2 = new String[0];
            int listIndex1, listIndex2;
            boolean safe = true;
            boolean safe2 = true;
            while(true){
                // Selecting first list to merge
                System.out.println("\nSelect your first list to merge\n");
                printArrayList();
                choice = numScan.nextInt();
                // Case where list does not exist
                if(choice < 1 || choice > lists.size()){
                    System.out.println("Invalid list selected. Please try again.");
                // Case where list is empty
                } else if(lists.get(choice-1).arrayList()[0].equals("")){
                    System.out.println("You cannot merge a blank list.");
                    safe = false;
                // Case where list is valid for merging
                } else {
                    listIndex1 = choice-1;
                    safe = true;
                    break;
                }
            }
            // This part creates a new list called mergeList2, which is a list of the lists to be merged with the above selected removed
            // while maintaining order
            if(safe == true){
                mergeList2 = new String[lists.size()-1];
                int counter = 0;
                int runIndex = 0;
                for(int i = 0; i < lists.size(); i++){
                    if(i == listIndex1){
                        counter--;
                        runIndex = i;
                    } else{
                        mergeList2[counter] = lists.get(i).getName();
                    }
                    counter++;
                }
                // Selecting second list to merge
                while(true){
                    System.out.println("\nSelect your second list to merge\n");
                    printIndexedArr(mergeList2);
                    choice = numScan.nextInt();
                    // Case where list is invalid
                    if(choice < 1 || choice > mergeList2.length){
                        System.out.println("Invalid list selected. Please try again.");
                    } else {
                        listIndex2 = choice-1;
                        safe2 = true;
                        break;
                    }
                }
                if(listIndex2 >= runIndex){
                    listIndex2++;
                }
                // If second list is blank, safe2 is falase
                if(lists.get(listIndex2).arrayList()[0].equals("")){
                    System.out.println("You cannot merge a blank list.");
                    safe2 = false;
                }
                // Second list is not blank therefore lists can be merged
                if(safe2 == true){
                    String[] mergedList = new String[lists.get(listIndex1).listSize() +
                            lists.get(listIndex2).listSize()];
                    try{
                        while(true){
                            // Name the new list and create file
                            System.out.println("\nWhat is the name of your new list?");
                            String input = wordScan.nextLine();
                            File newFile = new File("Data/Lists/"+input+".txt");
                            int counterI = 0;
                            // If file is created
                            if(newFile.createNewFile()){
                                // Add to arraylist
                                lists.add(new List(newFile.getName(), newFile.getAbsolutePath()));
                                // Loop that adds to the new list
                                for(int i = 0; i < mergedList.length; i++){
                                    if(i < lists.get(listIndex1).listSize()){
                                        mergedList[i] = lists.get(listIndex1).arrayList()[i];
                                        counterI++;
                                    } else{
                                        mergedList[i] = lists.get(listIndex2).arrayList()[i- counterI];
                                    }
                                    // Adding all the items to the items arraylist however now they belong to the new list
                                    if(items.get(i).getIndex() == listIndex1){
                                        items.add(new Item(items.get(i).name(), items.get(i).getCategory(),
                                                lists.get(lists.size()-1), lists.size()-1));
                                    } else if(items.get(i).getIndex() == listIndex2){
                                        items.add(new Item(items.get(i).name(), items.get(i).getCategory(),
                                                lists.get(lists.size()-1), lists.size()-1));
                                    }
                                }
                                Files.saveFile(newFile.getAbsolutePath(), mergedList);
                                System.out.println("\nNew list successfully created: " + newFile.getName());
                                break;
                            } else{
                                System.out.println("\nList already exists. Try again.");
                            }
                        }
                    // Catch error
                    } catch (IOException e){
                        System.out.println("\nAn error occured.");
                        e.printStackTrace();
                    }
                    // Ask user if they want to delete the old lists
                    System.out.println("\nWould you like to delete the lists " + lists.get(listIndex1).getName() + " and " + lists.get(listIndex2).getName() + "? (Yes/No)");
                            String input = wordScan.nextLine();
                    if(input.toLowerCase().startsWith("y")){
                        deleteList(listIndex1);
                        if(listIndex1 < listIndex2){
                            listIndex2--;
                        }
                        deleteList(listIndex2);
                    } else{
                    }
                }
            }
        }// end mergeList

        // Adding to lists which takes in an int as the index of the list to be added to
        public static void addList(int x){
            // If no shopping lists exist
            if(lists.size() == 0){
                System.out.println("You currently have no shopping lists. Would you like to create one? (Yes/No)");
                String ans = wordScan.nextLine();
                if (ans.toLowerCase().startsWith("y")){
                    System.out.println();
                    createList();
                } else{
                    return;
                }
            // If x is -1 then the list to be added to has yet to be specified
            } else if (x == -1){
                // Obtain list to be added to
                while (true){
                    printArrayList();
                    System.out.println("\nWhich list would you like to add to?");
                    choice = numScan.nextInt();
                    System.out.println();
                    if(choice < 1 || choice > lists.size()){
                        System.out.println("Invalid list selected. Please try again.");
                    } else {
                        listNum = choice;
                        break;
                    }
                }
            // Case where x is a valid list index
            } else{
                listNum = x;
            }
            // Adding to the list
            while(true){
                String itemName;
                // Obtaining name of item
                while (true){
                    System.out.println("\nEnter the name of the item you would like to add (Name cannot contain '@'):\n");
                    itemName = wordScan.nextLine();
                    // Name cannot include @ since it is used for sorting purposes in items.txt
                    if(itemName.contains("@")){
                        System.out.println("\nYour item name cannot contain the symbol '@'.");
                    } else{
                        break;
                    }
                }
                // Sorting the item into various category
                while (true){
                    System.out.println("\nWhat category does your item belong to?");
                    printIndexedArr(category, true);
                    int itemCategory = numScan.nextInt();
                    // Case where category is invalid
                    if(itemCategory < 0 || itemCategory > (category.length-1)){
                        System.out.println("Invalid category selected. Please try again.");
                    // Category is valid and assigned to item
                    } else {
                        items.add(new Item(itemName, itemCategory, lists.get(listNum-1), listNum-1));
                        System.out.println("\nItem successfully added.");
                        break;
                    }
                }
                // Ask if user would like to add more items
                System.out.println("\nWould you like to add more items? (Yes/No)");
                String ans = wordScan.nextLine();
                // Anything that does not start with 'y' breaks the loops
                if(!ans.toLowerCase().startsWith("y")){
                    break;
                // Anything starting with 'y' (assuming = yes) causes the loop to go again
                } else {
                    System.out.println();
                }
            }
        }// end addList

        // Removing item from lists
        public static void removeList(List x){
            int counterX = 0;
            int removeIndex = -1;
            System.out.println();
            printIndexedArr(x.arrayList());
            // Obtaining number of item to be deleted
            System.out.println("\nWhich item do you want to delete?");
            choice = numScan.nextInt();
            // If item selected is invalid
            if(choice < 1 || choice > x.arrayList().length){
                System.out.println("Invalid item selected. Please try again.");
            // Item selected is valid and therefore will be deleted
            } else{
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x){
                        counterX++;
                        if(counterX == choice){
                            removeIndex = i;
                        }
                    }
                }
            }
            // Removing item from items.txt and items arraylist
            items.remove(removeIndex);
            String[] voidArr = new String[0];
            Files.saveFile("Data/Items/items.txt", voidArr);
            for(int i = 0; i < items.size(); i++){
                items.get(i).saveItem();
            }
            System.out.println("\nItem successfully removed.");
        } //end removeList

        // Printing list by category, printCategoryArr takes in a list
        public static void printCategoryArr(List x){
            // cc is an array that stores booleans
            boolean[] cc = {false, false, false, false, false, false, false, false, false, false};
            // loop that goes through each item
            for(int i = 0; i < items.size(); i++){
                // if the list of the item matches the list we are printing
                if(items.get(i).getList() == x){
                    // various statements if the category of the item is a certain number n, the nth value in cc is true
                    // Any element in cc being true indicates that there are products in the list that fall under the category
                    // that the element in cc represents
                    if(items.get(i).getCategory() == 1){
                        cc[0] = true;
                    } else if(items.get(i).getCategory() == 2){
                        cc[1] = true;
                    } else if(items.get(i).getCategory() == 3){
                        cc[2] = true;
                    } else if(items.get(i).getCategory() == 4){
                        cc[3] = true;
                    } else if(items.get(i).getCategory() == 5){
                        cc[4] = true;
                    } else if(items.get(i).getCategory() == 6){
                        cc[5] = true;
                    } else if(items.get(i).getCategory() == 7){
                        cc[6] = true;
                    } else if(items.get(i).getCategory() == 8){
                        cc[7] = true;
                    } else if(items.get(i).getCategory() == 9){
                        cc[8] = true;
                    } else{
                        cc[9] = true;
                    }
                }
            }
            // If there is produce items
            if(cc[0] == true){
                // print all produce
                System.out.println("\nProduce: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 1){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // If there is meat items
            if(cc[1] == true){
                // print all meat
                System.out.println("\nMeat: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 2){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there is dairy items
            if(cc[2] == true){
                // print all dairy
                System.out.println("\nDairy: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 3){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there is seafood items
            if(cc[3] == true){
                // print all seafood
                System.out.println("\nSeafood: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 4){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are bread & bakery items
            if(cc[4] == true){
                // print all bb items
                System.out.println("\nBread & Bakery: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 5){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are deli items
            if(cc[5] == true){
                // print all deli
                System.out.println("\nDeli: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 6){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are other food items
            if(cc[6] == true){
                // print all other food items
                System.out.println("\nOther Foods: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 7){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are health care items
            if(cc[7] == true){
                // print all health care items
                System.out.println("\nHealth Care: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 8){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are household
            if(cc[8] == true){
                // print all household
                System.out.println("\nHousehold: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 9){
                        System.out.println(items.get(i).name());
                    }
                }
            }
            // if there are misc
            if(cc[9] == true){
                // print all misc
                System.out.println("\nMiscellaneous: ");
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getList() == x && items.get(i).getCategory() == 0){
                        System.out.println(items.get(i).name());
                    }
                }
            }
        } // end printCategoryArr

        // Function that prints any String array in a list format
        public static void printIndexedArr(String[] x){
            for(int i = 0; i < x.length; i++){
                System.out.println((i+1) + "-" + x[i]);
            }
        }// end printIndexedArr

        // Function designed specifically for printing category, prints list in order except the last element starts with 0
        public static void printIndexedArr(String[] x, boolean y){
            int counter = 1;
            for(int i = 0; i < x.length; i++){
                if(i == (x.length - 1)){
                    counter = 0;
                }
                System.out.println(counter + "-" + x[i]);
                counter++;
            }
        }// end printIndexedArr

        // Function that prints a String array
        public static void printArr(String[] arr){
            // Case where alpha is true, the array gets sorted alphabetically
            if(chrono == false && alpha == true){
                int currentMinIndex;
                // Sorting alphabetically
                for (int i = 0; i < arr.length - 1; i++){
                    currentMinIndex = i;
                    for (int j = i + 1; j < arr.length; j++){
                        if(arr[j].compareToIgnoreCase(arr[currentMinIndex])<0){
                            currentMinIndex = j;
                        }
                    }
// swap if needed
                    if (i != currentMinIndex){
                        String temp = arr[currentMinIndex];
                        arr[currentMinIndex] = arr[i];
                        arr[i] = temp;
                    }
                }
            }
            // Printing array regardless of what happens above
            for(int i = 0; i < arr.length; i++){
                System.out.println(arr[i]);
            }
        }// end printArr

        // Function that updates lists arraylist by reading from Lists folder
        public static void updateArrayList(){
            lists.clear();
            File folder = new File("Data/Lists");
            File[] fileList = folder.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                lists.add(new List(fileList[i].getName(), fileList[i].getAbsolutePath()));
            }
        }// end updateArrayList

        // Function that updates items arraylist by reading from items.txt
        public static void updateItem(){
            items.clear();
            File folder = new File("Data/Lists");
            File[] fileList = folder.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                try{
                    PrintWriter writer = new PrintWriter(lists.get(i).getLocation());
                    writer.print("");
                    writer.close();
                } catch (IOException e) {
                }
            }
            // reading in the items from items.txt
            // items are written in the items.txt file with the following format: ITEMNAME@CATEGORYNUM@LISTINDEX@
            String item[] = Files.loadStringArr("Data/Items/items.txt");
            if(item[0] != ""){
                for (int i = 0; i < item.length; i++){
                    int index = Integer.valueOf(item[i].substring(item[i].indexOf("@", item[i].indexOf("@")+1)+1,
                            item[i].indexOf("@", (item[i].indexOf("@", item[i].indexOf("@")+1)+1))));
                    updateFile(item, i, "Data/Items/items.txt");
                    items.add(new Item(item[i].substring(0,item[i].indexOf("@")),
                            Integer.valueOf(item[i].substring(item[i].indexOf("@")+1, item[i].indexOf("@",
                                    item[i].indexOf("@")+1))), lists.get(index), index));
                }
            }
        }// end updateItem

        // Function that updates a file
        public static void updateFile(String[] arr, int index, String fileLocation){
            // Removing the given indexed element from the array by creating a new array with that element removed
            String[] newArr = new String[arr.length - 1];
            for (int i = 0, x = 0; i < arr.length; i++) {
                if (i == index) {
                    continue;
                }
                newArr[x++] = arr[i];
            }
            // Saving the new array to the file location
            Files.saveFile(fileLocation, newArr);
        }// end updateFile

        // Function that prints an indexed list of all the lists
        public static void printArrayList(){
            int counter = 1;
            for(List l: lists){
                System.out.println(counter + "-" + l.getName());
                counter++;
            }
        }
    }// end class Main
