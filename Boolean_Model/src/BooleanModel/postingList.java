package BooleanModel;

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author muham
 */
import java.util.*;

public class postingList {
    public int tf;                                      //terms count in one doc
    public int docID;
    public LinkedList<Integer> positions;
    
    public postingList(){
        positions = new LinkedList();
        docID = -1;
    }
    
    public postingList(int id){
        docID = id;
        positions = new LinkedList();
    }
    
    public void addposition(int i){
        tf++;
        positions.add(i);
    }
    
    public void print(int i){
        if(!(positions.isEmpty())){
            System.out.print("Doc ID:"+ (i+1) +" "+ "Term Freq:" + tf + " Positions==>");
            positions.forEach((k) -> {
                System.out.print(k +" ");
            });
            System.out.println();
        }
    }
    
    public void printpositions(){
        positions.forEach((k) -> {
                System.out.print(k +" ");
            });
        System.out.println();
    }
}
