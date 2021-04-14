
package correctorortografico.dictionaries;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class WordList2Dictionary {

    public static void main( String[] args ) throws IOException {
        if( args.length != 2 ) {
            System.out.println("Convert a plain txt file with UTF8 encoding to a dictionary file.");
            System.out.println( "  Usage:" );
            System.out.println( "\tjava com.inet.jorthodictionaries.WordList2Dictionary <txt file> <dic file>" );
            System.out.println( "\t<txt file>\ttext file with words in UTF8 coding, every word is in its own line" );
            System.out.println( "\t<dic file>\toutput file name of the created dictionary" );
            System.exit( 1 );
        }

    
        File dictFile = new File( args[1] );
        OutputStream dict = new FileOutputStream( dictFile );
        dict = new BufferedOutputStream( dict );
        Deflater deflater = new Deflater();
        deflater.setLevel( Deflater.BEST_COMPRESSION );
        dict = new DeflaterOutputStream( dict, deflater );
        dict = new BufferedOutputStream( dict );
        PrintStream dictPs = new PrintStream( dict, false, "UTF8" );

       
        File txtFile = new File( args[0] );
        FileInputStream fis = new FileInputStream( txtFile );
        Reader reader = new InputStreamReader( fis, "UTF8" );
        BufferedReader txt = new BufferedReader( reader );

        
        ArrayList<String> wordList = new ArrayList<String>();
        String word = txt.readLine();
        while( word != null ) {
            wordList.add( word );
            word = txt.readLine();
        }

        
        String[] words = wordList.toArray( new String[wordList.size()] );
        Arrays.sort( words );
        for( int i = 0; i < words.length; i++ ) {
            dictPs.print( words[i] + '\n' );
        }
        dictPs.close();
    }

}
