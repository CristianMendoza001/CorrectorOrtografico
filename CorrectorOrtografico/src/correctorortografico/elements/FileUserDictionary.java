
package correctorortografico.elements;

import java.io.*;
import java.util.Iterator;
import java.util.Locale;



/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class FileUserDictionary implements UserDictionaryProvider{

    private final String fileBase;
    private File file;
    
    
    public FileUserDictionary(){
        this( "" );
    }
    

    public FileUserDictionary( String fileBase ){
        if( fileBase == null ){
            fileBase = "";
        }
        fileBase = fileBase.trim();
        fileBase = fileBase.replace( '\\', '/' );
        if( fileBase.length() > 0 && !fileBase.endsWith("/") ){
            fileBase += "/";
        }
        this.fileBase = fileBase;
    }
    

    public void addWord(String word){
        try{
            FileOutputStream output = new FileOutputStream( file, true );
            Writer writer = new OutputStreamWriter( output, "UTF8" );
            if( file.length() > 0 ){
                writer.write( "\n" );
            }
            writer.write( word );
            writer.close();
        }catch(Exception ex){
        	SpellChecker.getMessageHandler().handleException( ex );
        }
    }


    public Iterator<String> getWords(Locale locale){
        file = new File(fileBase + "UserDictionary_" + locale + ".txt" );
        try{
            FileInputStream input = new FileInputStream(file);
            return new WordIterator( input, "UTF8" );
        }catch(IOException ex){
            
        }
        return null;
    }


    public void setUserWords(String wordList){
        try{
            FileOutputStream output = new FileOutputStream( file );
            Writer writer = new OutputStreamWriter( output, "UTF8" );
            writer.write( wordList );
            writer.close();
        }catch(Exception ex){
        	SpellChecker.getMessageHandler().handleException( ex );
        }
    }
}
