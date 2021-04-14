
package correctorortografico;

import correctorortografico.views.VistaPrincipal;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class CorrectorOrtografico {

    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new VistaPrincipal().setVisible(true);
        }//End try
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Hubo un error fatal al intentar cargar el programa,\n ¿todo lo tienes instalado bien en Windows? ",
                                                "ERROR FATAL AL ABRIR EL PROGRAMA", JOptionPane.ERROR_MESSAGE);
        }//End catch navegador
    }//end main
    
}//end main class
