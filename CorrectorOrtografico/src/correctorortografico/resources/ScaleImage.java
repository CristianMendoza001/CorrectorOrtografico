
package correctorortografico.resources;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class ScaleImage {
    private Object objeto;
    private String source;
    private ImageIcon icon;
    private Image img;
    private Image imgScale;
    private ImageIcon scaledIcon;
    
    public ScaleImage(Object objeto, String source){
        this.objeto = objeto;
        this.source = source;
    }//end constructor
    
    
    //SETTERS
    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setImgScale(Image imgScale) {
        this.imgScale = imgScale;
    }

    public void setScaledIcon(ImageIcon scaledIcon) {
        this.scaledIcon = scaledIcon;
    }
    
    
    //GETTERS
    public Object getObjeto() {
        return objeto;
    }

    public String getSource() {
        return source;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public Image getImg() {
        return img;
    }

    public Image getImgScale() {
        return imgScale;
    }

    public ImageIcon getScaledIcon() {
        return scaledIcon;
    }
    
    
    //-----------------------------------
    //----------MÉTODOS NUEVOS-----------
    //-----------------------------------
    public ImageIcon getImageScaled(){
        if(this.getObjeto() instanceof JLabel){
            JLabel label = (JLabel)this.getObjeto();
            this.icon = new ImageIcon(this.getClass().getResource("images/" + this.source));
            this.img = this.icon.getImage();
            this.imgScale = this.img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            this.scaledIcon = new ImageIcon(this.imgScale);
        }//end if es JLabel
        if(this.getObjeto() instanceof JButton){
            JButton button = (JButton)this.getObjeto();
            this.icon = new ImageIcon(this.getClass().getResource("images/" + this.source));
            this.img = this.icon.getImage();
            this.imgScale = this.img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
            this.scaledIcon = new ImageIcon(this.imgScale);
        }//end if es JLabel
        return this.getScaledIcon();
    }//end getImageScaled
    
}//end class ScaleImage
