
package correctorortografico.elements;

import java.util.Iterator;
import java.util.Locale;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public interface CustomDictionaryProvider{
    Iterator<String> getWords(Locale locale);
}
