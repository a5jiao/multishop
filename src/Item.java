import java.io.*;

// Item class
class Item{
    String name;
    int category;
    List x;
    int listIndex;
    public Item(String name, int category, List x, int listIndex){
        this.name = name;
        this.category = category;
        this.x = x;
        this.listIndex = listIndex;
        convert(name);
        saveItem();
    } //end Item

    // Returns category of item
    public int getCategory(){
        return category;
    } //end getCategory

    // Returns the list the item belongs to
    public List getList(){
        return x;
    } //end getList

    // Returns name of item
    public String name(){
        return name;
    } //end name

    // Returns the listIndex of item
    public int getIndex(){
        return listIndex;
    } //end getIndex

    // Function that saves the item into items.txt
    public void saveItem(){
        // Sorting method as mentioned in Main.java
        String newItem = name + "@" + category + "@" + listIndex + "@";
        String arr[] = Files.loadStringArr("Data/Items/items.txt");
        int index = arr.length;
        if(arr[0].equals("") && arr.length == 1){
            index = 1;
        } else{
            index = arr.length+1;
        }
        String newArr[] = new String[index];
        for(int i = 0; i < index-1; i++){
            newArr[i] = arr[i];
        }
        newArr[newArr.length-1] = newItem;
        Files.saveFile("Data/Items/items.txt", newArr);
    }// end saveItem

    // Functionless code
    public void convert(String str){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(x.getLocation(), true)));
            out.println(str);
            out.close();
        } catch (IOException e) {
        }
    }// end convert
} //end Item class