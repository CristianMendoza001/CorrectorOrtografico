package correctorortografico.elements;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class AboutDialog extends JDialog {

    AboutDialog( JDialog parent ) {
        super( parent, Utils.getResource( "about" ), true );
        Utils.setDialogIcon( this );
        setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
        Container content = getContentPane();
        content.setLayout( new GridBagLayout() );
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.insets = new Insets( 5, 5, 5, 5 );
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        String licensee = null;
        String license = null;
        URL url = getClass().getResource( "/jortho-license.properties" );
        if( url != null ) {
            Properties props = new Properties();
            try {
                props.load( url.openStream() );
                licensee = props.getProperty( "Licensee" );
                license = props.getProperty( "License" );
            } catch( IOException ex ) {
                ex.printStackTrace();
            }
        }

        if( licensee != null && licensee.length() > 0 && "Commercial License".equalsIgnoreCase( license ) ) {
            // Commercial License
            content.add( Utils.getLabel( "JOrtho - Commercial License" ), gbc );
            content.add( Utils.getLabel( "Licensee: " + licensee ), gbc );
            content.add( Utils.getLabel( "2005 - 2013 \u00a9 i-net software, Berlin, Germany" ), gbc );
        } else {
            // GPL License
            content.add( Utils.getLabel( "JOrtho - GNU General Public License (GPL)" ), gbc );

            content.add( Utils.getLabel( "2005 - 2013 \u00a9 i-net software, Berlin, Germany" ), gbc );
            JLabel link = Utils.getLabel( "" );
            content.add( link, gbc );
            link.addMouseListener( new MouseAdapter() {

                @Override
                public void mouseClicked( MouseEvent e ) {
                    try {
                        Desktop.getDesktop().browse( new URI( "" ) );
                    } catch( Exception ex ) {
                        ex.printStackTrace();
                    }
                }
            } );
        }
        pack();
        setLocationRelativeTo( parent );
    }
}
