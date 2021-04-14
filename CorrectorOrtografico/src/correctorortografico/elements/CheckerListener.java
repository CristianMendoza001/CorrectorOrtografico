
package correctorortografico.elements;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class CheckerListener implements PopupMenuListener, LanguageChangeListener {

    private final JComponent          menu;

    private Dictionary                dictionary;

    private Locale                    locale;

    private final SpellCheckerOptions options;


    public CheckerListener( JComponent menu, SpellCheckerOptions options ) {
        this.menu = menu;
        this.options = options == null ? SpellChecker.getOptions() : options;
        SpellChecker.addLanguageChangeLister( this );
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
    }


    public void popupMenuCanceled( PopupMenuEvent e ) {
        
    }

    public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) {
        
    }


    public void popupMenuWillBecomeVisible( PopupMenuEvent ev ) {
        if( dictionary == null) {
            menu.setEnabled( false );
            return;
        }

        JPopupMenu popup = (JPopupMenu)ev.getSource();

        Component invoker = popup.getInvoker();
        if( invoker instanceof JTextComponent ) {
            final JTextComponent jText = (JTextComponent)invoker;
            if( !jText.isEditable() ) {
                menu.setEnabled( false );
                return;
            }
            try {
                int offs = getCursorPosition( jText );
                if( offs < 0 ) {
                    menu.setEnabled( false );
                    return;
                }
                
                final int begOffs = Utilities.getWordStart( jText, offs );
                final int endOffs = Utilities.getWordEnd( jText, offs );
                final String word = jText.getText( begOffs, endOffs - begOffs );

                Tokenizer tokenizer = new Tokenizer( jText, dictionary, locale, offs, options );
                String invalidWord;
                do {
                    invalidWord = tokenizer.nextInvalidWord();
                } while( tokenizer.getWordOffset() < begOffs );
                menu.removeAll();

                if( !word.equals( invalidWord ) ) {
                    menu.setEnabled( false );
                    return;
                }

                List<Suggestion> list = dictionary.searchSuggestions( word );


                menu.setEnabled( list.size() > 0 );

                boolean needCapitalization = tokenizer.isFirstWordInSentence() && Utils.isFirstCapitalized( word );

                addSuggestionMenuItem( jText, begOffs, endOffs, list, needCapitalization );
                addMenuItemAddToDictionary( jText, word, list.size() > 0 );
            } catch( BadLocationException ex ) {
            	SpellChecker.getMessageHandler().handleException( ex );
            }
        }
    }

    protected int getCursorPosition( JTextComponent jText ) throws BadLocationException {
        Caret caret = jText.getCaret();
        int offs;
        Point p = jText.getMousePosition();
        if( p != null ) {
            offs = jText.viewToModel( p );
            int startPos = Utilities.getRowStart( jText, offs );
            int endPos = Utilities.getRowEnd( jText, offs );
            Rectangle bounds = jText.modelToView( startPos ).union( jText.modelToView( endPos ) );
            if( !bounds.contains( p ) ){
                return -1; 
            }
        } else {
            offs = Math.min( caret.getDot(), caret.getMark() );
        }
        Document doc = jText.getDocument();
        if( offs > 0 && (offs >= doc.getLength() || Character.isWhitespace( doc.getText( offs, 1 ).charAt( 0 ) )) ) {
            offs--;
        }
        return offs;
    }
    

    protected void addSuggestionMenuItem( final JTextComponent jText, final int begOffs, final int endOffs, List<Suggestion> list, boolean needCapitalization ) {
        for( int i = 0; i < list.size() && i < options.getSuggestionsLimitMenu(); i++ ) {
            Suggestion sugestion = list.get( i );
            String sugestionWord = sugestion.getWord();
            if( needCapitalization ) {
                sugestionWord = Utils.getCapitalized( sugestionWord );
            }
            JMenuItem item = new JMenuItem( sugestionWord );
            menu.add( item );
            final String newWord = sugestionWord;
            item.addActionListener( new ActionListener() {

                public void actionPerformed( ActionEvent e ) {
                    jText.setSelectionStart( begOffs );
                    jText.setSelectionEnd( endOffs );
                    jText.replaceSelection( newWord );
                }

            } );
        }
    }
    

    protected void addMenuItemAddToDictionary( JTextComponent jText, String word, boolean addSeparator ) {
        UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
        if( provider == null ) {
            return;
        }
        JMenuItem addToDic = new JMenuItem( new AddWordAction( jText, word ) );
        if( addSeparator ) {
            if( menu instanceof JMenu ) {
                ((JMenu)menu).addSeparator();
            } else if( menu instanceof JPopupMenu ) {
                ((JPopupMenu)menu).addSeparator();
            }
        }
        menu.add( addToDic );
        menu.setEnabled( true );
    }


    public void languageChanged( LanguageChangeEvent ev ) {
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
    }
}
