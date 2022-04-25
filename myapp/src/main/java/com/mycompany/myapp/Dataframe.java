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
        int n_c = colonne_name.size() ;
        int nb_t = elements.size() ;
        int n_l = nb_t/n_c ;
        dataframe = new Object[n_l][n_c] ;

        for (int i = 0; i < n_l; i++) {
            for (int j = 0; j < n_c; j++) {
                dataframe[i][j] = elements.get(i + n_l*j) ; 
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

            int n_l = colonne_size(csvFilePath) ;
            int n_c = line.length ;
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


    public static int colonne_size(String fileName) {

        Path path = Paths.get(fileName);
  
        int lines = 0;
        try {
  
            // much slower, this task better with sequence access
            //lines = Files.lines(path).parallel().count();
  
            lines = (int) Files.lines(path).count();
  
        } catch (IOException e) {
            e.printStackTrace();
        }
  
        return lines;
  
    }
    
}
