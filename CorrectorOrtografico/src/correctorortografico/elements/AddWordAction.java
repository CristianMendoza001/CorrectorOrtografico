package correctorortografico.elements;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class AddWordAction extends AbstractAction {
    private String         word;
    private JTextComponent jText;

    public AddWordAction( JTextComponent jText, String word ) {
        this( jText, word, Utils.getResource( "addToDictionary" ) );
    }

    public AddWordAction( JTextComponent jText, String word, String label ) {
        super( label );
        this.word = word;
        this.jText = jText;
    }

    public void actionPerformed( ActionEvent arg0 ) {
        UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
        if( provider != null ) {
            provider.addWord( word );
        }
        Dictionary dictionary = SpellChecker.getCurrentDictionary();
        dictionary.add( word );
        dictionary.trimToSize();
        AutoSpellChecker.refresh( jText );
    }

}
