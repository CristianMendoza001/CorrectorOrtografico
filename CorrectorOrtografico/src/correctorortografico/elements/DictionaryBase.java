
package correctorortografico.elements;

import java.util.*;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

abstract class DictionaryBase {

    protected char[] tree;
    protected int size;
    protected int idx;
    
    
    protected static final char LAST_CHAR = 0xFFFF;
    
    
    DictionaryBase(char[] tree){
        this.tree = tree;
        size = tree.length;
    }
    

    protected DictionaryBase(){
     
    }
    

    public boolean exist(String word){
        idx = 0;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            while(idx<size && tree[idx] < c){
                idx += 3;
            }
            if((idx>=size || tree[idx] != c)){
                return false;
            }
            if( i == word.length() - 1 && isWordMatch() ) {
                return true;
            }
            idx = readIndex();
            if(idx <= 0) return false;
        }
        return false;
    }

    public List<Suggestion> searchSuggestions(String word){
        if(word.length() == 0 || exist(word)){
            return new ArrayList<Suggestion>();
        }
        Suggestions suggesions = new Suggestions( Math.min( 20, 4+word.length() ) );
        idx = 0;
        searchSuggestions( suggesions, word, 0, 0, 0);
        List<Suggestion> list = suggesions.getlist();
        Collections.sort( list );
        return list;
    }

    private void searchSuggestions( Suggestions list, CharSequence chars, int charPosition, int lastIdx, int diff){
        if(diff > list.getMaxDissimilarity()){
            return;
        }
        idx = lastIdx;
        char currentChar = chars.charAt(charPosition);
        if(searchChar(currentChar)){
            if( isWordMatch() ) {
                if(charPosition+1 == chars.length()){
                    list.add( new Suggestion(chars, diff));
                }else{
                    int length = charPosition+1;
                    CharSequence chars2 = chars.subSequence( 0, length );
                    list.add( new Suggestion(chars2, diff + (chars.length()-length)*5));
                }
            }
            idx = readIndex();
            if( idx > 0 ) {
                if(charPosition+1 == chars.length()){
                    searchSuggestionsLonger( list, chars, idx, diff + 5);
                } else {
                    searchSuggestions( list, chars, charPosition + 1, idx, diff );
                }
            }
        }

        
        if(charPosition+1 < chars.length()){
            idx = lastIdx;
            currentChar = chars.charAt(charPosition+1);
            if(searchChar(currentChar)){
                int tempIdx = idx;
                
                idx = readIndex();
                if( idx > 0 ) {
                    StringBuilder buffer = new StringBuilder( chars );
                    buffer.setCharAt( charPosition+1, chars.charAt( charPosition ) );
                    buffer.setCharAt( charPosition, currentChar );
                    searchSuggestions( list, buffer, charPosition+1, idx, diff+3);
                }
                
                idx = tempIdx;
                StringBuilder buffer = new StringBuilder();
                buffer.append( chars, 0, charPosition );
                buffer.append( chars, charPosition+1, chars.length() );
                searchSuggestions( list, buffer, charPosition, lastIdx, diff+5);
            }
        }

        {
            int tempIdx = idx = lastIdx;
            while( idx < size && tree[idx] < LAST_CHAR ) {
                char newChar = tree[idx];
                idx = readIndex();
                if( idx > 0 && newChar != currentChar) {
                    StringBuilder buffer = new StringBuilder( chars );
                    buffer.insert( charPosition, newChar );
                    searchSuggestions( list, buffer, charPosition + 1, idx, diff + 5 );
                }
                idx = tempIdx += 3;
            }
        }
        
        if(charPosition < chars.length()){
            currentChar = chars.charAt(charPosition);
            int tempIdx = idx = lastIdx;
            while( idx < size && tree[idx] < LAST_CHAR ) {
                if( isWordMatch() ){
                    StringBuilder buffer = new StringBuilder();
                    buffer.append( chars, 0, charPosition );
                    buffer.append( tree[idx] );
                    list.add( new Suggestion( buffer, diff + 5 + (chars.length()-buffer.length())*5 ) );
                }
                if(charPosition + 1 < chars.length()){
                    char newChar = tree[idx];
                    idx = readIndex();
                    if( idx > 0 && newChar != currentChar) {
                        StringBuilder buffer = new StringBuilder( chars );
                        buffer.setCharAt( charPosition, newChar );
                        searchSuggestions( list, buffer, charPosition + 1, idx, diff + charDiff( currentChar, newChar ) );
                    }
                }
                idx = tempIdx += 3;
            }
        }
    }
    
    private void searchSuggestionsLonger( Suggestions list, CharSequence chars, int lastIdx, int diff){
        int tempIdx = idx = lastIdx;
        while(idx<size && tree[idx] < LAST_CHAR){
            StringBuilder buffer = new StringBuilder();
            buffer.append( chars );
            buffer.append( tree[idx] );
                searchSuggestions( list, buffer, chars.length(), idx, diff );
            idx = tempIdx += 3;
        }
    }

    private boolean searchChar(char c){
        while(idx<size && tree[idx] < c){
            idx += 3;
        }
        if((idx>=size || tree[idx] != c)){
            return false;
        }
        return true;
    }
    

    private boolean isWordMatch(){
        return (tree[idx + 1] & 0x8000) > 0;
    }
    

    final int readIndex(){
        return ((tree[idx+1] & 0x7fff)<<16) + tree[idx+2]; 
    }
    

    private int charDiff( char a, char b ) {
        a = Character.toLowerCase( a );
        b = Character.toLowerCase( b );

        if( a == b ) {
            return 1;
        }

        if( Character.getType( a ) != Character.getType( b ) ) {
            return 6;
        }

        return 5;
    }
}
