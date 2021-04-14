
package correctorortografico.elements;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class SpellCheckerOptions {

    private int     suggestionsLimitMenu   = 15;

    private int     suggestionsLimitDialog = 15;

    private boolean caseSensitive          = true;

    private boolean ignoreCapitalization   = false;

    private boolean ignoreAllCaps          = true;

    private boolean ignoreNumbers          = false;

    private boolean languageDisableVisible = false;

    
    public SpellCheckerOptions() {
    }

    
    public void setSuggestionsLimitMenu( int count ) {
        this.suggestionsLimitMenu = count;
    }

    
    public int getSuggestionsLimitMenu() {
        return suggestionsLimitMenu;
    }

    
    public void setSuggestionsLimitDialog( int count ) {
        this.suggestionsLimitDialog = count;
    }

    
    public int getSuggestionsLimitDialog() {
        return suggestionsLimitDialog;
    }

    
    public void setCaseSensitive( boolean caseSensitive ) {
        this.caseSensitive = caseSensitive;
    }

    
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    
    public void setIgnoreCapitalization( boolean ignore ) {
        ignoreCapitalization = ignore;
    }

    
    public boolean getIgnoreCapitalization() {
        return ignoreCapitalization;
    }

    
    public void setIgnoreAllCapsWords( boolean ignore ) {
        ignoreAllCaps = ignore;
    }

    
    public boolean isIgnoreAllCapsWords() {
        return ignoreAllCaps;
    }

    
    public void setIgnoreWordsWithNumbers( boolean ignore ) {
        ignoreNumbers = ignore;
    }

    
    public boolean isIgnoreWordsWithNumbers() {
        return ignoreNumbers;
    }
    
    
    public void setLanguageDisableVisible( boolean visible ) {
        languageDisableVisible = visible;
    }

    
    public boolean isLanguageDisableVisible() {
        return languageDisableVisible;
    }
}
