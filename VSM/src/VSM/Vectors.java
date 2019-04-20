package VSM;

import java.util.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shahzaib
 */

public class Vectors{
    public int count;
    public int max;
    public Story [] stories;
    public HashMap<String, term> values;
    public HashMap<Integer, String> Keys;
    public ArrayList<Double> DocVec[];
    
    Vectors(int size){
        count = size;
        stories = new Story[count];
        Keys = new HashMap();
        values = new HashMap();
        DocVec = new ArrayList[count];
        for(int i=0; i<count; i++){
            DocVec[i] = new ArrayList<>(); 
        }
    }
    
    public void add(int i, int dcount){
        for(int j=DocVec[i].size(); j<dcount; j++){
            DocVec[i].add(0.0);
        }
        DocVec[i].add(1.0);
    }
    
    public void update(int i, int count){
        for(int j=DocVec[i].size(); j<count; j++){
            DocVec[i].add(0.0);
        }
        if(DocVec[i].size() == count){
            DocVec[i].add(1.0);
        }
        else{
            double val = DocVec[i].get(count);
            val++;
            DocVec[i].set(count, val);
        }
    }

    public boolean check(int i, int dcount){
        boolean b = false;
        if(DocVec[i].get(dcount)  == 1){
            b = true;
        }
        return b;
    }
}