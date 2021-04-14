
package correctorortografico.elements;

import java.awt.event.*;

import javax.swing.*;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */


class CheckerMenu extends JMenu implements HierarchyListener {
    
    private final CheckerListener listener;

    CheckerMenu(SpellCheckerOptions options){
        super( Utils.getResource("spelling"));
        listener = new CheckerListener(this, options);
        super.addHierarchyListener(this);
    }


    public void hierarchyChanged(HierarchyEvent ev) {
        if(ev.getChangeFlags() == HierarchyEvent.PARENT_CHANGED && ev.getChanged() == this){
            JPopupMenu parent = (JPopupMenu)getParent();
            if(parent != null){
                parent.addPopupMenuListener(listener);
            }else{
                ((JPopupMenu)ev.getChangedParent()).removePopupMenuListener(listener);
            }
        }
    }
}
