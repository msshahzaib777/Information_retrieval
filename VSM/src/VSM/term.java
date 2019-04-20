package VSM;


import java.util.*;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author muham
 */

public class term {
    private final int termID;
    private int df;                                          //document count in which term appears
    private String term;
    
    public term(int a, String s){
        term = s;
        termID = a;
        df =1;
    }
    
    public term(int a, term t){
        term = t.getterm();
        termID = a;
        df = t.getDf() + 1;
    }
    
    public int getID(){
        return this.termID;
    }
    
    public int getDf(){
        return this.df;
    }
    
    public String getterm(){
        return this.term;
    }
    public void print(){
        System.out.println("############################################\nTerm ID:" + termID +" Doc frequency:"+ df);
    }
    
    public void update(boolean b){
        if(b){
            this.df++;
        }
    }
            
}
