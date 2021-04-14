
package correctorortografico.elements;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public interface CustomUIProvider {

    JButton getButton( String resource );

    JTextField getTextField();

    JLabel getLabel( String text );

    JList getList();
}
