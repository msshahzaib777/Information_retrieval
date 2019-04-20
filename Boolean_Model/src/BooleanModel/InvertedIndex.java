package BooleanModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shahzaib
 */
import BooleanModel.term;
import BooleanModel.Story;
import java.util.*;

public class InvertedIndex {
    public int count;
    public Story [] stories;
    public HashMap<String, term> terms;
    
    InvertedIndex(int size){
        count = size;
        stories = new Story[count];
        terms = new HashMap<>();
    }
    
    public void print(){
        //Getting Collection of values from HashMap 
        Collection<term> values = terms.values();
        //Creating an ArrayList of values  
        ArrayList<term> listOfTerms = new ArrayList<>(values);
        for(term t: listOfTerms){
            t.print();
        }
    }
    
}
