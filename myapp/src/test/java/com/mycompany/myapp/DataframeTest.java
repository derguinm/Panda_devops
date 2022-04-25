package com.mycompany.myapp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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

        suite.addTest(new DataframeTest("ConstructorDFbyHandTest"));
        suite.addTest(new DataframeTest("ConstructorDFfromFileTest"));
        suite.addTest(new DataframeTest("printTest"));
        return suite;
    }

    public void ConstructorDFbyHandTest(){
        Object[][] dataframe = new Object[3][2] ;
        ArrayList<String> colonne_name = new ArrayList<String>();
        ArrayList<String> colonne_type = new ArrayList<String>() ;

        int n_l = 3;
        int n_c = 2; 

        for(int i=0; i<n_l; i++){
            for(int j=0; j<n_c; j++){
                dataframe[i][j] = (Object)i ;
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
                elements.add( dataframe[i][j] ) ;
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

    public void ConstructorDFfromFileTest(){
        Object[][] dataframe = new Object[3][2] ;
        ArrayList<String> colonne_name = new ArrayList<String>();
        ArrayList<String> colonne_type = new ArrayList<String>() ;

        int n_l = 3; 
        int n_c = 2;

        for(int i=0; i<n_l; i++){
            for(int j=0; j<n_c; j++){
                dataframe[i][j] = Integer.toString(i) ;
            }
        }
        colonne_name.add("A");
        colonne_name.add("B");

        colonne_type.add("java.lang.Integer");  
        colonne_type.add("java.lang.Integer"); 
        
        Dataframe df = new Dataframe("src/test/normalDataframe.csv") ;
        
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
        assertFalse(diff);
    }

    public void printTest(){
        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //print all a normal dataframe
        myDataframe.print(0);
        String expectedOutput = "A,B\n0,0\n1,1\n2,2\n";
        assertEquals(expectedOutput, outContent.toString());
        
        //print all a normal dataframe from end 
        outContent.reset();
        myDataframe.print(-3);
        expectedOutput = "A,B\n0,0\n1,1\n2,2\n";
        assertEquals(expectedOutput, outContent.toString());

        //print a part of normal dataframe from end
        outContent.reset();
        myDataframe.print(-2);
        expectedOutput = "A,B\n1,1\n2,2\n";
        assertEquals(expectedOutput, outContent.toString());

        //print part of a dataframe from begining
        outContent.reset();
        myDataframe.print(2);
        expectedOutput = "A,B\n0,0\n1,1\n";
        assertEquals(expectedOutput, outContent.toString());

        //print all of an empty dataframe
        myDataframe = new Dataframe("src/test/emptyDataframe.csv");
        outContent.reset();
        myDataframe.print(0);
        expectedOutput = "A,B\n";
        assertEquals(expectedOutput, outContent.toString());

        //print with wrong argument
        outContent.reset();
        myDataframe.print(1);
        expectedOutput = "Invalid argument in function Dataframe.print()\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}