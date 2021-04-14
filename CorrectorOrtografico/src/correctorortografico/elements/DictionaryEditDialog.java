
package correctorortografico.elements;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.*;


/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class DictionaryEditDialog extends JDialog{
    
    private final JList list;
    private final JButton delete;
    private boolean isModify;

    DictionaryEditDialog( JDialog parent ){
        super( parent, Utils.getResource("userDictionary"), true );
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        Container content = getContentPane();
        content.setLayout( new GridBagLayout() );
        DefaultListModel data = new DefaultListModel();
        loadWordList( data );
        list = new JList( data );
        content.add( new JScrollPane(list), new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.NORTH ,GridBagConstraints.BOTH, new Insets( 8,8,8,8 ), 0, 0) );
        
        delete = Utils.getButton( "delete" );
        content.add( delete, new GridBagConstraints( 1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH ,GridBagConstraints.BOTH, new Insets( 0,8,8,8 ), 0, 0) );
        DeleteAction deleteAction = new DeleteAction();
        delete.addActionListener( deleteAction );
        getRootPane().getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0, false ), "DELETE" );
        getRootPane().getActionMap().put( "DELETE", deleteAction );
        
        getRootPane().getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0, false ), "ESCAPE" );
        getRootPane().getActionMap().put( "ESCAPE", new AbstractAction() {
            public void actionPerformed( ActionEvent e ) {
                dispose();
            }
        } );

        pack();
        setLocationRelativeTo( parent );
    }
    

    @Override
    public Dimension getPreferredSize() {
        Dimension dim = super.getPreferredSize();
        String title = getTitle();
        int titleWidth = getFontMetrics(getFont()).stringWidth(title) + 80;
        if( dim.width < titleWidth ){
            dim.width = titleWidth;
        }
        return dim;
    }
    

    private void loadWordList( DefaultListModel data ){
        UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
        if( provider != null ) {
            Iterator<String> userWords = provider.getWords( SpellChecker.getCurrentLocale() );
            if( userWords != null ) {
                ArrayList<String> wordList = new ArrayList<String>();
                while(userWords.hasNext()){
                    String word = userWords.next();
                    if( word != null && word.length() > 1 ) {
                        wordList.add( word );
                    }
                }
                Collections.sort( wordList, Collator.getInstance() );
                for(String str : wordList){
                    data.addElement( str );
                }
            }
        }
    }

    private class DeleteAction extends AbstractAction{
        
        public void actionPerformed(ActionEvent e){
            int[] selected = list.getSelectedIndices();
            Arrays.sort( selected );
            for( int i=selected.length-1; i>=0; i-- ){
                ((DefaultListModel)list.getModel()).remove( selected[i] );
                isModify = true;
            }
        }
    }
    

    @Override
    public void dispose(){
        super.dispose();
        if( isModify ) {
            UserDictionaryProvider provider = SpellChecker.getUserDictionaryProvider();
            if( provider != null ) {
                ListModel model = list.getModel();
                StringBuilder builder = new StringBuilder();
                for( int i=0; i<model.getSize(); i++){
                    if( builder.length() != 0 ){
                        builder.append( '\n' );
                    }
                    builder.append( model.getElementAt(i) );
                }
                provider.setUserWords( builder.toString() );
            }
            JMenu menu = SpellChecker.createLanguagesMenu( null );
            Component[] comps = menu.getMenuComponents();
            for( Component comp : comps ) {
                if( comp instanceof JRadioButtonMenuItem ){
                    JRadioButtonMenuItem item = (JRadioButtonMenuItem)comp;
                    if( item.isSelected() ){
                        item.doClick();
                    }
                }
            }
        }
    }
}
