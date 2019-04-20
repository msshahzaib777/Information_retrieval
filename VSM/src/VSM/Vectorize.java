package VSM;

/**
 *
 * @author muham
 */
import java.util.*;
import java.io.*;

public class Vectorize{
    public String path = System.getProperty("user.dir") + "\\src\\dataset\\";
    private final Scanner FILE;
    public int filecount;
    private int max;
    private final HashSet<String> Stopword;
    private final String delims = "[ -]+";
    private final String filepath[];
    
    
    
    public Vectorize(int s){
        filecount = s;
        Stopword = new HashSet();
        filepath = new String[s];
        FILE = open("Stopword-List.txt");
        while(FILE.hasNext()){
            Stopword.add(FILE.next().toLowerCase());
        }
        for(int i=0; i<filecount; i++){
            filepath[i] = "ShortStories\\" + (i+1) + ".txt";
        }
    }
    
    public Vectors makeVector(){
        Vectors Index = new Vectors(filecount);
        max= 0;
        Scanner x;
        int tcount=0;                                                           //tcount count of total terms processed till now
        int dcount=0;                                                           //tcount count of distinct terms processed till now
        int scount=0;                                                           //tcount count of Stopwords processed till now
        for(int i=0; i<filecount; i++){
            x = open(filepath[i]);
            String title= null, author; 
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
                        if( !(Index.values.containsKey(token)) ){
                            term Term = new term(dcount, token);
                            Index.Keys.put(dcount, token);
                            Index.values.put(token, Term);
                            Index.add(i, dcount);
                            dcount++;
                            tcount++;
                        }
                        else{
                            int pos = Index.values.get(token).getID();
                            Index.update(i, pos);
                            if(Index.check(i, pos)){
                                term Term = new term(pos, Index.values.get(token));
                                Index.values.replace(token, Term);
                            }
                            tcount++;
                        }
                    }
                    else{
                        scount++;
                    }
                }
            }
            if(max < Index.DocVec[i].size()){
                max = Index.DocVec[i].size();
            }
            for(int k=0; k<Index.DocVec[i].size(); k++){
                double val = Index.DocVec[i].get(k);
                val = Math.log(val+1);
                Index.DocVec[i].set(k, val);
            }
        }
        Index.max = this.max;
        for(int f=0; f<filecount; f++){
            for(int t=Index.DocVec[f].size(); t<max; t++){
                Index.DocVec[f].add(0.0);
            }
        }
        for(int f=0; f<filecount; f++){
            for(int t=0; t<Index.DocVec[f].size(); t++){
                double val = Index.DocVec[f].get(t);
                String s = Index.Keys.get(t);
                double df = Index.values.get(s).getDf();
                val = val*(Math.log(filecount/df));
                Index.DocVec[f].set(t, val);
            }
            double mag = length(Index.DocVec[f]);
            for(int d=0; d<Index.DocVec[f].size(); d++){
                double val = Index.DocVec[f].get(d);
                val = val/mag;
                Index.DocVec[f].set(d, val);
            }
        }
        return Index;
    }
    
    public ArrayList<Double> query2vector(String q, Vectors Vec){
        ArrayList<Double> query = new ArrayList();
        ArrayList<Integer> positions = new ArrayList();
        for(int i=0; i<max; i++){
            query.add(0.0);
        }
        String tokens[] = q.split(delims);
        for (String token : tokens) {
            token = preprocess(token);
            if (!(Stopword.contains(token)) && token.length() > 1){
                if( Vec.values.containsKey(token) ){
                    int pos = Vec.values.get(token).getID();
                    positions.add(pos);
                    double val = query.get(pos);
                    val++;
                    query.set(pos, val);
                }
            }
        }
        for(int k=0; k<positions.size(); k++){
            int l = positions.get(k);
            String s = Vec.Keys.get(l);
            double df = Vec.values.get(s).getDf();
            double val = query.get(l);
            val = Math.log(val+1)*Math.log(filecount/df);
            query.set(l, val);
        }
        double mag = length(query);
        for(int d=0; d<positions.size(); d++){
            int l = positions.get(d);
            double val = query.get(l);
            val = val/mag;
            query.set(l, val);
        }
        return query;
    }
     
    private String preprocess(String s){
        String processed;
        processed = s.replaceAll("[.,<>(){}\"?!'`~@#$%^&*;:/\\\\\\[\\]]+","");
        processed = processed.toLowerCase();
        return processed;
    }
    
    public double length(ArrayList<Double> v){
        double length = 0;
        for(int i=0; i<v.size(); i++){
            double a = v.get(i);
            length += a*a;
        }
        length = Math.sqrt(length);
        return length;
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
