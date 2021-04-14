
package correctorortografico.elements;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class PopupListener extends MouseAdapter {

    private final JPopupMenu menu;

    public PopupListener( JPopupMenu menu ) {
        this.menu = menu;
    }

    @Override
    public void mousePressed( MouseEvent ev ) {
        maybeShowPopup( ev );
    }

    @Override
    public void mouseReleased( MouseEvent ev ) {
        maybeShowPopup( ev );
    }

    private void maybeShowPopup( MouseEvent ev ) {
        if( ev.isPopupTrigger() ) {
            menu.show( ev.getComponent(), ev.getX(), ev.getY() );
        }
    }

}
