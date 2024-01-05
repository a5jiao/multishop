// List class
public class List{
    String name;
    String location;
    int order;
    public List(String name, String location){
        this.name = name;
        this.location = location;
    } //end List

    // Returns name of list
    public String getName(){
        return name;
    } //end getName

    // Returns directory/location of list
    public String getLocation(){
        return location;
    } //end getLocation

    // Returns the list as a String Array using Files.java
    public String[] arrayList(){
        return Files.loadStringArr(location);
    } //end arrayList

    // Returns the size of the list
    public int listSize(){
        String[] arr = Files.loadStringArr(location);
        return arr.length;
    } //end listSize
} //end List class