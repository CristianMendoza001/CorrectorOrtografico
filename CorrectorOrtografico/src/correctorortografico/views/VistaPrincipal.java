
package correctorortografico.views;

import correctorortografico.elements.FileUserDictionary;
import correctorortografico.elements.SpellChecker;
import correctorortografico.resources.ScaleImage;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;


/**
 *          AUTORES
 *  - MENDOZA RIVAS CRISTIAN
 *  - SÁNCHEZ MÉLENDEZ MAGALI
 *  - SÁNCHEZ HERNÁNDEZ LUIS DANIEL
 *    8° "A" TI
 */

public class VistaPrincipal extends javax.swing.JFrame {
    private Color colorTexto;

    public VistaPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Corrector Ortográfico");
        this.setIconImage(new ImageIcon(this.getClass().getResource("../resources/images/favicon.png")).getImage());
        this.colorTexto = Color.BLACK;
        this.iniciarFrame();
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());
        SpellChecker.registerDictionaries(null, null);
        SpellChecker.register(this.contenido);    
    }//end constructor


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        icono = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tipoLetra = new javax.swing.JComboBox<>();
        iconoTipoLetra = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tamanioLetra = new javax.swing.JComboBox<>();
        iconoTamanioLetra = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        iconoColorLetra = new javax.swing.JLabel();
        colorLetra = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        alineacionTexto = new javax.swing.JComboBox<>();
        iconoAlineacion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contenido = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(161, 215, 193));

        titulo.setFont(new java.awt.Font("Trebuchet MS", 1, 40)); // NOI18N
        titulo.setForeground(new java.awt.Color(28, 102, 159));
        titulo.setText("CORRECTOR ORTOGRÁFICO");

        icono.setBackground(new java.awt.Color(255, 0, 0));
        icono.setMaximumSize(new java.awt.Dimension(200, 200));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Fecha:");

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Hora:");

        fecha.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        fecha.setForeground(new java.awt.Color(0, 0, 0));
        fecha.setText("00/00/0000");

        hora.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        hora.setForeground(new java.awt.Color(0, 0, 0));
        hora.setText("14:00 hrs");

        jPanel3.setBackground(new java.awt.Color(206, 185, 230));

        tipoLetra.setBackground(new java.awt.Color(206, 185, 230));
        tipoLetra.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        tipoLetra.setForeground(new java.awt.Color(0, 0, 0));
        tipoLetra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo De Letra", "Arial", "Calibri", "Etc" }));
        tipoLetra.setToolTipText("Tipo De Letra");
        tipoLetra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(206, 185, 230), 1, true));
        tipoLetra.setFocusable(false);
        tipoLetra.setOpaque(false);
        tipoLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoLetraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconoTipoLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipoLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoTipoLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(230, 185, 185));

        tamanioLetra.setBackground(new java.awt.Color(230, 185, 185));
        tamanioLetra.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        tamanioLetra.setForeground(new java.awt.Color(0, 0, 0));
        tamanioLetra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tamaño De Letra", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "36", "38", "40" }));
        tamanioLetra.setToolTipText("Tamaño De Letra");
        tamanioLetra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(206, 185, 230), 1, true));
        tamanioLetra.setFocusable(false);
        tamanioLetra.setOpaque(false);
        tamanioLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tamanioLetraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(iconoTamanioLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tamanioLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoTamanioLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tamanioLetra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(188, 236, 243));

        colorLetra.setBackground(new java.awt.Color(188, 236, 243));
        colorLetra.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        colorLetra.setForeground(new java.awt.Color(0, 0, 0));
        colorLetra.setText("Color De Letra");
        colorLetra.setToolTipText("Color De Letra");
        colorLetra.setAlignmentX(0.5F);
        colorLetra.setBorder(null);
        colorLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorLetraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconoColorLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(colorLetra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(iconoColorLetra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(230, 226, 185));

        alineacionTexto.setBackground(new java.awt.Color(230, 226, 185));
        alineacionTexto.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        alineacionTexto.setForeground(new java.awt.Color(0, 0, 0));
        alineacionTexto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alineación De Texto", "Izquierda", "Centrado", "Derecha", "Justificado" }));
        alineacionTexto.setToolTipText("Alineación De Texto");
        alineacionTexto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(206, 185, 230), 1, true));
        alineacionTexto.setFocusable(false);
        alineacionTexto.setOpaque(false);
        alineacionTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alineacionTextoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(iconoAlineacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alineacionTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconoAlineacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alineacionTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        contenido.setBackground(new java.awt.Color(239, 255, 250));
        contenido.setBorder(null);
        contenido.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(contenido);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 6, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titulo)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hora)
                        .addGap(9, 9, 9))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(titulo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(icono, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(fecha))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hora)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 22, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tipoLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoLetraActionPerformed
        this.cambiarContenido((String)this.tipoLetra.getSelectedItem(), (String)this.tamanioLetra.getSelectedItem(), 
                (String)this.alineacionTexto.getSelectedItem());
    }//GEN-LAST:event_tipoLetraActionPerformed

    private void tamanioLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tamanioLetraActionPerformed
        this.cambiarContenido((String)this.tipoLetra.getSelectedItem(), (String)this.tamanioLetra.getSelectedItem(), 
                (String)this.alineacionTexto.getSelectedItem());
    }//GEN-LAST:event_tamanioLetraActionPerformed

    private void colorLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorLetraActionPerformed
        this.colorTexto = javax.swing.JColorChooser.showDialog(null, "Selecciona un color de texto...", this.colorTexto);
        this.colorTexto = this.colorTexto == null ? Color.BLACK : this.colorTexto;
        this.cambiarContenido((String)this.tipoLetra.getSelectedItem(), (String)this.tamanioLetra.getSelectedItem(), 
                (String)this.alineacionTexto.getSelectedItem());
    }//GEN-LAST:event_colorLetraActionPerformed

    private void alineacionTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alineacionTextoActionPerformed
        this.cambiarContenido((String)this.tipoLetra.getSelectedItem(), (String)this.tamanioLetra.getSelectedItem(), 
                (String)this.alineacionTexto.getSelectedItem());
    }//GEN-LAST:event_alineacionTextoActionPerformed

    public void iniciarFrame(){
        JLabel [] labels = {
            this.icono,
            this.iconoTipoLetra,
            this.iconoTamanioLetra,
            this.iconoColorLetra,
            this.iconoAlineacion
        };
        String [] sources = {
            "icono-principal.png",
            "tipo-letra.png",
            "tamanio-letra.png",
            "color-letra.png",
            "alineacion-texto.png"
        };
        this.setIcons(labels, sources);
        this.fecha.setText(java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        java.text.DecimalFormat formatoHora = new java.text.DecimalFormat("00");
        this.hora.setText(formatoHora.format(java.time.LocalTime.now().getHour()) + ":" + formatoHora.format(java.time.LocalTime.now().getMinute()) + " hrs");
        this.cambiarBgComboBox();
        this.setFuentes();
    }//end iniciarFrame
    
    public void setIcons(JLabel [] labels, String [] sources){
        for(int i=0; i < labels.length; i++){
            labels[i].setIcon(new ScaleImage(labels[i], sources[i]).getImageScaled());
        }//end for
    }//end setIcons
    
    public void setFuentes(){
        String[] fuentes = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        String[] fuentesComboBox = new String[fuentes.length + 1];
        fuentesComboBox[0] = "Tipo De Letra";
        for(int i = 0; i < fuentes.length; i++) {
            fuentesComboBox[i+1] = fuentes[i];
        }//end for
        this.tipoLetra.setModel(new javax.swing.DefaultComboBoxModel<>(fuentesComboBox));
    }//end setFuentes
    
    public void cambiarContenido(String fuente, String size, String alineacion){
        if(fuente.equals("Tipo de Letra"))
            fuente = "Arial";
        if(size.equals("Tamaño De Letra"))
            size = "18";
        javax.swing.text.SimpleAttributeSet attribs = new javax.swing.text.SimpleAttributeSet();
        javax.swing.text.StyleConstants.setAlignment(attribs, this.obtenerTipoAlineacion(alineacion));
        javax.swing.text.StyleConstants.setFontFamily(attribs, fuente);
        javax.swing.text.StyleConstants.setFontSize(attribs, Integer.parseInt(size));
        javax.swing.text.StyleConstants.setForeground(attribs, this.colorTexto);
        javax.swing.text.StyleConstants.setBackground(attribs, new java.awt.Color(239, 255, 250));
        this.contenido.setParagraphAttributes(attribs, true);
    }//end cambiarFuente
    
    public int obtenerTipoAlineacion(String alineacion){
        int tipoAlineacion = 0;
        if(alineacion.equals("Alineación De Texto"))
            tipoAlineacion = 0;
        if(alineacion.equals("Izquierda"))
            tipoAlineacion = 0;
        if(alineacion.equals("Centrado"))
            tipoAlineacion = 1;
        if(alineacion.equals("Derecha"))
            tipoAlineacion = 2;
        if(alineacion.equals("Justificado"))
            tipoAlineacion = 3;
        return tipoAlineacion;
    }//end cambiarAlineacion
    
    public void cambiarBgComboBox(){
        this.tipoLetra.setBackground(new java.awt.Color(206,185,230));
            this.tipoLetra.setRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList list, Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus)
                {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    return c;
                }
           }
        );
        this.tamanioLetra.setBackground(new java.awt.Color(230,185,185));
            this.tamanioLetra.setRenderer(new DefaultListCellRenderer(){
                public Component getListCellRendererComponent(JList list, Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus)
                {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    return c;
                }
           }
        );
        this.alineacionTexto.setBackground(new java.awt.Color(230,226,185));
            this.alineacionTexto.setRenderer(new DefaultListCellRenderer(){
                public Component getListCellRendererComponent(JList list, Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus)
                {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    return c;
                }
           }
        );
    }//end cambiarBgComboBox
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> alineacionTexto;
    private javax.swing.JButton colorLetra;
    private javax.swing.JTextPane contenido;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel hora;
    private javax.swing.JLabel icono;
    private javax.swing.JLabel iconoAlineacion;
    private javax.swing.JLabel iconoColorLetra;
    private javax.swing.JLabel iconoTamanioLetra;
    private javax.swing.JLabel iconoTipoLetra;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> tamanioLetra;
    private javax.swing.JComboBox<String> tipoLetra;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables

}
