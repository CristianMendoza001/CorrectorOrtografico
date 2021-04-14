
package correctorortografico.elements;

import java.util.Locale;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class LanguageChangeEvent{

    private final Locale currentLocale;
    private final Locale oldLocale;
    

    public LanguageChangeEvent( Locale currentLocale, Locale oldLocale ) {
        this.currentLocale = currentLocale;
        this.oldLocale = oldLocale;
    }

    public Locale getOldLocale() {
        return oldLocale;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

}
