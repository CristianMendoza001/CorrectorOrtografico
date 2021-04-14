
package correctorortografico.dictionaries;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.*;

public abstract class BookGenerator {

    private final Book book;

    
    public static void main(String[] args) throws Exception {
        String languagesList = (args.length>0) ? args[0] : "en";
        String dirName  = (args.length>1) ? args[1].replace( '\\', '/' ) : "";
        if(dirName.length() > 0 && !dirName.endsWith( "/" )){
            dirName += '/';
        }
        String[] languages = languagesList.split(",");
        for(int i = 0; i < languages.length; i++){
            String language = languages[i];
            
            String filename = dirName + language+"wiktionary-latest-pages-articles.xml";
            File file = new File(filename);
            BookGenerator generator = (BookGenerator)Class.forName( BookGenerator.class.getName()+"_" + language ).newInstance();
            generator.start(file);
            generator.save(language);
            
            generator.createPackage( language );
        }
    }
    
    BookGenerator(){
        this(new Book());
    }
    
    BookGenerator(Book book){
        this.book = book;
    }
    

    void start(File file) throws Exception{
        InputStream stream = new FileInputStream(file);
        System.out.println("=== Start Parsing XML stream ===");
        new Parser(this, stream);

        stream.close();
    }
    
    
    final void save(String language) throws Exception{
        File dictFile = new File("dictionary_"+language+".ortho");
        OutputStream dict = new FileOutputStream(dictFile);
        dict = new BufferedOutputStream(dict);
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        dict = new DeflaterOutputStream(dict, deflater);
        dict = new BufferedOutputStream(dict);
        PrintStream dictPs = new PrintStream(dict, false, "UTF8");
        
        OutputStream txt = new FileOutputStream("IncludedWords.txt");
        txt = new BufferedOutputStream(txt);
        PrintStream ps = new PrintStream(txt, false, "UTF8");
        
        String[] words = book.getWords();
        Arrays.sort( words );
        for(int i=0; i<words.length; i++){
            ps.print( words[i] +'\n' );
            dictPs.print( words[i] +'\n' );
        }
        ps.close();
        dictPs.close();
        saveStatistics(dictFile);
    }
    

    private final void saveStatistics(File dictFile) throws Exception{
        String statistics = "";
        statistics += "Total Wiktionary Title count: "+book.getTitleCount()+"\r\n";
        statistics += "Language Title count: "+book.getLanguageTitleCount()+"\r\n";
        statistics += "Word count in dictionary: "+book.getWordCount()+"\r\n";
        statistics += "Char count in dictionary: "+book.getCharCount()+"\r\n";
        statistics += "Dictionary size on disk (bytes): " + dictFile.length()+"\r\n";
        
        Class clazz = Class.forName("com.inet.jortho.DictionaryFactory");
        Constructor constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        Object factory = constructor.newInstance();
        Method loadWordList = clazz.getDeclaredMethod( "loadWordList", URL.class );
        loadWordList.setAccessible(true);
        loadWordList.invoke(factory, dictFile.toURL());
        Method create = clazz.getDeclaredMethod( "create" );
        create.setAccessible(true);
        Object dictionary = create.invoke( factory );
        Method getDataSize = dictionary.getClass().getDeclaredMethod( "getDataSize" );
        getDataSize.setAccessible(true);
        Integer size = (Integer)getDataSize.invoke( dictionary );
        statistics += "Dictionary size in memory (bytes): " + size+"\r\n";
        
        System.out.println(statistics);
        FileOutputStream out = new FileOutputStream("statistics.txt");
        out.write( statistics.getBytes() );
        out.close();
    }
    

    private final void createPackage(String language) throws Exception{
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream("dictionary_"+language+"_" + new SimpleDateFormat("yyyy_MM").format( new Date() )+ ".zip"));
        
        out.setLevel( Deflater.BEST_COMPRESSION );
        addFileToZip( out, "license.txt", false );
        addFileToZip( out, "dictionary_"+language+".ortho", true );
        addFileToZip( out, "statistics.txt", true );
        addFileToZip( out, "IncludedWords.txt", true );
        
        out.close();
    }
    
    private final void addFileToZip(ZipOutputStream out, String filename, boolean delete) throws Exception{
        File file = new File(filename);
        FileInputStream fin = new FileInputStream( file );
        ZipEntry entry = new ZipEntry(filename);
        entry.setTime( file.lastModified() );
        out.putNextEntry( entry );
        byte[] buffer = new byte[16384];
        int count;
        while((count = fin.read(buffer)) > 0){
            out.write(  buffer, 0, count );
        }
        out.closeEntry();
        fin.close();
        if(delete){
            file.delete();
        }
    }
    

    protected final int indexOf(String string, char[] chars, int fromIndex){
        for(; fromIndex < string.length(); fromIndex++){
            char c = string.charAt(fromIndex);
            for(int i=0; i<chars.length; i++){
                if(c == chars[i]) return fromIndex;
            }
        }
        return -1;
    }
    

    protected boolean isValidWord(String word){
        if( word == null ){
            return false;
        }
        final int length = word.length();
        if(length <= 1) return false;
        int last = length - 1;
        for( int i = last; i >= 0; i-- ) {
            char ch = word.charAt( i );
            if( Character.isLetter( ch ) ) {
                continue;
            }
            if( ch == '\'' ) {
                if( i == last && word.charAt( 0 ) == '\'' ) {
                    return false;
                }
                if( i > 0 && word.charAt( i - 1 ) == '\'' ) {
                    return false;
                }
                continue;
            }

            if( ch == '.' && i == last ) {
                continue;
            }

            if( ch == '-' && i != 0 && i != last ) {
                continue;
            }

            return false;
        }
        return true;
    }    
    

    final protected void addWord(String word){
        book.addWord( word );
    }
    

    Book getBook(){
        return book;
    }
    

    abstract boolean isValidLanguage(String word, String wikiText); 
}
