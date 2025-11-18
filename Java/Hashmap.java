package BWG220000;

import java.util.HashMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.LinkedList;



// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
    Comparator<Item> idComparator = (Item i1, Item i2) -> {
        return Integer.compare(i1.id, i2.id);
    };
    Comparator<Item> priceComparator = (Item i1, Item i2) -> {
        return Integer.compare(i1.price, i2.price);
    };

   

    // Add fields of MDS here
    public class Item implements Comparable<Item> {
        int id;
        int price;
        java.util.List<Integer> list;

        public Item(int i, int p, java.util.List<Integer> l) {
            this.id = i;
            this.price = p;
            this.list = l;
        }
        
        public int getPrice(Item i) {
            return i.price;
        }
        public int getId(Item i){
            return i.id;
        }

        public Item deepCopy() {
            return new Item(this.id, this.price, this.list);
        }

        @Override  //overiding the compareTo method to make the class item comparable by integer price
        public int compareTo(Item i) { 
            return Integer.compare(this.price, i.price);
        }
    }
       
    HashMap<Integer, Item> map = new HashMap<Integer, Item>(); //ID is integer, newest item
    //reverse map for descripton, put all in one set
    //HashMap findMap = new HashMap<Integer, TreeSet<Item>>();
    HashMap<Integer, TreeSet<Item>> descMap = new HashMap<Integer, TreeSet<Item>>(); //key is description number, list all items that contain that description number in the list
    //TreeSet<E> ts = new TreeSet<item>();
    // Constructors
    public MDS() {
    }
    
    

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(int id, int price, java.util.List<Integer> list) {

        if (find(id) == 0){ //id is not found, add new item
            Item i = new Item(id, price, list);
            //TreeSet<E> ts = new TreeSet<Item>(i);
            //ts.addAll(list);
            //findMap.put(id,ts);
            map.put(id, i);

            for (Integer currDesc : list) {
                TreeSet<Item> currDescSet = descMap.containsKey(currDesc) ? descMap.get(currDesc) : new TreeSet<Item>();


                currDescSet.add(i);
                descMap.put(currDesc, currDescSet);
            } 

            return 1;
        }

        else if( list == null || list.isEmpty() == true ) { //item is found, but no discription exists
            map.get(id).price = price;

            // 1. Get the original instance of Item which has this id
            for (Integer currDesc : list) {
                TreeSet<Item> currDescSet = descMap.containsKey(currDesc) ? descMap.get(currDesc) : new TreeSet<Item>();
                currDescSet.add( map.get(id));
                descMap.put(currDesc, currDescSet);
            } 
            // 2. Remove all references to that item in the descMap for all of the descriptions in that original list
            // 3. Add the new descriptions as was done above
            return 0;
        }

        else {

            for (Integer currDesc : list) {
                TreeSet<Item> currDescSet = descMap.containsKey(currDesc) ? descMap.get(currDesc) : new TreeSet<Item>();
                currDescSet.remove(map.get(id));
                descMap.remove(currDesc, currDescSet);
            } 

            map.get(id).price = price;
            map.get(id).list = list;

            for (Integer currDesc : list) {
                TreeSet<Item> currDescSet = descMap.containsKey(currDesc) ? descMap.get(currDesc) : new TreeSet<Item>();
                currDescSet.add(map.get(id));
                descMap.put(currDesc, currDescSet);
            } 

            return 0;

        }

    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) { //find if the item exists, if it does return the price
        if (map.get(id) == null) { return 0; }
        return map.get(id).price;

    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public int delete(int id) { //delete the item and update the data structures
        int total = 0;
        if (map.get(id) != null) { return 0; }

        java.util.List<Integer> list = map.get(id).list;

        for (Integer integer : list) {
            total =+ integer;
        } 

        /*for (Integer currDesc : list) {
                TreeSet<Item> currDescSet = descMap.containsKey(currDesc) ? descMap.get(currDesc) : new TreeSet<Item>(); //check if  bug
                currDescSet.remove(map.get(id));
                descMap.remove(currDesc, currDescSet);
            } */
        
        for (Integer currDesc : list) {
            descMap.get(currDesc).remove(map.get(id));
            descMap.remove(currDesc, descMap.get(currDesc));
        }
        map.remove(id);
        // 2. Remove all references to that item in the descMap for all of the descriptions in that original list

	    return total;
    }

    /* 
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public int findMinPrice(int n) {//find the minimum price that has the discription n

        if (descMap.get(n) != null ) {
            TreeSet<Item> setContainingN = descMap.get(n);
            //System.out.println(setContainingN.first().price);
            return setContainingN.first().price;
        }

	    return 0;
    }

    /* 
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public int findMaxPrice(int n) { //find the maximum price that has the discription n

        if (descMap.get(n) != null ) {
            TreeSet<Item> setContainingN = descMap.get(n);
           // System.out.println(setContainingN.last().price);
            //int min = setContainingN.first().price;
            return setContainingN.last().price;
        }

	    return 0;

    }

    /* 
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(int n, int low, int high) { //find the range of items of descriptor 2 and between price high and low
        int total = 0;
        if (descMap.get(n) != null ) {
        
            TreeSet<Item> setContainingN = descMap.get(n);
            for (Item item : setContainingN) {
                System.out.println(item.price);
                if (item.price > low && item.price < high) {
                    total++;
                }
            }
        }

        //System.out.println(total);
	return total;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public int removeNames(int id, java.util.List<Integer> list) { //remove specific descriptions in the item class, return the sum of the removed descriptors
        if (find(id) == 0) { return 0;}
        int sum = 0;
        for (int i  : list) {
            sum += i;
            descMap.remove(id, descMap.get(i));
            //map.get(id).list.remove(i);
        }



        //if tree set is empty, remove the map
        // 2. Remove all references to that item in the descMap for all of the descriptions in that original list
	return sum;
    }
}

