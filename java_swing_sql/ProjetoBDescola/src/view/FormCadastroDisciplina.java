/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Beans.Disciplina;
import Beans.Professor;
import DAO.DisciplinaDAO;
import DAO.ProfessorDAO;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author leo zorzi
 */
public class FormCadastroDisciplina extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormCadastroDisciplina.class.getName());

    /**
     * Creates new form FormCadastroDisciplina
     */
    public FormCadastroDisciplina() {
        initComponents();
        preencherComboProfessores();
    }

    public void preencherComboProfessores(){
        ProfessorDAO profDAO = new ProfessorDAO();
        List<Professor> listaProf = profDAO.getProfessores("", "");
        cmb_professores.removeAllItems(); //limpa o campo antes de preencher
        for(Professor prof : listaProf){
            cmb_professores.addItem(prof);
        }
    }

    private void limparFormulario(){
        txt_nome.setText("");
        txt_carga_horaria.setText("");
        if(cmb_professores.getItemCount() > 0){
            cmb_professores.setSelectedIndex(0);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_nome = new javax.swing.JLabel();
        lbl_carga_horaria = new javax.swing.JLabel();
        lbl_professor = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        txt_carga_horaria = new javax.swing.JTextField();
        cmb_professores = new javax.swing.JComboBox<>();
        btn_salvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_nome.setText("Nome da Disciplina");

        lbl_carga_horaria.setText("Carga Horaria");

        lbl_professor.setText("Professor");

        txt_nome.addActionListener(this::txt_nomeActionPerformed);

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(this::btn_salvarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(lbl_professor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_professores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_carga_horaria)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_carga_horaria, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(97, 97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_salvar)
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nome)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_carga_horaria)
                    .addComponent(txt_carga_horaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_professores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_professor))
                .addGap(49, 49, 49)
                .addComponent(btn_salvar)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomeActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        Professor prof = (Professor) cmb_professores.getSelectedItem();
        if(prof == null){
            JOptionPane.showMessageDialog(this, "Cadastre um professor antes");
            return;
        }
        Disciplina d = new Disciplina();
        d.setNome(txt_nome.getText());
        try{
            d.setCargaHoraria(Integer.parseInt(txt_carga_horaria.getText()));
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Carga horaria deve ser um numero");
            return;
        }
        d.setProfessor(prof);

        DisciplinaDAO dDAO = new DisciplinaDAO();
        dDAO.inserir(d);
        JOptionPane.showMessageDialog(this, "Disciplina cadastrada");
        limparFormulario();
    }//GEN-LAST:event_btn_salvarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FormCadastroDisciplina().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox<Professor> cmb_professores;
    private javax.swing.JLabel lbl_nome;
    private javax.swing.JLabel lbl_professor;
    private javax.swing.JLabel lbl_carga_horaria;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_carga_horaria;
    // End of variables declaration//GEN-END:variables
}
