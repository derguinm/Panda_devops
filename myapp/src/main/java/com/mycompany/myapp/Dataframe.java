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

public class Dataframe{
    private Object[][] dataframe;
    private ArrayList<String> colonne_name ;
    private ArrayList<String> colonne_type ;
    private int n_l ;
    private int n_c ;
        
    public Object getObject(int i, int j){
        return this.dataframe[i][j];
    }

    public int getN_l(){
        return n_l;
    }

    public int getN_c(){
        return n_c;
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
    print affiche la dataframe entièrement si nb est nul. Si nb est
    postif elle affiche les nb premières lignes, si il est négatif elle affiche les dernières nb 
    lignes dans leur ordre respective.
    **/
    public void print(int nb){
        int begin , end ;


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
     * @param colonnesASelectionner = nom des colonnes à selectionner
     * @return un nouveau Dataframe contenant uniquement les colonnes selectionées,
     *  null en cas d'erreur
     */
    public Dataframe select(ArrayList<String> colonnesASelectionner){
        //array servant à la création du nouveau dataframe
        int size = colonnesASelectionner.size() * n_l;
        Object[] tmp = new Object[size];

        //pour chaque colonne selectionnée on remplit le tableau d'elements
        for (int i = 0; i < colonnesASelectionner.size(); i ++){
            
            int numColonne = 0;
            while (numColonne < this.n_c && !this.colonne_name.get(numColonne).equals(colonnesASelectionner.get(i))){
                numColonne++;
            }
            if (numColonne == this.n_c){
                System.err.println("Dataframe.Select() : Une des colonnes n'a pas étée trouvée");
                return null;
            }
            for (int ligne = 0; ligne < n_l; ligne++){
                tmp[ligne * colonnesASelectionner.size() + i] = this.getObject(ligne, numColonne);
            }
        }
        ArrayList<Object> elements = new ArrayList<>();
        Collections.addAll(elements,tmp);
        return new Dataframe(colonnesASelectionner, elements);
    }

    @Override
    public boolean equals(Object o){
        if (this.getN_c() != ((Dataframe)o).getN_c() || 
        this.getN_l() != ((Dataframe)o).getN_l() || 
        !this.colonne_name.equals(((Dataframe)o).colonne_name)/* ||
        !this.colonne_type.equals(((Dataframe)o).colonne_type)*/){
            return false;
        }
        for (int i = 0; i < this.getN_l(); i++){
            for (int j = 0; j < this.getN_c(); j++){
                if (Integer.parseInt((String)this.getObject(i, j)) != Integer.parseInt((String)((Dataframe)o).getObject(i, j))){
                    return false;
                }
            }
        }
        return true;
    }
}
