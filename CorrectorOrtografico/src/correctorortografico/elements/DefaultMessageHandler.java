
package correctorortografico.elements;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class DefaultMessageHandler implements MessageHandler {

	private JFrame ownerFrame;
	
	public DefaultMessageHandler( JFrame ownerFrame ) {
		this.ownerFrame = ownerFrame;
	}

	public void handleError( String title, String errorText, Throwable throwable) {
		throwable.printStackTrace();
		
		JOptionPane.showMessageDialog( ownerFrame, throwable.toString(), title, JOptionPane.ERROR_MESSAGE );
	}


	public void handleException( Throwable throwable) {
		throwable.printStackTrace();
	}


	public void handleInformation(Container parent, String title, String info) {
        JOptionPane.showMessageDialog( parent, info, title, JOptionPane.INFORMATION_MESSAGE );
	}
}
