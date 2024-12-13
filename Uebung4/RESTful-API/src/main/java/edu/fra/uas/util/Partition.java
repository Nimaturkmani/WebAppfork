package edu.fra.uas.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

//final: The class cannot be extended.
//partition: breaking a list or dataset into sublists
public final class Partition<T> extends AbstractList<List<T>> {

    private final List<T> list; //list: Stores the original list
                                //A copy is made during initialization to ensure immutability
    private final int chunkSize; //The maximum size of each sublist.

    public Partition(List<T> list, int chunkSize) {
        this.list = new ArrayList<>(list);
        this.chunkSize = chunkSize;
    } //Copies the provided list to ensure the original list is not modified

    //static: Allows creating a Partition object without directly calling the constructor
    //<T>: Ensures type compatibility.
    public static <T> Partition<T> ofSize(List<T> list, int chunkSize) {
        return new Partition<>(list, chunkSize);
    }// Usage: creates a Partition object that divides list into sublists of size chunkSize.

    @Override
    public int size() {
        return (int) Math.ceil((double) list.size() / (double) chunkSize); // Type Casting (int) or (double)
    }
    //Math.ceil: Rounds up to ensure all elements are covered, even if the last chunk is smaller.
    //list.size() =10: Total number of elements in the original list
    //chunkSize = 3: Maximum number of elements per chunk.
    // return 4
    @Override
    public List<T> get(int index) {
        int start = index * chunkSize;
        int end = Math.min(start + chunkSize, list.size());
        if (start > end) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of the list range <0," + (size() - 1) + ">");
        }
        return new ArrayList<>(list.subList(start, end));
    }
    
}
