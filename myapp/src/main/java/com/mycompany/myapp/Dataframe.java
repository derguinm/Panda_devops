package com.mycompany.myapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Dataframe {
    private Object[][] dataframe;
    private ArrayList<String> colonne_name ;
    private ArrayList<String> colonne_type ;

    public Object[][] getDataframe() {
        return dataframe;
    }

    public void setDataframe(Object[][] dataframe) {
        this.dataframe = dataframe;
    }

    public ArrayList<String> getColonne_name() {
        return colonne_name;
    }

    public void setColonne_name(ArrayList<String> colonne_name) {
        this.colonne_name = colonne_name;
    }

    public ArrayList<String> getColonne_type() {
        return colonne_type;
    }

    public void setColonne_type(ArrayList<String> colonne_type) {
        this.colonne_type = colonne_type;
    }


    public Dataframe(ArrayList<String> colonne_name, ArrayList<Object> elements) {
        this.colonne_name = colonne_name ;
        int n_c = colonne_name.size() ;
        int nb_t = elements.size() ;
        int n_l = nb_t/n_c ;
        dataframe = new Object[n_l][n_c] ;

        for (int i = 0; i < n_l; i++) {
            for (int j = 0; j < n_c; j++) {
                dataframe[i][j] = elements.get(j + n_l*i) ; 
            }
            colonne_type.add( elements.get(n_l*i).getClass().getName() );
        
        }
    }

    public Dataframe(String cvsFilePath){
        File file = new File(cvsFilePath);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            
            String[] line = br.readLine().split(",");
            Collections.addAll(colonne_type,line);
            
            line = br.readLine().split(",");
            Collections.addAll(colonne_name,line);
            String tmps;
            int i = 0;
            while((tmps=br.readLine())!=null){
                line = tmps.split(",");
                for (int j = 0; j < line.length; j++) {
                    dataframe[i][j]=line[j];
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
