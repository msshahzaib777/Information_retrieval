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

public class term {
    public int filecount = 50;
    public int termID;
    public String term;
    public postingList list[];
    public int cf;                                          //frequency of term in overall collection
    public int df;                                          //document count in which term appears
    
    public term(int a, String c){
        termID = a;
        term = c;
        list = new postingList[filecount];
        for(int i=0; i<filecount; i++){
            list[i] = new postingList();
        }
    }
    
    public void set_postingList(int p, int docID){
        if(list[docID].positions.size() <= 0){
            df++;
        }
        list[docID].addposition(p);
        cf++;
    }
    
    public void print(){
        System.out.println("############################################\nTerm ID:" + termID +" Term:"+ term + " Cumu freq: "+ cf +" Doc frequency:"+ df);
        int i=0;
        for(postingList p: list){
            p.print(i);
            i++;
        }
    }
    
    public int[] getdocID(){
        LinkedList<Integer> docID = new LinkedList();
        for(int i=0; i<filecount; i++){
            if(!(list[i].positions.isEmpty())){
                docID.add(i);
            }
        }
        return docID.stream().mapToInt(k->k).toArray();
    }
    
    public int[] getNOTdocID(){
        LinkedList<Integer> docID = new LinkedList();
        for(int i=0; i<filecount; i++){
            if(list[i].positions.isEmpty()){
                docID.add(i);
            }
        }
        return docID.stream().mapToInt(k->k).toArray();
    }
    
    public int gettf(int i){
        return list[i].tf;
    }
}
