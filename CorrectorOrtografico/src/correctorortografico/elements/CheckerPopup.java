
package correctorortografico.elements;

import javax.swing.*;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class CheckerPopup extends JPopupMenu {
    
    CheckerPopup(SpellCheckerOptions options){
        CheckerListener listener = new CheckerListener(this, options);
        super.addPopupMenuListener(listener);
    }
}
