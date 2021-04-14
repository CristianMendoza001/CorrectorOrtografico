
package correctorortografico.elements;


/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

final class Suggestion implements Comparable<Suggestion>{

    private final String word;
    private final int diff;
    
    
    Suggestion( CharSequence word, int diff) {
        this.word = word.toString();
        this.diff = diff;
    }
    
    
    @Override
    public String toString(){
        return word;
    }


    
    public String getWord() {
        return word;
    }


    public int getDissimilarity(){
        return diff;
    }

    @Override
    public boolean equals(Object sugg){
        if (sugg instanceof Suggestion) {
            return word.equals( ((Suggestion)sugg).word );
        }
        return false;
    }
    
    
    @Override
    public int hashCode(){
        return word.hashCode();
    }


    public int compareTo( Suggestion sugg ) {
        return diff - sugg.diff;
    }
}
