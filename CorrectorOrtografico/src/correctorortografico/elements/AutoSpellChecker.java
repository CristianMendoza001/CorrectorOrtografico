
package correctorortografico.elements;

import java.util.Locale;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.Highlight;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */


class AutoSpellChecker implements DocumentListener, LanguageChangeListener {
    private static final RedZigZagPainter painter = new RedZigZagPainter();

    private final JTextComponent                jText;
    private final SpellCheckerOptions options;

    private Dictionary                    dictionary;

    private Locale                        locale;

    
    public AutoSpellChecker(JTextComponent text, SpellCheckerOptions options){
        this.jText = text;
        this.options = options == null ? SpellChecker.getOptions() : options;
        jText.getDocument().addDocumentListener( this );

        SpellChecker.addLanguageChangeLister( this );
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
        checkAll();
    }

    static void disable( JTextComponent text ){
        AbstractDocument doc = (AbstractDocument)text.getDocument();
        for(DocumentListener listener : doc.getDocumentListeners()){
            if(listener instanceof AutoSpellChecker){
                AutoSpellChecker autoSpell = (AutoSpellChecker)listener;
                doc.removeDocumentListener( autoSpell );
                removeHighlights(text);
            }
        }
    }

	private static void removeHighlights( JTextComponent text ) {
        Highlighter highlighter = text.getHighlighter();
        for( Highlight highlight : highlighter.getHighlights() ) {
            if( highlight.getPainter() == painter ) {
                highlighter.removeHighlight( highlight );
            }
        }
    }

    static void refresh( JTextComponent text ){
        AbstractDocument doc = (AbstractDocument)text.getDocument();
        for(DocumentListener listener : doc.getDocumentListeners()){
            if( listener instanceof AutoSpellChecker ){
                AutoSpellChecker autoSpell = (AutoSpellChecker)listener;
                autoSpell.checkAll();
            }
        }
    }

    /*====================================================================
     * 
     * Methods of interface DocumentListener
     * 
     *===================================================================*/

    public void changedUpdate( DocumentEvent ev ) {
        
    }


    public void insertUpdate( DocumentEvent ev ) {
        checkElements( ev.getOffset(), ev.getLength() );
    }

    public void removeUpdate( DocumentEvent ev ) {
        checkElements( ev.getOffset(), 0 );
    }

    private void checkElements( int offset, int length ) {
        int end = offset + length;
        Document document = jText.getDocument();
        Element element;

        do{
            try {
                element = ((AbstractDocument)document).getParagraphElement( offset );
            } catch( java.lang.Exception ex ) {
                return;
            }
            checkElement( element );
            int endOffset = element.getEndOffset();
            offset = endOffset > offset ? endOffset : offset + 1;
        }while( offset <= end && offset < document.getLength() );
    }

    private void checkElement( javax.swing.text.Element element ) {
        try {
            int i = element.getStartOffset();
            int j = element.getEndOffset();
            Highlighter highlighter = jText.getHighlighter();
            Highlight[] highlights = highlighter.getHighlights();
            for( int k = highlights.length; --k >= 0; ) {
                Highlight highlight = highlights[k];
                int hlStartOffset = highlight.getStartOffset();
                int hlEndOffset = highlight.getEndOffset();
                if( (i <= hlStartOffset && hlStartOffset <= j) || 
                    (i <= hlEndOffset && hlEndOffset <= j) ) {
                    if( highlight.getPainter() == painter ) {
                        highlighter.removeHighlight( highlight );
                    }
                }
            }

            int l = ((AbstractDocument)jText.getDocument()).getLength();
            j = Math.min( j, l );
            if( i >= j )
                return;

            Dictionary dic = dictionary;
            Locale loc = locale;
            if( dic == null || loc == null ){
                return;
            }
            
            Tokenizer tok = new Tokenizer( jText, dic, loc, i, j, options );
            String word;
            while( (word = tok.nextInvalidWord()) != null ) {
                int wordOffset = tok.getWordOffset();
                highlighter.addHighlight( wordOffset, wordOffset + word.length(), painter );
            }
        } catch( BadLocationException e ) {
        	SpellChecker.getMessageHandler().handleException( e );
        }
    }

    private void checkAll() {
        if( jText == null ) {
            //the needed objects does not exists
            return;
        }
        if( dictionary == null ) {
            removeHighlights( jText );
            return;
        }
        if( jText.getDocument().getLength() == 0 ){
            return;
        }

        Thread thread = new Thread( new Runnable() {
            public void run() {
                Document document = jText.getDocument();
                for( int i = 0; i < document.getLength(); ) {
                    try {
                        final Element element = ((AbstractDocument)document).getParagraphElement( i );
                        i = element.getEndOffset();
                        SwingUtilities.invokeLater( new Runnable() {
                            public void run() {
                                checkElement( element );
                            }

                        } );
                    } catch( java.lang.Exception ex ) {
                        return;
                    }
                }
            }
        }, "JOrtho checkall" );
        thread.setPriority( Thread.NORM_PRIORITY - 1 );
        thread.setDaemon( true );
        thread.start();
    }

    public void languageChanged( LanguageChangeEvent ev ) {
        dictionary = SpellChecker.getCurrentDictionary();
        locale = SpellChecker.getCurrentLocale();
        checkAll();
    }

}
