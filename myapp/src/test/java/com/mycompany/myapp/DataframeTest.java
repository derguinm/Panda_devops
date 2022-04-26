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
    
     public static Test suite()
    {
        TestSuite suite = new TestSuite();
    
        
        suite.addTest(new DataframeTest("getObjectTest"));
        suite.addTest(new DataframeTest("gettersTest"));
        suite.addTest(new DataframeTest("ConstructorDFbyHandTest"));
        suite.addTest(new DataframeTest("ConstructorDFfromFileTest"));
        suite.addTest(new DataframeTest("equalsTest"));
        suite.addTest(new DataframeTest("printTest"));
        suite.addTest(new DataframeTest("selectFromLabelTest"));
        suite.addTest(new DataframeTest("selectLineTest"));
        suite.addTest(new DataframeTest("getStatTest"));
        
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

    public void equalsTest(){
        //compare 2 equals dataframe
        assertEquals(new Dataframe("src/test/normalDataframe.csv"), new Dataframe("src/test/normalDataframe.csv"));
        //compare 2 empty equals dataframe (column names but no values)
        assertEquals(new Dataframe("src/test/emptyDataframe.csv"), new Dataframe("src/test/emptyDataframe.csv"));
        //compare 2 empty different dataframe (column names but no values)
        assertFalse(new Dataframe("src/test/emptyDataframe.csv").equals(new Dataframe("src/test/otherEmptyDataframe.csv")));
        //compare 2 dataframe with different column names
        assertFalse(new Dataframe("src/test/normalDataframe.csv").equals(new Dataframe("src/test/dataframeWithDifferentColumnName.csv")));
        //compare 2 dataframe with different column type
        assertFalse(new Dataframe("src/test/normalDataframe.csv").equals(new Dataframe("src/test/dataframeWithDifferentColumnType.csv")));
        //compare 2 dataframe with different number of line
        assertFalse(new Dataframe("src/test/normalDataframe.csv").equals(new Dataframe("src/test/normalDataframe.csv").selectLine(new ArrayList<Integer>(){{add(0);}})));
        //compare 2 dataframe with different number of column
        assertFalse(new Dataframe("src/test/normalDataframe.csv").equals(new Dataframe("src/test/normalDataframe.csv").selectFromLabel((new ArrayList<String>(){{add("A");}}))));
        //compare 2 dataframe with different values
        assertFalse(new Dataframe("src/test/normalDataframe.csv").equals(new Dataframe("src/test/dataframeWithDifferentValue.csv")));
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
        assertEquals(new Dataframe("src/test/normalDataframeWithOnlyColumnB.csv"),myDataframe.selectFromLabel(nomsColonnes) );
        //test a select with bad argument
        nomsColonnes.add("v");
        assertNull(myDataframe.selectFromLabel(nomsColonnes));
        //test a select with more than one column
        nomsColonnes.remove(1);
        nomsColonnes.add("A");
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

    public void getStatTest(){
        Dataframe myDataframe = new Dataframe("src/test/statDataframe.csv");
        ArrayList<String> lineIndx =new ArrayList<>();

        lineIndx.add("A");
        lineIndx.add("B");
        
        ArrayList<Float> res=new ArrayList<>();
        res = myDataframe.getStat(lineIndx,0);
        assertTrue(0 == res.get(0));
        assertTrue(0 == res.get(1));
        res = myDataframe.getStat(lineIndx,1);
        assertTrue(10 == res.get(0));
        assertTrue(2 == res.get(1));
        res = myDataframe.getStat(lineIndx,2);
        assertTrue(4.5==res.get(0));
        assertTrue(1.25 == res.get(1));
      
    } 

}