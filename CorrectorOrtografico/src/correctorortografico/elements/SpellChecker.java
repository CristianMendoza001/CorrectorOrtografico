
package correctorortografico.elements;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.WeakHashMap;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class SpellChecker {
    
    private final static ArrayList<LanguageAction> languages = new ArrayList<LanguageAction>();
    private static Dictionary currentDictionary;
    private static Locale currentLocale;
    private static UserDictionaryProvider userDictionaryProvider;
    private static CustomDictionaryProvider customDictionaryProvider;
    private final static java.util.Map<LanguageChangeListener, Object> listeners = Collections.synchronizedMap( new WeakHashMap<LanguageChangeListener, Object>() );
    private static String applicationName;
    private static final SpellCheckerOptions globalOptions = new SpellCheckerOptions();
    private static MessageHandler messageHandler = new DefaultMessageHandler( null );
    private static CustomUIProvider customUIProvider;
    
   
    static final String SELECTED_KEY = "SwingSelectedKey";
    
    
    private SpellChecker(){/*nothing*/}
    
   
    public static void setUserDictionaryProvider( UserDictionaryProvider userDictionaryProvider ) {
        SpellChecker.userDictionaryProvider = userDictionaryProvider;
    }

   
    public static UserDictionaryProvider getUserDictionaryProvider() {
        return SpellChecker.userDictionaryProvider;
    }
    
  
    public static void setCustomDictionaryProvider( CustomDictionaryProvider customDictionaryProvider ) {
        SpellChecker.customDictionaryProvider = customDictionaryProvider;
    }
    
  
    public static CustomDictionaryProvider getCustomDictionaryProvider() {
        return SpellChecker.customDictionaryProvider;
    }
    
    
    public static void setMessageHandler( MessageHandler messageHandler ) {
        if( messageHandler == null ) {
            throw new IllegalArgumentException();
        }
        SpellChecker.messageHandler = messageHandler;
    }


    public static MessageHandler getMessageHandler() {
        return SpellChecker.messageHandler;
    }

    
    public static void setCustomUIProvider( CustomUIProvider customUIProvider ) {
        SpellChecker.customUIProvider = customUIProvider;
    }

    
    public static CustomUIProvider getCustomUIProvider() {
        return SpellChecker.customUIProvider;
    }

    
    public static void registerDictionaries( URL baseURL, String activeLocale ) {
        if( baseURL == null ){
            try {
                baseURL = SpellChecker.class.getResource( "/dictionaries.cnf" );
                if( baseURL != null ) {
                    baseURL = new URL( baseURL, "." );
                } else {
                    baseURL = new URL( "file", null, "" );
                }
            } catch( MalformedURLException e ) {
            	SpellChecker.getMessageHandler().handleException( e );
            }
        }
        InputStream input;
        try {
            input = new URL( baseURL, "dictionaries.cnf" ).openStream();
        } catch( Exception e1 ) {
            try {
                input = new URL( baseURL, "dictionaries.properties" ).openStream();
            } catch( Exception e2 ) {
                try {
                    input = new URL( baseURL, "dictionaries.txt" ).openStream();
                } catch( Exception e3 ) {
                    System.err.println( "JOrtho configuration file not found!" );
                	SpellChecker.getMessageHandler().handleException( e1 );
                	SpellChecker.getMessageHandler().handleException( e2 );
                	SpellChecker.getMessageHandler().handleException( e3 );
                    return;
                }
            }
        }
        Properties props = new Properties();
        try {
            props.load( input );
        } catch( IOException e ) {
        	SpellChecker.getMessageHandler().handleException( e );
            return;
        }
        String availableLocales = props.getProperty( "languages" );
        String extension = props.getProperty( "extension", ".ortho" );
        registerDictionaries( baseURL, availableLocales, activeLocale, extension );
    }


    public static void registerDictionaries( URL baseURL, String availableLocales, String activeLocale ) {
        registerDictionaries( baseURL, availableLocales, activeLocale, ".ortho" );
    }

    
    public static void registerDictionaries( URL baseURL, String availableLocales, String activeLocale, String extension ) {
        if( baseURL == null ){
            try {
                baseURL = new URL("file", null, "");
            } catch( MalformedURLException e ) {
            	SpellChecker.getMessageHandler().handleException( e );
            }
        }
        if( activeLocale == null ) {
            activeLocale = "";
        }
        activeLocale = activeLocale.trim();
        if( activeLocale.length() == 0 ) {
            activeLocale = Locale.getDefault().getLanguage();
        }
        
        boolean activeSelected = false;
        for( String locale : availableLocales.split( "," ) ) {
            locale = locale.trim().toLowerCase();
            if(locale.length() > 0){
                LanguageAction action = new LanguageAction( baseURL, new Locale( locale ), extension );
                languages.remove( action );
                languages.add( action );
                if( locale.equals( activeLocale ) ) {
                    action.actionPerformed( null );
                    activeSelected = true;
                }
            }
        }
        if( !activeSelected && languages.size() > 0 ) {
            LanguageAction action = languages.get( 0 );
            action.actionPerformed( null );
        }
        
        Collections.sort( languages );
    }
    

    public static void register( final JTextComponent text) throws NullPointerException{
        register( text, true, true, true, true );
    }

    
    public static void register( final JTextComponent text, boolean hasPopup, boolean submenu, boolean hasShortKey, boolean hasAutoSpell ) throws NullPointerException {
        if( hasPopup ) {
            enablePopup( text, true, submenu );
        }
        if( hasShortKey ) {
            enableShortKey( text, true );
        }
        if( hasAutoSpell ) {
            enableAutoSpell( text, true );
        }
    }
    

    public static void unregister( JTextComponent text ){
        enableShortKey( text, false );
        enablePopup( text, false, false );
        enableAutoSpell( text, false );
    }
    

    public static void enableShortKey( final JTextComponent text, boolean enable ){
        enableShortKey( text, enable, null );
    }
    

    public static void enableShortKey( final JTextComponent text, boolean enable, final SpellCheckerOptions options ){
        if( enable ){
            text.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_F7, 0 ), "spell-checking" );
            text.getActionMap().put( "spell-checking", new AbstractAction(){
                public void actionPerformed( ActionEvent e ) {
                    showSpellCheckerDialog( text, options );
                }
            });
        }else{
            text.getActionMap().remove( "spell-checking" ); 
        }
    }
    

    public static void showSpellCheckerDialog( final JTextComponent text, SpellCheckerOptions options ) {
        if( !text.isEditable() ) {
            // only editable text component have spell checking
            return;
        }
        Dictionary dictionary = currentDictionary;
        if( dictionary != null ) {
            Window parent = SwingUtilities.getWindowAncestor( text );
            SpellCheckerDialog dialog;
            if( parent instanceof Frame ) {
                dialog = new SpellCheckerDialog( (Frame)parent, true, options );
            } else {
                dialog = new SpellCheckerDialog( (Dialog)parent, true, options );
            }
            dialog.show( text, dictionary, currentLocale );
        }
    }
    

    public static void enablePopup( JTextComponent text, boolean enable, boolean submenu ){
        if( enable ){
            final JPopupMenu menu;
            if( submenu ){
                menu = new JPopupMenu();
                menu.add( createCheckerMenu() );
                menu.add( createLanguagesMenu() );
            } else {
                menu = createCheckerPopup();
            }
            text.addMouseListener( new PopupListener(menu) );
        } else {
            for(MouseListener listener : text.getMouseListeners()){
                if(listener instanceof PopupListener){
                    text.removeMouseListener( listener );
                }
            }
        }
    }
    

    public static void enableAutoSpell( JTextComponent text, boolean enable ){
        enableAutoSpell( text, enable, null );
    }


    public static void enableAutoSpell( JTextComponent text, boolean enable, SpellCheckerOptions options ){
        if( enable ){
            new AutoSpellChecker( text, options );
        } else {
            AutoSpellChecker.disable( text );
        }
    }
    

    public static void addLanguageChangeLister(LanguageChangeListener listener){
        listeners.put( listener, null );
    }
    
 
    public static void removeLanguageChangeLister(LanguageChangeListener listener){
        listeners.remove( listener );
    }
    

    private static void fireLanguageChanged( Locale oldLocale ) {
        LanguageChangeEvent ev = new LanguageChangeEvent( currentLocale, oldLocale );
        
        Object[] list;
        synchronized( listeners ) {
            list = listeners.keySet().toArray();
        }
        for( Object listener : list ) {
            ((LanguageChangeListener)listener).languageChanged( ev );
        }
    }
    
 
    public static JMenu createCheckerMenu(){
        return createCheckerMenu( null );
    }
    
 
    public static JMenu createCheckerMenu(SpellCheckerOptions options){
        return new CheckerMenu(options);
    }
    

    public static JPopupMenu createCheckerPopup(){
        return createCheckerPopup( null );
    }
    

    public static JPopupMenu createCheckerPopup(SpellCheckerOptions options){
        return new CheckerPopup(options);
    }
    

    public static JMenu createLanguagesMenu(){
        return createLanguagesMenu( null );
    }
    

    public static JMenu createLanguagesMenu(SpellCheckerOptions options){
        JMenu menu = new JMenu(Utils.getResource("languages"));
        ButtonGroup group = new ButtonGroup();
        menu.setEnabled( languages.size() > 0 );
        
        for(LanguageAction action : languages){
            JRadioButtonMenuItem item = new JRadioButtonMenuItem( action );
            item.setModel( new ActionToggleButtonModel(action) );
            menu.add( item );
            group.add( item );
        }
        
        if(options == null ){
            options = SpellChecker.getOptions();
        }
        
        if(languages.size() > 0 && options.isLanguageDisableVisible()){
        	menu.addSeparator();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem( DisableLanguageAction.instance );
            item.setModel( new ActionToggleButtonModel(DisableLanguageAction.instance) );
            menu.add( item );
            group.add( item );
        }
        
        return menu;
    }
    
    private static class ActionToggleButtonModel extends JToggleButton.ToggleButtonModel{
        private final AbtsractLanguageAction action;
        
        ActionToggleButtonModel(AbtsractLanguageAction action){
            this.action = action;
        }
        

        @Override
        public boolean isSelected() {
            return Boolean.TRUE.equals(action.getValue(SELECTED_KEY));
        }
        

        @Override
        public void setSelected( boolean b ) {
            ButtonGroup group = getGroup();
            if (group != null) {
                group.setSelected(this, b);
                b = group.isSelected(this);
            }

            if (isSelected() == b) {
                return;
            }

            action.setSelected( b );

            fireStateChanged();

            fireItemStateChanged(
                    new ItemEvent(this,
                                  ItemEvent.ITEM_STATE_CHANGED,
                                  this,
                                  this.isSelected() ?  ItemEvent.SELECTED : ItemEvent.DESELECTED));

        }
    }
    

    private static abstract class AbtsractLanguageAction extends AbstractAction{
        
        private static AbtsractLanguageAction currentAction;
        
        public AbtsractLanguageAction( String name ) {
            super(name);
        }


        public void setSelected( boolean b ) {
            if( b ) {
                if( currentAction != null && currentAction != this ) {
                    currentAction.setSelected( false );
                }
                currentAction = this;
            }
            putValue( SELECTED_KEY, Boolean.valueOf( b ) );
        }

    }


    private static class DisableLanguageAction extends AbtsractLanguageAction{
    	static DisableLanguageAction instance = new DisableLanguageAction();

        private DisableLanguageAction() {
            super(Utils.getResource("disable"));
        }
        
        public void actionPerformed( ActionEvent ev ) {
            if( !isEnabled() ) {
                return;
            }
            setEnabled( false );
            setSelected( true );
            try {
                currentDictionary = null;
                Locale oldLocale = currentLocale;
                currentLocale = null;
                fireLanguageChanged( oldLocale );
            } finally {
                setEnabled( true );
            }
        }
    }
    

    private static class LanguageAction extends AbtsractLanguageAction implements Comparable<LanguageAction>{
        
        private final URL baseURL;
        private final Locale locale;
        private String extension;
        
        LanguageAction(URL baseURL, Locale locale, String extension){
            super( locale.getDisplayLanguage() );
            this.baseURL = baseURL;
            this.locale = locale;
            this.extension = extension;
        }

        public void actionPerformed( ActionEvent ev ) {
            if( !isEnabled() ){
                return;
            }
            setEnabled( false );
            setSelected( true );
            
            Thread thread = new Thread( new Runnable() {
                public void run() {
                    try {
                        DictionaryFactory factory = new DictionaryFactory();
                        try {
                            factory.loadWordList( new URL( baseURL, "dictionary_" + locale + extension ) );
                        } catch( Exception ex ) {
                        	SpellChecker.getMessageHandler().handleError( ex.toString(), "Error", ex );
                        }
                         try {
                            CustomDictionaryProvider provider = userDictionaryProvider;
                            if( provider != null ) {
                                Iterator<String> userWords = provider.getWords( locale );
                                if( userWords != null ) {
                                    factory.loadWords( userWords );
                                }
                            }
                            provider = customDictionaryProvider;
                            if( provider != null ) {
                                Iterator<String> userWords = provider.getWords( locale );
                                if( userWords != null ) {
                                    factory.loadWords( userWords );
                                }
                            }
                        } catch( Exception ex ) {
                        	SpellChecker.getMessageHandler().handleError( ex.toString(), "Error", ex );
                        }
                        Locale oldLocale = locale;
                        currentDictionary = factory.create();
                        factory = null; 
                        currentLocale = locale;
                        fireLanguageChanged( oldLocale );
                    } finally {
                        setEnabled( true );
                    }
                }
            });
            thread.setPriority( Thread.NORM_PRIORITY );
            thread.setDaemon( true );
            thread.start();
        }
        
        @Override
        public boolean equals(Object obj){
            if(obj instanceof LanguageAction){
                return locale.equals( ((LanguageAction)obj).locale );
            }
            return false;
        }
        
        @Override
        public int hashCode(){
            return locale.hashCode();
        }


        public int compareTo( LanguageAction obj ) {
            return toString().compareTo( obj.toString() );
        }
    }


    static Dictionary getCurrentDictionary() {
        return currentDictionary;
    }


    public static Locale getCurrentLocale() {
        return currentLocale;
    }
    

    public static void setCurrentLocale( Locale locale ) throws IllegalArgumentException {
        if( locale.equals( currentLocale ) ) {
            return;
        }
        for( LanguageAction language : languages ){
            if( language.locale.equals( locale ) ){
                language.actionPerformed( null );
                return;
            }
        }
        throw new IllegalArgumentException( "Not registered locale: " + locale );
    }
    
  
    public static boolean isDictionaryLoaded(){
        return currentDictionary != null && currentDictionary.getDataSize() > 1;
    }
    

    public static void setApplicationName( String name ){
        applicationName = name;
    }


    public static String getApplicationName(){
        return applicationName;
    }
 

    public static SpellCheckerOptions getOptions(){
        return globalOptions;
    }
}
