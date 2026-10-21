/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Beans.Professor;
import DAO.ProfessorDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leo zorzi
 */
public class RelatorioProfessores extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RelatorioProfessores.class.getName());

    /**
     * Creates new form RelatorioProfessores
     */
    public RelatorioProfessores() {
        initComponents();
        preencheTabela();
    }
     public void preencheTabela(){
    ProfessorDAO pDAO = new ProfessorDAO();

    List<Professor> listaProfessores = pDAO.getProfessores(txt_nome.getText(), ""); 
    DefaultTableModel tabelaProfessores = (DefaultTableModel) tbl_professores.getModel();
    tabelaProfessores.setNumRows(0);
    for(Professor p : listaProfessores){
        Object[] obj = new Object[]{
        p.getId(),
        p.getNome(),
        p.getIdade(),
        p.getDisciplina()
            };
        tabelaProfessores.addRow(obj);
        }
    
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_titulo = new javax.swing.JLabel();
        lbl_nome = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_professores = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_titulo.setText("Relatorio Professores");

        lbl_nome.setText("Nome");

        txt_nome.addCaretListener(this::txt_nomeCaretUpdate);
        txt_nome.addActionListener(this::txt_nomeActionPerformed);

        tbl_professores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Idade", "Disciplina"
            }
        ));
        jScrollPane1.setViewportView(tbl_professores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lbl_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nome))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(92, 92, 92))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nome)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void txt_nomeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_nomeCaretUpdate
        preencheTabela();
    }//GEN-LAST:event_txt_nomeCaretUpdate

    private void txt_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new RelatorioProfessores().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_nome;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JTable tbl_professores;
    private javax.swing.JTextField txt_nome;
    // End of variables declaration//GEN-END:variables
}
