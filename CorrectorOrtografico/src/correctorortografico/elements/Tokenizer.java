
package correctorortografico.elements;

import java.text.BreakIterator;
import java.util.Locale;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
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

class Tokenizer {
    
    private final Document doc;
    private final SpellCheckerOptions options;
    private final LanguageBundle bundle;
    private int paragraphOffset;
    private int endOffset;
    
    private String phrase;
    private final Dictionary dictionary;
    private BreakIterator sentences;
    private int startSentence, endSentence, startWord, endWord;
    private String sentence;
    private BreakIterator words;
    private int wordOffset;
    private boolean isFirstWordInSentence;
    

    Tokenizer( JTextComponent jText, Dictionary dictionary, Locale locale, SpellCheckerOptions options ) {
        this( jText, dictionary, locale, 0, jText.getDocument().getLength(), options );
    }

    Tokenizer( JTextComponent jText, Dictionary dictionary, Locale locale, int offset, SpellCheckerOptions options ) {
        this( jText, dictionary, locale, Utilities.getParagraphElement( jText, offset ).getStartOffset(), 
                                         Utilities.getParagraphElement( jText, offset ).getEndOffset(), options );
    }


    Tokenizer( JTextComponent jText, Dictionary dictionary, Locale locale, int startOffset, int endOffset, SpellCheckerOptions options ) {

        this.dictionary = dictionary;
        doc = jText.getDocument();
        bundle = LanguageBundle.get( locale );
        this.options = options == null ? SpellChecker.getOptions() : options;
        sentences = BreakIterator.getSentenceInstance( locale );
        words = BreakIterator.getWordInstance( locale );

        paragraphOffset = startOffset;
        this.endOffset = endOffset;
        setSentencesText();
        endSentence = sentences.first();
        endWord = BreakIterator.DONE;
    }


    String nextInvalidWord() {
        isFirstWordInSentence = false;
        while( true ) {
            if( endWord == BreakIterator.DONE ) {
                startSentence = endSentence;
                endSentence = sentences.next();
                if( endSentence == BreakIterator.DONE ) {
                    if(!nextParagraph()){
                        return null;
                    }
                }else{
                    nextSentence();
                }
            }
            while( endWord != BreakIterator.DONE ) {
                String word = sentence.substring( startWord, endWord ).trim();
                wordOffset = startSentence + startWord;
                startWord = endWord;
                endWord = words.next();
                if( word.length() > 1 && Character.isLetter( word.charAt( 0 ) )){
                    boolean exist = bundle.existInDictionary( word, dictionary, options, isFirstWordInSentence );
                    
                    if( !exist && options.isIgnoreAllCapsWords() && Utils.isAllCapitalized( word ) ){
                        exist = true;
                    }
                    
                    if( !exist && options.isIgnoreWordsWithNumbers() && Utils.isIncludeNumbers( word ) ){
                        exist = true;
                    }
                    
                    if( !exist && startWord + 1 == endWord ) {
                        char nextChar = sentence.charAt( startWord );
                        switch( nextChar ) {
                            case '.':
                            case '\'':
                                exist = bundle.existInDictionary( word + nextChar, dictionary, options, isFirstWordInSentence );
                        }
                    }
                    
                    if( !exist && !isWebAddress( word )) {
                        return word;
                    }
                    isFirstWordInSentence = false;
                } else {
                    if( ":".equals( word ) || "(".equals( word ) ){
                        isFirstWordInSentence = true;
                    }
                }
            }
        }
    }
    

    private boolean isWebAddress( String word ){
        if( startWord >= sentence.length() ){
            return false;
        }
        if( sentence.charAt( startWord ) == '@' ){
            word += '@';
            startWord = endWord;
            endWord = words.next();
            String domaine = sentence.substring( startWord, endWord ).trim();
            if( domaine.length()>3 && domaine.indexOf( '.' ) > 0 ){
                startWord = endWord;
                endWord = words.next();
                return true;
            }
            return false;
        }
        if( startWord + 3 < sentence.length() && sentence.charAt( startWord ) == ':' && sentence.charAt( startWord + 1 ) == '/' && sentence.charAt( startWord + 2 ) == '/' ) {
            while(startWord < endWord){
                String next = sentence.substring( startWord, endWord ).trim();
                if( next.length() > 0 ){
                    word += next;
                    startWord = endWord;
                    endWord = words.next();
                } else {
                    break;
                }
            }
            return true;
        }
        return false;
    }
    

    boolean isFirstWordInSentence(){
        return isFirstWordInSentence;
    }
    

    private boolean nextParagraph(){
        if(doc instanceof AbstractDocument){
            paragraphOffset = ((AbstractDocument)doc).getParagraphElement( paragraphOffset ).getEndOffset();
            if(paragraphOffset >= endOffset){
                return false;
            }
        }else{
            return false;
        }
        loadSentences();
        return true;
    }
    

    private void loadSentences(){
        setSentencesText();

        startSentence = sentences.first();
        endSentence = sentences.next();
        nextSentence();
    }
    

    private void setSentencesText(){
        int end = endOffset;
        if(doc instanceof AbstractDocument){
            end = ((AbstractDocument)doc).getParagraphElement( paragraphOffset ).getEndOffset();
        }
        try {
            phrase = doc.getText( paragraphOffset, end-paragraphOffset );
            phrase = Utils.replaceUnicodeQuotation( phrase );
        } catch( BadLocationException e ) {
        	SpellChecker.getMessageHandler().handleException( e );
        }
        sentences.setText( phrase );
    }


    private void nextSentence() {
        sentence = phrase.substring( startSentence, endSentence );
        words.setText( sentence );
        startWord = words.first();
        endWord = words.next();
        isFirstWordInSentence = true;
    }


    int getWordOffset() {
        return paragraphOffset + wordOffset;
    }


    void updatePhrase() {
        endOffset = doc.getLength();
        setSentencesText();
        
        endSentence = sentences.following( startSentence );
        sentence = phrase.substring( startSentence, endSentence );
        
        words.setText( sentence );
        startWord = words.following( wordOffset - startSentence );
        endWord = words.next();
    }
}
