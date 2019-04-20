package VSM;




import java.io.*;
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
public class Stats {
    public String path = System.getProperty("user.dir") + "\\src\\dataset\\";
    public int filecount;
    public final String delims = "[-]+";
    private final Scanner FILE;
    private final HashSet<String> Stopword;
    private final HashMap<String, Integer> dwords;
    public final String filepath[];
    public int minsize;
    public int docIDmin;
    public int maxsize;
    public int docIDmax;
    public int tterms, tsw, dterms;
    
    
    public Stats(int s){
        minsize = 0;
        maxsize = 0;
        tterms=0;
        tsw=0;
        dterms=0;
        filecount = s;
        Stopword = new HashSet(); 
        dwords= new HashMap<>(); 
        filepath = new String[s];
        FILE = open("Stopword-List.txt");
        while(FILE.hasNext()){
            Stopword.add(FILE.next().toLowerCase());
        }
        for(int i=0; i<filecount; i++){
            filepath[i] = "ShortStories\\" + (i+1) + ".txt";
        }
    }
    
    public void calculation(){
        for(int i=0; i<filecount; i++){
            int wordcount=0;
            Scanner x = open(filepath[i]);
            while(x.hasNext()){
                String str = x.next();
                String tokens[] = str.split(delims);
                for (String token : tokens) {
                    wordcount ++;
                    token = preprocess(token);
                    if (!(Stopword.contains(token)) && token.length() > 1) {
                        if (!(dwords.containsKey(token))){                      //first occourance of the word
                            dwords.put(token, i);
                            dterms++;
                        } else {                                                //word aready in the list
                            tterms++;
                        }
                    }
                    else if(token.length() > 1){                                //to remove empty string formed by str.replaceAll() function
                            tsw++;                                            //is a stopword
                    }
                }
            }
            if(i == 0){ 
                    minsize = wordcount;
                    docIDmin = i;
                    docIDmax = i;
                    maxsize = wordcount;
            }
            else{
                if(minsize > wordcount){
                    minsize = wordcount;
                    docIDmin = i;
                }
                if(maxsize < wordcount){
                    maxsize = wordcount;
                    docIDmax = i;  
                }
            }
        }
    }
    
    public void print(){
        System.out.println("Data Set Stats:");
        System.out.println("Documents Count            : "+ filecount);
        System.out.println("Total words in Dataset     : "+ (tterms+tsw) );
        System.out.println("Total Stopwords in Dataset : "+ tsw);
        System.out.println("Distinct words in Dataset  : "+ dwords.size());
        System.out.println("Size of Smallest file      : "+ minsize + " in " + (docIDmin ++));
        System.out.println("Size of Largest file       : "+ maxsize + " in " + (docIDmax++));
        System.out.println("Average Words per Document : "+ ((tterms+tsw)/filecount));
    }
    
    public String getstats(){
        
        return "<html><b>Documents Count            : </b>"+ filecount
                +"<br/><b>Total words in Dataset     : <b>"+ (tterms+tsw) 
                +"<br/><b>Total Stopwords in Dataset : <b>"+ tsw
                +"<br/><b>Distinct words in Dataset  : <b>"+ dwords.size()
                +"<br/><b>Size of Smallest file      : <b>"+ minsize + " in " + (docIDmin+1)
                +"<br/><b>Size of Largest file       : <b>"+ maxsize + " in " + (docIDmax+1)
                +"<br/><b>Average Words per Document : <b>"+ ((tterms+tsw)/filecount)
                + "</html>";
    }
   
    private String preprocess(String s){
        String processed;
        processed = s.replaceAll("[.,<>(){}\"?!'`~@#$%^&*;:/\\\\\\[\\]]+","");
        processed = processed.toLowerCase();
        return processed;
    }
    
    private Scanner open(String name){                           //general function to open file
        Scanner x = null;
        try{
            x= new Scanner(new File(path + name), "UTF-8");
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        return x;
    }
    
}



