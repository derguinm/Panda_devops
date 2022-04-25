package com.mycompany.myapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Dataframe {
    private Object[][] dataframe;
    private ArrayList<String> colonne_name ;
    private ArrayList<String> colonne_type ;
    private int n_l ;
    private int n_c ;
        
    

    public Object[][] getDataframe() {
        return dataframe;
    }

    public ArrayList<String> getColonne_name() {
        return colonne_name;
    }

    public ArrayList<String> getColonne_type() {
        return colonne_type;
    }

    public Dataframe(ArrayList<String> colonne_name, ArrayList<Object> elements) {
        this.colonne_name = colonne_name ;
        this.colonne_type = new ArrayList<String>() ;
        n_c = colonne_name.size() ;
        int nb_t = elements.size() ;
        n_l = nb_t/n_c ;
        dataframe = new Object[n_l][n_c] ;

        for (int i = 0; i < n_l; i++) {
            for (int j = 0; j < n_c; j++) {
                dataframe[i][j] = elements.get(j + n_c*i) ; 
            }
            colonne_type.add( elements.get(n_c*i).getClass().getName() );
        
        }
    }

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
                    dataframe[i][j] = line[j];
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

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
    print affiche la dataframe entièrement si nb est nul, un nombre |nb| de ligne, si nb est
    postif elle affiche les nb premières lignes sinon les dernières nb lignes dans leur ordre 
    respective.
    **/
    public void print(int nb){
        int begin , end ;
 
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
}
