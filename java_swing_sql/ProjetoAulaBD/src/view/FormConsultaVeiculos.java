/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import Beans.Pessoa;
import Beans.Veiculo;
import DAO.PessoaDAO;
import DAO.VeiculoDAO;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author leo zorzi
 */
public class FormConsultaVeiculos extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormConsultaVeiculos.class.getName());

    /**
     * Creates new form RelatorioVeiculosComFiltro
     */
    public FormConsultaVeiculos() {
        initComponents();
        preencherComboPessoas();
    }

    public void preencherComboPessoas(){
        PessoaDAO pDAO = new PessoaDAO();
        List<Pessoa> listap = pDAO.getPessoas();
        cmb_pessoas.removeAllItems(); //limpa o campo antes de preencher
        for(Pessoa p : listap){
        cmb_pessoas.addItem(p);
        }
    }

       private void limparFormulario(){
       txt_modelo_veiculo.setText("");
       txt_placa.setText("");
       cmb_pessoas.setSelectedIndex(0);

       }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        lbl_modelo_veiculo = new javax.swing.JLabel();
        lbl_placa = new javax.swing.JLabel();
        lbl_pessoa = new javax.swing.JLabel();
        txt_modelo_veiculo = new javax.swing.JTextField();
        txt_placa = new javax.swing.JTextField();
        cmb_pessoas = new javax.swing.JComboBox();
        btn_atualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID");

        txt_id.addActionListener(this::txt_idActionPerformed);

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(this::btn_buscarActionPerformed);

        lbl_modelo_veiculo.setText("Modelo do Veiculo");

        lbl_placa.setText("Placa do automovel");

        lbl_pessoa.setText("Pessoa");

        txt_modelo_veiculo.addActionListener(this::txt_modelo_veiculoActionPerformed);

        cmb_pessoas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

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
                            .addComponent(lbl_placa)
                            .addComponent(lbl_modelo_veiculo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_modelo_veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_placa, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(cmb_pessoas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 122, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_pessoa)
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
                    .addComponent(lbl_modelo_veiculo)
                    .addComponent(txt_modelo_veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_placa)
                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_pessoa)
                    .addComponent(cmb_pessoas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(btn_atualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_modelo_veiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_modelo_veiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_modelo_veiculoActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
       int idVeiculo = Integer.parseInt(txt_id.getText());
       VeiculoDAO vDAO = new VeiculoDAO();
       
       Veiculo v = vDAO.getVeiculo(idVeiculo);
       if(v == null){
           limparFormulario();
           JOptionPane.showMessageDialog(this, "Veiculo nao encontrado");
       }else{
       txt_id.setText(idVeiculo + "");
       txt_modelo_veiculo.setText(v.getModelo());
       txt_placa.setText(v.getPlaca());
       
       Pessoa pessoaSelecionado = (Pessoa) v.getPessoaid();
       cmb_pessoas.setSelectedItem(pessoaSelecionado);
       }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_atualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizarActionPerformed
      Veiculo v = new Veiculo();
      v.setId(Integer.parseInt(txt_id.getText()));
      v.setModelo(txt_modelo_veiculo.getText());
      v.setPlaca(txt_placa.getText());
      v.setPessoaid((Pessoa) cmb_pessoas.getSelectedItem());
      
      VeiculoDAO vDAO = new VeiculoDAO();
      vDAO.editar(v);
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
        java.awt.EventQueue.invokeLater(() -> new FormConsultaVeiculos().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_atualizar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JComboBox cmb_pessoas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_modelo_veiculo;
    private javax.swing.JLabel lbl_pessoa;
    private javax.swing.JLabel lbl_placa;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_modelo_veiculo;
    private javax.swing.JTextField txt_placa;
    // End of variables declaration//GEN-END:variables
}
