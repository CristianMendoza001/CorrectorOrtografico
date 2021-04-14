
package correctorortografico.elements;

import java.awt.*;

import javax.swing.text.*;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

class RedZigZagPainter extends DefaultHighlighter.DefaultHighlightPainter {
	
	private static final java.awt.BasicStroke STROKE1 = new java.awt.BasicStroke(0.01F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{1,3}, 0);
    private static final java.awt.BasicStroke STROKE2 = new java.awt.BasicStroke(0.01F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{1,1}, 1);
    private static final java.awt.BasicStroke STROKE3 = new java.awt.BasicStroke(0.01F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{1,3}, 2);


    public RedZigZagPainter(){
        super(Color.red);
    }
	


	@Override
    public Shape paintLayer(Graphics g, int i, int j, Shape shape, JTextComponent jtext, View view){
	    if(jtext.isEditable()){
            g.setColor(Color.red);
            try{
                Shape sh = view.modelToView(i, Position.Bias.Forward, j, Position.Bias.Backward, shape);
                Rectangle rect = (sh instanceof Rectangle) ? (Rectangle)sh : sh.getBounds();
                drawZigZagLine(g, rect);
                return rect;
            }catch(BadLocationException badlocationexception){
                return null;
            }
	    }
	    return null;
    }
	

    private void drawZigZagLine(Graphics g, Rectangle rect){
        int x1 = rect.x;
        int x2 = x1 + rect.width - 1;
        int y = rect.y + rect.height - 1;
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(STROKE1);
        g2.drawLine(x1, y, x2, y);
        y--;
        g2.setStroke(STROKE2);
        g2.drawLine(x1, y, x2, y);
        y--;
        g2.setStroke(STROKE3);
        g2.drawLine(x1, y, x2, y);
    }

}
