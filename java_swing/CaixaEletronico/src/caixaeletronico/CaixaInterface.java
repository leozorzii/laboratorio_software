/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caixaeletronico;

/**
 *
 * @author laboratorio
 */
public class CaixaInterface extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CaixaInterface.class.getName());
    
    private double saldo = 1000;
    private boolean is_saldo_visivel = true;
    
    public CaixaInterface() {
        initComponents();
        lblStatus.setText("Bem vindo ao caixa do Bradresco!");
        atualizarSaldo();
        lblSaldo.setPreferredSize(new java.awt.Dimension(260, 30));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSaldo = new javax.swing.JLabel();
        textSaldo = new javax.swing.JTextField();
        Depositar = new javax.swing.JButton();
        Sacar = new javax.swing.JButton();
        consultarSaldo = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

       // aumenta a largura para 200 ou 250
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblSaldo.setText("Saldo");

        textSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSaldoActionPerformed(evt);
            }
        });

        Depositar.setText("Depositar");
        Depositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DepositarActionPerformed(evt);
            }
        });

        Sacar.setText("Sacar");
        Sacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SacarActionPerformed(evt);
            }
        });

        consultarSaldo.setText("Consultar Saldo");
        consultarSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultarSaldoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(Sacar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(Depositar)
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(consultarSaldo)
                        .addGap(134, 134, 134))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(textSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Depositar)
                    .addComponent(Sacar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(consultarSaldo)
                .addGap(52, 52, 52))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSaldoActionPerformed
        System.out.println(saldo);
    }//GEN-LAST:event_textSaldoActionPerformed

    private void DepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepositarActionPerformed
        try {
            double valor = lerEValidarValor();

            saldo += valor;
            lblStatus.setText("Deposito de R$ " + valor + "efetuado com sucesso");

            textSaldo.setText("");
            atualizarSaldo();

        } catch (IllegalArgumentException e) {
            lblSaldo.setText("erro:" + e.getMessage());
        }
            }
    //GEN-LAST:event_DepositarActionPerformed

    private void consultarSaldoActionPerformed(java.awt.event.ActionEvent evt) {
        is_saldo_visivel = !is_saldo_visivel;
        if(is_saldo_visivel){
            consultarSaldo.setText("Ocultar Saldo");
        }else{
            consultarSaldo.setText("Apresentar Saldo");
        }
        atualizarSaldo();
    }//GEN-LAST:event_consultarSaldoActionPerformed

    private void SacarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            double valor = lerEValidarValor();
            if(valor > saldo){
                lblStatus.setText("saldo insuficente, nao foi possivel realizar a transação");
            }
            saldo -= valor;
            lblStatus.setText("Deposito de R$" + valor + "efetuado com sucesso");
            textSaldo.setText("");
            atualizarSaldo();
        } catch (IllegalArgumentException e) {
            lblStatus.setText("erro:" + e.getMessage());
        }
    }
    public double lerEValidarValor(){
        String texto = textSaldo.getText().trim().replace(",",".");
        if(texto.isEmpty()){
            System.out.println("nao pode texto vazio");
        }
        double valor = Double.parseDouble(texto);
        
        if(valor <= 0 ){
            System.out.println("informe um valor maior que 0");
            
        }
        return valor;
    } 
    public void atualizarSaldo(){
        if(is_saldo_visivel){
            lblSaldo.setText("Saldo Atual: R$" + saldo);
        }else{
            //caso o saldo esteja oculto
            lblSaldo.setText("Saldo atual: R$ ***,**");
        }
    }

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
        java.awt.EventQueue.invokeLater(() -> new CaixaInterface().setVisible(true));
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Depositar;
    private javax.swing.JButton Sacar;
    private javax.swing.JButton consultarSaldo;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField textSaldo;
    // End of variables declaration//GEN-END:variables
}
