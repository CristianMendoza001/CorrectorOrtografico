
package correctorortografico.elements;

import java.util.EventListener;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public interface LanguageChangeListener extends EventListener{
    
    public void languageChanged(LanguageChangeEvent ev);

}
