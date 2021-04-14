
package correctorortografico.elements;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public interface UserDictionaryProvider extends CustomDictionaryProvider{

   
    void addWord( String word );

    void setUserWords( String wordList );

}
