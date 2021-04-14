
package correctorortografico.elements;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class DictionaryFactory {

    private final Node root = new Node( (char)0 );
    private char[] tree;
    private int size;
    

    public DictionaryFactory(){
    }
    

    public void loadWordList( URL filename ) throws IOException {
        loadWords( new WordIterator( filename ) );
    }
    
    public void loadWords( Iterator<String> words ) {
        while( words.hasNext() ) {
            String word = words.next();
            if( word != null && word.length() > 1 ) {
                add( word );
            }
        }
    }

    public void add(String word){
        Node node = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            Node entry = node.searchCharOrAdd( c );
            if(i == word.length()-1){
                entry.isWord = true;
                return;
            }
            node = entry;
        }
    }


    public Dictionary create(){
        tree = new char[10000];
        
        root.save( this );
        
        //shrink the array
        char[] temp = new char[size];
        System.arraycopy( tree, 0, temp, 0, size );
        tree = temp;
        
        return new Dictionary(tree);
    }

    final void checkSize(int newSize){
        if(newSize > tree.length){
            char[] puffer = new char[Math.max(newSize, 2*tree.length)];
            System.arraycopy(tree, 0, puffer, 0, size);
            tree = puffer;
        }
    }
    

    private final static class Node extends LowMemoryArrayList<Node>{

        private final char c;
        private boolean isWord;
        
        Node(char c){
            this.c = c;
        }
        
                
        Node searchCharOrAdd( char c ) {
            for(int i=0; i<size(); i++){
                Node entry = get( i );
                if(entry.c < c){
                    continue;
                }
                if(entry.c == c){
                    return entry;
                }
                entry = new Node(c);
                add( i, entry );
                return entry;
            }
            Node entry = new Node(c);
            add( entry );
            return entry;
        }
        
        int save(DictionaryFactory factory){
            int idx;
            int start = idx = factory.size;
            int newSize = factory.size + size() * 3 + 1;
            factory.checkSize( newSize );
            factory.size = newSize;
            
            for(int i=0; i<size(); i++){
                Node entry = get( i );
                factory.tree[idx++] = entry.c;
                int offset = 0;
                if(entry.size() != 0){
                    offset = entry.save(factory);
                }
                if(entry.isWord){
                    offset |= 0x80000000;
                }
                factory.tree[idx++] = (char)(offset >> 16);
                factory.tree[idx++] = (char)(offset);
            }
            factory.tree[idx] = DictionaryBase.LAST_CHAR;
            return start;
        }
    }
    
}
