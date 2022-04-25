package com.mycompany.myapp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Dataframe df = new Dataframe("file.csv");
        
        df.print(0);
    }
}
