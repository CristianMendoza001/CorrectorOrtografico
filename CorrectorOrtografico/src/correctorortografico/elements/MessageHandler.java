
package correctorortografico.elements;

import java.awt.Container;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public interface MessageHandler {

    void handleError( String title, String errorText, Throwable throwable );


    void handleException( Throwable throwable );


    void handleInformation( Container parent, String title, String info );

}
