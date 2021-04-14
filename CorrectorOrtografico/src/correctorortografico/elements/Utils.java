
package correctorortografico.elements;

import java.awt.Dialog;
import java.awt.Image;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
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

public class Utils {

    
    static String getResource( String value ) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle( "com.inet.jortho.i18n.resource" );
            return resource.getString( value );
        } catch( Exception e ) {
            if( !value.endsWith( ".tooltip" ) ) { // Tooltip is something extra. Ignore it if not there
                SpellChecker.getMessageHandler().handleException( e );
            }
        }
        return value;
    }

    
    static JButton getButton( String resource ) {
        JButton button;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            button = customProvider.getButton( resource );
        } else {
            button = new JButton( Utils.getResource( resource ) );

            String tooltipKey = resource + ".tooltip";
            String tooltip = Utils.getResource( tooltipKey );
            if( tooltip != tooltipKey ) {
                button.setToolTipText( tooltip );
            }
        }

        return button;
    }

    
    static JTextField getTextField() {
        JTextField textField;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            textField = customProvider.getTextField();
        } else {
            textField = new JTextField();
        }

        return textField;
    }


    static JLabel getLabel( String text ) {
        JLabel label;

        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            label = customProvider.getLabel( text );
        } else {
            label = new JLabel( text );
        }

        return label;
    }


    static JList getList() {
        CustomUIProvider customProvider = SpellChecker.getCustomUIProvider();
        if( customProvider != null ) {
            return customProvider.getList();
        } else {
            return new JList();
        }
    }
    

    static void setDialogIcon(JDialog dlg) {
        try {
            Image image = ImageIO.read( dlg.getClass().getResourceAsStream( "icon.png" ) );
            Class cls = Dialog.class;
            java.lang.reflect.Method m = cls.getMethod( "setIconImage", new Class[] { Image.class } );
            m.invoke( dlg, new Object[] { image } );
        } catch( Throwable e1 ) {
        }
    }
    

    static String getCapitalized( String word ) {
        if( (word.length() > 0) && Character.isLowerCase( word.charAt( 0 ) ) ) {
            return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
        }
        return word;
    }

    static String getInvertedCapitalizion( String word ) {
        if( word.length() > 0 ) {
            if( Character.isLowerCase( word.charAt( 0 ) ) ) {
                return word.substring( 0, 1 ).toUpperCase() + word.substring( 1 );
            }
            if( Character.isUpperCase( word.charAt( 0 ) ) ) {
                return word.substring( 0, 1 ).toLowerCase() + word.substring( 1 );
            }
        }
        return word;
    }


    static boolean isFirstCapitalized( String word ) {
        return (word.length() > 0) && Character.isUpperCase( word.charAt( 0 ) );
    }


    static boolean isAllCapitalized( String word ) {
        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );

            if( Character.isLetter( ch ) && !Character.isUpperCase( ch ) ) {
                return false;
            }
        }
        return true;
    }


    static boolean isIncludeNumbers( String word ) {
        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );
            if( Character.isDigit( ch ) ) {
                return true;
            }
        }
        return false;
    }
    

    public static String replaceUnicodeQuotation( String word ) {
        char[] newWord = null;

        for( int i = 0; i < word.length(); i++ ) {
            char ch = word.charAt( i );

            switch( ch ) {
                case '\u2018': 
                case '\u2019': 
                case '\u201a': 
                case '\u201b': 
                case '´': 
                case '`': 
                    if( newWord == null ){
                        newWord = word.toCharArray();
                    }
                    newWord[i] = '\'';
                    break;
                case '\u2011': 
                case '\u2012': 
                case '\u2013': 
                case '\u2014': 
                case '\u2015':
                    if( newWord == null ){
                        newWord = word.toCharArray();
                    }
                    newWord[i] = '-';
                    break;
            }
        }
        return ( newWord == null ) ? word : new String( newWord );
    }
}
