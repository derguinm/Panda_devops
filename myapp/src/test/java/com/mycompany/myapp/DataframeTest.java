package com.mycompany.myapp;

import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataframeTest extends TestCase{
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    
    public DataframeTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    //TODO faut ajouter les tester ecrit ici pour qu'il soit dynamiquement execute.
    // faut ajoutetr des tests sur la fonction print. 
    public static Test suite()
    {
        TestSuite suite = new TestSuite();

        suite.addTest(new DataframeTest("Constructor1Test"));
        suite.addTest(new DataframeTest("Constructor2Test"));
        return suite;
    }

    public void Constructor1Test(){
        Object[][] dataframe = new Object[2][2] ;
        ArrayList<String> colonne_name = new ArrayList<String>();
        ArrayList<String> colonne_type = new ArrayList<String>() ;

        int n_c = 2;
        int n_l = 2; 

        for(int i=0; i<n_l; i++){
            for(int j=0; j<n_c; j++){
                dataframe[j][i] = (Object)i ;
            }
        }

        colonne_name.add("A");
        colonne_name.add("B");

        colonne_type.add("java.lang.Integer");  
        colonne_type.add("java.lang.Integer"); 
        
        boolean diff = false;
        ArrayList<Object> elements = new ArrayList<Object>() ;
        for (int i = 0; i < n_l; i++) {
            for (int j = 0; j < n_c; j++) {
                elements.add( dataframe[j][i] ) ;
            }
        }

        Dataframe df = new Dataframe(colonne_name, elements) ;
        
        for (int j2 = 0; j2 < n_c ; j2++) {
            
            if(!(colonne_name.get(j2).equals(df.getColonne_name().get(j2))) || !(colonne_type.get(j2).equals(df.getColonne_type().get(j2))) ){
                diff = true ;
                break ;
            }
            
            for (int j = 0; j < n_l; j++) {
                if(!(dataframe[j][j2].equals(df.getDataframe()[j][j2])) || diff){
                    diff = true ;
                    break ;
                } 
            }
        }       
        assertTrue( !diff);
    }

    public void Constructor2Test(){
        Object[][] dataframe = new Object[2][2] ;
        ArrayList<String> colonne_name = new ArrayList<String>();
        ArrayList<String> colonne_type = new ArrayList<String>() ;

        int n_c = 2;
        int n_l = 2; 

        for(int i=0; i<n_l; i++){
            for(int j=0; j<n_c; j++){
                dataframe[j][i] = Integer.toString(i) ;
            }
        }
        colonne_name.add("A");
        colonne_name.add("B");

        colonne_type.add("java.lang.Integer");  
        colonne_type.add("java.lang.Integer"); 
        
        Dataframe df = new Dataframe("file.csv") ;
        
        boolean diff = false ;
        for (int j2 = 0; j2 < n_c && !diff; j2++) {
            
            if(!(colonne_name.get(j2).equals(df.getColonne_name().get(j2))) || !(colonne_type.get(j2).equals(df.getColonne_type().get(j2))) ){
                diff = true ;
                break ;
            }
            
            for (int j = 0; j < n_l; j++) {
                if(!(dataframe[j][j2].equals((String)df.getDataframe()[j][j2])) || diff){
                    diff = true ;
                    break ;
                } 
            }
            
        }
        df.print(0);
        assertTrue( !diff);
    }
}