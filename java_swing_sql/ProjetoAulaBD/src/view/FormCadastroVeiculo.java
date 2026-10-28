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

/**
 *
 * @author leo zorzi
 */
public class FormCadastroVeiculo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FormCadastroVeiculo.class.getName());

    /**
     * Creates new form FormCadastroVeiculo
     */
    public FormCadastroVeiculo() {
        initComponents();
        preencherComboPessoas();
    }
    private void limparFormulario(){
    txt_modelo_veiculo.setText("");
    txt_placa.setText("");
    cmb_pessoas.setSelectedIndex(0);
    }

    public void preencherComboPessoas(){
    PessoaDAO pDAO = new PessoaDAO();
    List<Pessoa> listaP = pDAO.getPessoas();
    
    cmb_pessoas.removeAllItems(); //limpa o campo antes de preencher
    for(Pessoa p : listaP){
        cmb_pessoas.addItem(p);
    }
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_modelo_veiculo = new javax.swing.JLabel();
        lbl_placa = new javax.swing.JLabel();
        lbl_pessoa = new javax.swing.JLabel();
        txt_modelo_veiculo = new javax.swing.JTextField();
        txt_placa = new javax.swing.JTextField();
        cmb_pessoas = new javax.swing.JComboBox();
        btn_salvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbl_modelo_veiculo.setText("Modelo do Veiculo");

        lbl_placa.setText("Placa do automovel");

        lbl_pessoa.setText("Pessoa");

        txt_modelo_veiculo.addActionListener(this::txt_modelo_veiculoActionPerformed);

        cmb_pessoas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));

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
                        .addComponent(lbl_pessoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_pessoas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_placa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_modelo_veiculo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_modelo_veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(lbl_modelo_veiculo)
                    .addComponent(txt_modelo_veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_placa)
                    .addComponent(txt_placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_pessoas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_pessoa))
                .addGap(49, 49, 49)
                .addComponent(btn_salvar)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_modelo_veiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_modelo_veiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_modelo_veiculoActionPerformed

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        Pessoa  p = (Pessoa) cmb_pessoas.getSelectedItem();
        Veiculo v = new Veiculo();
        v.setModelo(txt_modelo_veiculo.getText());
        v.setPlaca(txt_placa.getText());
        v.setPessoaid(p);
        
        VeiculoDAO vDAO = new VeiculoDAO();
        vDAO.inserir(v);
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
        java.awt.EventQueue.invokeLater(() -> new FormCadastroVeiculo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_salvar;
    private javax.swing.JComboBox cmb_pessoas;
    private javax.swing.JLabel lbl_modelo_veiculo;
    private javax.swing.JLabel lbl_pessoa;
    private javax.swing.JLabel lbl_placa;
    private javax.swing.JTextField txt_modelo_veiculo;
    private javax.swing.JTextField txt_placa;
    // End of variables declaration//GEN-END:variables
}
