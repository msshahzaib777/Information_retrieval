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
public class Story {
    String Title;
    String Author;
    
    public Story(String t, String a){
        Title = t;
        Author = a;
    }
    
    public void getDetails(){
        System.out.println("Story: \"" + Title +  "\"");
        System.out.println("\t" + Author);
    }
    public String getstory(){        
        return "Story: \"" + Title +  "\""+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Author + "\n";
    }
}
