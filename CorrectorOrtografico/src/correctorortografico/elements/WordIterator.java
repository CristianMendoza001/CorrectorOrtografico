
package correctorortografico.elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.zip.InflaterInputStream;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class WordIterator implements Iterator<String> {

    private BufferedReader input;
    private String word;
    

    public WordIterator( URL filename ) throws IOException {
        this( createInflaterStream(filename), "UTF8" );
    }
    

    public WordIterator( InputStream stream, String charsetName ) throws IOException {
       this( new InputStreamReader( stream, charsetName ) );
    }


    public WordIterator( Reader reader ) throws IOException {
        input = new BufferedReader( reader );
        word = input.readLine();
    }
    

    private static InputStream createInflaterStream( URL filename ) throws IOException{
        URLConnection conn = filename.openConnection();
        conn.setReadTimeout( 5000 );
        InputStream input = conn.getInputStream();
        input = new InflaterInputStream( input );
        return new BufferedInputStream( input );
    }
    

    public boolean hasNext() {
        return word != null;
    }


    public String next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        String next = word;
        try {
            word = input.readLine();
            if(word == null){
                input.close();
            }
        } catch( IOException e ) {
            word = null;
        	SpellChecker.getMessageHandler().handleException( e );
        }
        return next;
    }

 
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
