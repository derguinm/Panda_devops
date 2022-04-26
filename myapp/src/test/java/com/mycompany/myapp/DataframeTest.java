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
        //TOTO : test equals method:
        //TODO : fixe lable bug in the function selectFromLabel
        
        suite.addTest(new DataframeTest("getObjectTest"));
        suite.addTest(new DataframeTest("gettersTest"));
        suite.addTest(new DataframeTest("ConstructorDFbyHandTest"));
        suite.addTest(new DataframeTest("ConstructorDFfromFileTest"));
        suite.addTest(new DataframeTest("printTest"));
        //suite.addTest(new DataframeTest("selectFromLabelTest"));
        suite.addTest(new DataframeTest("selectLineTest"));
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
        for (int j = 0; j < n_c; j++) {
            for (int i = 0; i < n_l; i++) {
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
                if(!(dataframe[j][j2].equals(df.getObject(j,j2))) || diff){
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
                dataframe[i][j] = i ;
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
                if(!(dataframe[j][j2].equals(df.getObject(j,j2))) || diff){
                    diff = true ;
                    break ;
                } 
            }
            
        }
        assertFalse(diff);
    }
    public void getObjectTest(){
        Object[][] dataframe = new Object[3][2] ;

        int n_l = 3; 
        int n_c = 2;

        for(int i=0; i<n_l; i++){
            for(int j=0; j<n_c; j++){
                dataframe[i][j] = i ;
            }
        }

        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        for (int i = 0; i < myDataframe.getN_l(); i++){
            for(int j = 0; j < myDataframe.getN_c(); j++){
                assertEquals(dataframe[i][j],myDataframe.getObject(i,j));
            }
        }
    }

    public void gettersTest (){
        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        assertEquals(myDataframe.getN_c(), 2);
        assertEquals(myDataframe.getN_l(), 3);
    }

    public void printTest(){
        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream out = System.out;
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
        
        System.setOut(out);
    }

    public void selectFromLabelTest(){
        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        //test a normal select of 1 column
        ArrayList<String> nomsColonnes = new ArrayList<>();
        nomsColonnes.add("B");
        Dataframe test = myDataframe.selectFromLabel(nomsColonnes);
        assertEquals(new Dataframe("src/test/normalDataframeWithOnlyColumnB.csv"),test );
        //test a select with bad argument
        nomsColonnes.add("v");
        assertNull(myDataframe.selectFromLabel(nomsColonnes));
        //test a select with more than one column
        nomsColonnes.remove(0);
        nomsColonnes.remove(0);
        nomsColonnes.add("A");
        nomsColonnes.add("B");
        new Dataframe("src/test/DataFrameLine-0-2.csv").print(0);
        myDataframe.selectFromLabel(nomsColonnes).print(0);
        assertEquals(new Dataframe("src/test/normalDataframeWithBBeforeA.csv"), myDataframe.selectFromLabel(nomsColonnes));
    }

    public void selectLineTest(){
        Dataframe myDataframe = new Dataframe("src/test/normalDataframe.csv");
        //test a normal select of 1 column
        ArrayList<Integer> lineIndx = new ArrayList<>();
        lineIndx.add(0);
        Dataframe test = myDataframe.selectLine(lineIndx);
        assertEquals(new Dataframe("src/test/DataFrameWithFirstLine.csv"),test );
        //test a select with bad argument
        lineIndx.add(4);
        assertNull(myDataframe.selectLine(lineIndx));
        //test a select with more than one column
        lineIndx.remove(1);
        lineIndx.add(2);

        assertEquals(new Dataframe("src/test/DataFrameLine-0-2.csv"), myDataframe.selectLine(lineIndx));
    }

    
}