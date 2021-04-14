
package correctorortografico.elements;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class Suggestions {

    private final int maxDiff;
    private final HashMap<Suggestion,Suggestion> map = new HashMap<Suggestion,Suggestion>();

    
    Suggestions(int maxDiff){
        this.maxDiff = maxDiff;
    }
    

    void add(Suggestion suggestion){
        if(suggestion.getDissimilarity() > maxDiff){
            return;
        }
        Suggestion oldSuggestion = map.get( suggestion );
        if(oldSuggestion != null && oldSuggestion.getDissimilarity() <= suggestion.getDissimilarity()){
            return;
        }
        map.put( suggestion, suggestion );
    }
    
    
    List<Suggestion> getlist(){
        ArrayList<Suggestion> list = new ArrayList<Suggestion>();
        for(Suggestion sugg : map.values()){
            list.add( sugg );
        }
        return list;
    }

    int getMaxDissimilarity() {
        return maxDiff;
    }
}
