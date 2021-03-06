package com.mycompany.myapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Dataframe{
    private Object[][] dataframe;
    private ArrayList<String> colonne_name ;
    private ArrayList<String> colonne_type ;
    private int n_l ;
    private int n_c ;
        
    public Object getObject(int i, int j){
        return this.dataframe[i][j];
    }

    
    /** 
     * @return int
     */
    public int getN_l(){
        return n_l;
    }

    
    /** 
     * @return int
     */
    public int getN_c(){
        return n_c;
    }

    
    /** 
     * @return the labels of the dataframe
     */
    public ArrayList<String> getColonne_name() {
        return colonne_name;
    }

    
    /** 
     * @return the type of the different label
     */
    public ArrayList<String> getColonne_type() {
        return colonne_type;
    }

    /**
     * Creates a Dataframe instance from 2 arraylists
     * @param colonne_name ArrayList containing the names of the columns 
     * @param elements ArrayList containing the elements column after column
     */
    public Dataframe(ArrayList<String> colonne_name, ArrayList<Object> elements) {
        this.colonne_name = colonne_name ;
        this.colonne_type = new ArrayList<String>() ;
        n_c = colonne_name.size() ;
        int nb_t = elements.size() ;
        n_l = nb_t/n_c ;
        dataframe = new Object[n_l][n_c] ;
        int k=0;
        for (int j = 0; j < n_c; j++) {
            for (int i = 0; i < n_l; i++) {
                dataframe[i][j] = elements.get(k++) ; 
            }
            colonne_type.add( elements.get(n_l*j).getClass().getName() );
        
        }
    }
    /**
     * 
     * @param csvFilePath
     */
    public Dataframe(String csvFilePath){
        this.colonne_type = new ArrayList<String>() ;
        this.colonne_name = new ArrayList<String>() ;
        
        File file = new File(csvFilePath);
        BufferedReader br;

        
        
        try {
            br = new BufferedReader(new FileReader(file));
            
            String[] line = br.readLine().split(",");
            Collections.addAll(colonne_type,line);
            
            line = br.readLine().split(",");

            n_l = colonne_size(csvFilePath) ;
            n_c = line.length ;
            dataframe = new Object[n_l][n_c] ;

            Collections.addAll(colonne_name,line);
            String tmps;
            int i = 0;
            while((tmps=br.readLine())!=null){
                line = tmps.split(",");
                for (int j = 0; j < line.length; j++) {
                    switch (colonne_type.get(j)){
                        case "java.lang.Integer":
                            dataframe[i][j] = Integer.parseInt(line[j]);
                            break;
                        case "java.lang.Float":
                            dataframe[i][j] = Float.parseFloat(line[j]);
                            break;
                        case "java.lang.String":
                        default : 
                            dataframe[i][j] = line[j];
                    }
                    
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    
    /** 
     * @param fileName is a csv file 
     * @return the number of line in the dataframe
     */
    // calcule le nombre de ligne a partir du fichier csv.
    public static int colonne_size(String fileName) {

        Path path = Paths.get(fileName);
  
        int lines = 0;
        try {
            lines = (int) Files.lines(path).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        return lines -2;
    }
    
    
    /** 
     * @param nb if nb is equal to zero, we print the whole dataframe,
     *           if nb is postive, we print the first nb lines of the dataframe,
     *           if nb is negative, we print the last |nb| lines of the dataframe. 
     */
    public void print(int nb){
        int begin , end ;

        if(this.colonne_name.size() == 0 || this.colonne_type.size() == 0){
            System.out.println("Void Dataframe");
            return;
        }

        if(Math.abs(nb) > n_l){
            System.out.println("Invalid argument in function Dataframe.print()");
            return;
        }

        if(nb > 0){
            begin = 0 ;
            end = nb ;
        }
        else if(nb < 0){
            begin = n_l + nb ;
            end = n_l ;
        }
        else{
            begin = 0 ;
            end = n_l ;
        }
        
        int i=0;
        for ( i = 0; i < n_c-1; i++) {
            System.out.print(colonne_name.get(i) + ",");
        }
        System.out.println(colonne_name.get(i));
        
        int j ;
        for (i = begin; i < end; i++) {
            for (j = 0 ; j < n_c-1; j++) {
                System.out.print(dataframe[i][j] + ",");
            }
            System.out.println(dataframe[i][j]);
        }

    }    

    /**
     * 
     * @param colonnesASelectionner = labels of column to select.
     * @return a new dataframe that contains thes column in param. 
     */
    public Dataframe selectFromLabel(ArrayList<String> colonnesASelectionner){
        //array servant ?? la cr??ation du nouveau dataframe
        ArrayList<Object> elements = new ArrayList<>();

        for (int i = 0; i < colonnesASelectionner.size(); i ++){
            
            int numColonne = 0;
            while (numColonne < this.n_c && !this.colonne_name.get(numColonne).equals(colonnesASelectionner.get(i))){
                numColonne++;
            }
            if (numColonne == this.n_c){
                System.err.println("Dataframe.Select() : Une des colonnes n'a pas ??t??e trouv??e : "+colonnesASelectionner.get(i));
                return null;
            }
            for (int ligne = 0; ligne < n_l; ligne++){
                elements.add(this.getObject(ligne, numColonne));
            }
        }
        return new Dataframe(colonnesASelectionner, elements);
    }


    
    /** 
     * @param list : list of line to select. 
     * @return Dataframe that contains only the lines in param.
     */
    public Dataframe  selectLine(ArrayList<Integer> list){
        ArrayList<Object> elements = new ArrayList<>();
        Iterator<Integer> it=list.iterator();
        int i ;

            for (int j = 0; j < this.getN_c(); j++) {
                while(it.hasNext()){
                    i = it.next() ;
                    if(i<0 || i>=this.getN_l()){
                        System.err.println("Dataframe.Select() : Une la ligne d'indice "+i+" n'a pas ??t??e trouv??e");
                        return null;
                    }
                    elements.add(this.getObject(i, j)) ;
                }
                it=list.iterator();        
            }

        return new Dataframe(colonne_name, elements) ;  
    }
    
    @Override
    public boolean equals(Object o){
        if (this.getN_c() != ((Dataframe)o).getN_c() || 
        this.getN_l() != ((Dataframe)o).getN_l() || 
        !this.colonne_name.equals(((Dataframe)o).colonne_name) ||
        !this.colonne_type.equals(((Dataframe)o).colonne_type)){
            return false;
        }
        for (int i = 0; i < this.getN_l(); i++){
            for (int j = 0; j < this.getN_c(); j++){
                if (!this.getObject(i, j).equals(((Dataframe)o).getObject(i, j))){
                    return false;
                }
            }
        }
        return true;
    }

    
    /** 
     * @param colonnesASelectionner : list of columns to select before getting statistics from them.
     * @param stat : is an integer that specify the type of statistics we ask.
     *                  stat = 0 => min, stat = 1 => max, stat = 2 => mean 
     * @return a list where every element represent the statistic for each column.
     */
    public ArrayList<Float> getStat(ArrayList<String> colonnesASelectionner, int stat){
        ArrayList<Float> statList = new ArrayList<Float>() ;
        Dataframe new_df =  this.selectFromLabel(colonnesASelectionner) ;
        for (int j = 0; j < new_df.getN_c(); j++) {
            ArrayList<Float> colonneValues=new ArrayList<>();
            for(int i=0; i<new_df.getN_l(); i++){
            
                colonneValues.add(((Integer)this.getObject(i,j)).floatValue());
            }
            switch(stat){
                case 0:
                statList.add(min(colonneValues));
                break;
                case 1:
                statList.add(max(colonneValues));
                break;
                case 2:
                statList.add(moy(colonneValues));
                break;
                default:
                System.out.println("unknown operation");   
            }
            
        }
        return statList;
    }
    
    /** 
     * @param list a list of the elements.
     * @return the minimum from the list.
     */
    public float min(ArrayList<Float> list ){
        float minv = Float.MAX_VALUE ;
        for(int i=0; i< list.size(); i++){
            if(minv > list.get(i))
                minv = list.get(i) ;
        }

        return minv ;
    }

    
    /** 
     * @param list a list of the elements.
     * @return the maximum from the list.
     */
    public float max(ArrayList<Float> list ){
        float maxv = Float.MIN_VALUE ;
        for(int i=0; i< list.size(); i++){
            if(maxv < list.get(i))
                maxv = list.get(i) ;
        }

        return maxv ;
    }

    
    /** 
     * @param list a list of the elements.
     * @return the mean of all the elements from the list.
     */
    public float moy(ArrayList<Float> list){
        Iterator<Float> it=list.iterator();
        float res=0;
        int nb=0;
        while(it.hasNext()){
            res+=it.next();
            nb++;
        }
        res=res/nb;
        return res;
    }
}
