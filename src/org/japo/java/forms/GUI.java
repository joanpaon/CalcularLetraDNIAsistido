/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.forms;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.japo.java.components.BackgroundPanel;
import org.japo.java.events.DEM;
import org.japo.java.libraries.UtilesDNI;
import org.japo.java.libraries.UtilesSwing;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class GUI extends JFrame implements ClipboardOwner {

    // Referencias
    private final Properties prp;

    // Modelos
    private Document doc;
    private DocumentListener dem;
    
    // Fuentes
    private Font fntDNI;

    // Imágenes
    private Image imgBack;
    private Image imgClip;

    // Constructor
    public GUI(Properties prp) {
        // Conectar Referencia
        this.prp = prp;

        // Inicialización Anterior
        initBefore();

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción - GUI
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDNI = new javax.swing.JPanel();
        btnClip = new javax.swing.JButton();
        txfDNI = new javax.swing.JTextField();
        lblDNI = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Calcular NIF Asistido");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        btnClip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClipActionPerformed(evt);
            }
        });
        pnlDNI.add(btnClip);

        txfDNI.setColumns(8);
        txfDNI.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pnlDNI.add(txfDNI);

        lblDNI.setText("•");
        lblDNI.setOpaque(true);
        pnlDNI.add(lblDNI);

        getContentPane().add(pnlDNI, new java.awt.GridBagConstraints());

        setSize(new java.awt.Dimension(600, 400));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClipActionPerformed
        procesarPortapapeles(evt);
    }//GEN-LAST:event_btnClipActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClip;
    private javax.swing.JLabel lblDNI;
    private javax.swing.JPanel pnlDNI;
    private javax.swing.JTextField txfDNI;
    // End of variables declaration//GEN-END:variables
    //
    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty("look_and_feel_profile"));

        // Fuentes
        fntDNI = UtilesSwing.generarFuenteRecurso(prp.getProperty("font_resource"));

        // Imágenes
        imgBack = UtilesSwing.importarImagenRecurso(prp.getProperty("img_back_resource"));
        imgClip = UtilesSwing.importarImagenRecurso(prp.getProperty("img_clip_resource"));
        
        // Panel Principal
        setContentPane(new BackgroundPanel(imgBack));
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty("img_favicon_resource"));

        // Botón - Portapapeles
        btnClip.setIcon(new ImageIcon(UtilesSwing.escalarImagen(imgClip, 64, 64)));

        // Campo de Texto - Número de DNI
        txfDNI.setFont(fntDNI.deriveFont(Font.PLAIN, 80f));
        txfDNI.setColumns(8);

        // Modelo Campo de Texto - Número de DNI
        doc = txfDNI.getDocument();

        // Etiqueta - Control de DNI
        lblDNI.setFont(fntDNI.deriveFont(Font.PLAIN, 80f));

        // Ventana Principal
        setTitle(prp.getProperty("form_title"));
        int width = Integer.parseInt(prp.getProperty("form_width"));
        int height = Integer.parseInt(prp.getProperty("form_height"));
        setSize(width, height);
        setLocationRelativeTo(null);

        // Ajustes de tamaño
        btnClip.setPreferredSize(new Dimension(
                btnClip.getPreferredSize().width + 2,
                txfDNI.getPreferredSize().height + 2));
        lblDNI.setPreferredSize(new Dimension(
                lblDNI.getPreferredSize().width + 2,
                txfDNI.getPreferredSize().height));

        // Gestor de Eventos de Documento - Número de DNI
        dem = new DEM(this);

        // Registra Gestores de Eventos
        doc.addDocumentListener(dem);
    }

    // Notificación Pérdida Propiedad Portapapeles
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        // No hacer nada
    }

    // Campo de texto > Portapapeles
    public final void procesarPortapapeles(ActionEvent e) {
        // Obtiene el Número de DNI Introducido
        String txtDNI = txfDNI.getText();

        // Forma DNI
        if (UtilesDNI.validarNumero(txtDNI)) {
            // Si Numero de DNI OK > Letra ya Calculada
            txtDNI += lblDNI.getText();
        }

        // Texto > Portapapeles
        UtilesSwing.exportarTextoPortapapeles(txtDNI, this);

        // Portapapeles > Consola - Realimentación
        System.out.println("Portapapeles: " + UtilesSwing.importarTextoPortapapeles());
    }

    // Procesamiento - Eliminación de Texto
    public final void procesarCambioTexto(DocumentEvent e) {
        SwingUtilities.invokeLater(() -> {
            procesarDNI();
        });
    }

    // Calcula la letra del DNI actual
    private void procesarDNI() {
        try {
            // Obtiene y Depura el Número de DNI Introducido
            String numDNI = txfDNI.getText().trim().toUpperCase();

            // Actualiza DNI ( *** NO Nuevos Eventos *** )
            doc.removeDocumentListener(dem);
            txfDNI.setText(numDNI);
            doc.addDocumentListener(dem);

            // Procesa Número de DNI
            if (UtilesDNI.validarNumero(numDNI)) {
                // Número de DNI
                int numero = Integer.parseInt(UtilesDNI.normalizarNumero(numDNI));

                // Calcula Letra de DNI
                char letra = UtilesDNI.calcularControl(numero);

                // Publica Letra de DNI
                lblDNI.setText(letra + "");

                // Seleccionar Contenido Campo de Texto
                txfDNI.setSelectionStart(0);
            } else {
                throw new Exception("ERROR: Formato de DNI incorrecto");
            }
        } catch (Exception e) {
            lblDNI.setText("•"); // Alt + NUMPAD7
        }
    }
}
