package BooleanModel;

/**
 *
 * @author muham
 */
import java.util.*;
import java.io.*;

public class indexer {
    public String path = System.getProperty("user.dir") + "\\src\\dataset\\";
    private final Scanner FILE;
    public int filecount;
    public final HashSet<String> Stopword;
    private final HashMap<String, Integer> dwords;
    private final String filepath[];
    private final String delims = "[ -]+";
    
    
    public indexer(int s){
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
    
    public InvertedIndex makeindex(){
        InvertedIndex Index = new InvertedIndex(filecount);
        Scanner x;
        int tcount=0;                                                           //tcount count of distinct terms processed till now
        for(int i=0; i<filecount; i++){
            x = open(filepath[i]);
            String title= null, author;
            int position = 0; 
            int line = 0;
            while(x.hasNext()){
                String str = x.nextLine();
                if(line == 0 ){
                    title = str;
                    line++;
                }
                else if(line == 1){
                    author = str;
                    Index.stories[i]= new Story(title, author);
                    line++;
                }
                
                String tokens[] = str.split(delims);
                for (String token : tokens) {
                    token = preprocess(token);
                    if (!(Stopword.contains(token)) && token.length() > 1){
                        if (!(Index.terms.containsKey(token))){                 //first occourance of the word
                            term Term = new term(tcount,token);
                            Term.set_postingList(position, i);
                            Index.terms.put(token, Term);
                            position++;
                            tcount++;
                        } else {                                                //word aready in the list
                            term Term = Index.terms.get(token);
                            Term.set_postingList(position, i);
                            Index.terms.replace(token, Term);
                            position++;
                        }
                    } else {                                                    //is a stopword
                        position++;
                    }
                }
            }
        }
        return Index;
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
