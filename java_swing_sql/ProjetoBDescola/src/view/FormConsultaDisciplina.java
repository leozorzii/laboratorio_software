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
public class FormConsultaDisciplina extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormConsultaDisciplina.class.getName());

    /**
     * Creates new form FormConsultaDisciplina
     */
    public FormConsultaDisciplina() {
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

        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        lbl_nome = new javax.swing.JLabel();
        lbl_carga_horaria = new javax.swing.JLabel();
        lbl_professor = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        txt_carga_horaria = new javax.swing.JTextField();
        cmb_professores = new javax.swing.JComboBox<>();
        btn_atualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID");

        txt_id.addActionListener(this::txt_idActionPerformed);

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(this::btn_buscarActionPerformed);

        lbl_nome.setText("Nome da Disciplina");

        lbl_carga_horaria.setText("Carga Horaria");

        lbl_professor.setText("Professor");

        txt_nome.addActionListener(this::txt_nomeActionPerformed);

        btn_atualizar.setText("Atualizar");
        btn_atualizar.addActionListener(this::btn_atualizarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(24, 24, 24)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_buscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_carga_horaria)
                            .addComponent(lbl_nome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_carga_horaria, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(cmb_professores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 122, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_professor)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_atualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_buscar)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nome)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_carga_horaria)
                    .addComponent(txt_carga_horaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_professor)
                    .addComponent(cmb_professores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nomeActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        int idDisciplina;
        try{
            idDisciplina = Integer.parseInt(txt_id.getText());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "Informe um ID valido");
            return;
        }
        DisciplinaDAO dDAO = new DisciplinaDAO();

        Disciplina d = dDAO.getDisciplina(idDisciplina);
        if(d == null){
            limparFormulario();
            JOptionPane.showMessageDialog(this, "Disciplina nao encontrada");
        }else{
            txt_nome.setText(d.getNome());
            txt_carga_horaria.setText(d.getCargaHoraria() + "");
            cmb_professores.setSelectedItem(d.getProfessor());
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
        Disciplina d = new Disciplina();
        try{
            d.setId(Integer.parseInt(txt_id.getText()));
            d.setCargaHoraria(Integer.parseInt(txt_carga_horaria.getText()));
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this, "ID e carga horaria devem ser numeros");
            return;
        }
        d.setNome(txt_nome.getText());
        d.setProfessor((Professor) cmb_professores.getSelectedItem());

        DisciplinaDAO dDAO = new DisciplinaDAO();
        dDAO.editar(d);
        JOptionPane.showMessageDialog(this, "Disciplina atualizada");
        txt_id.setText("");
        limparFormulario();
    }//GEN-LAST:event_btn_atualizarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FormConsultaDisciplina().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_atualizar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JComboBox<Professor> cmb_professores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_nome;
    private javax.swing.JLabel lbl_professor;
    private javax.swing.JLabel lbl_carga_horaria;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_carga_horaria;
    // End of variables declaration//GEN-END:variables
}
