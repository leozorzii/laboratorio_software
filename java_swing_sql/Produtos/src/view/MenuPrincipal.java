package view;

/**
 *
 * @author leo zorzi
 */
public class MenuPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuPrincipal.class.getName());

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menu_cadastros = new javax.swing.JMenu();
        menu_cadProduto = new javax.swing.JMenuItem();
        menu_categorias = new javax.swing.JMenuItem();
        menu_consultas = new javax.swing.JMenu();
        menu_consultaProduto = new javax.swing.JMenuItem();
        menu_relatorios = new javax.swing.JMenu();
        menu_relatorioProdutos = new javax.swing.JMenuItem();
        menu_sair = new javax.swing.JMenu();
        menu_fechar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Produtos");

        menu_cadastros.setText("Cadastros");
        menu_cadProduto.setText("Cadastrar Produto");
        menu_cadProduto.addActionListener(this::menu_cadProdutoActionPerformed);
        menu_cadastros.add(menu_cadProduto);
        menu_categorias.setText("Categorias (cadastrar / editar / listar)");
        menu_categorias.addActionListener(this::menu_categoriasActionPerformed);
        menu_cadastros.add(menu_categorias);

        jMenuBar1.add(menu_cadastros);

        menu_consultas.setText("Consultas");
        menu_consultaProduto.setText("Consultar / Editar / Excluir Produto");
        menu_consultaProduto.addActionListener(this::menu_consultaProdutoActionPerformed);
        menu_consultas.add(menu_consultaProduto);

        jMenuBar1.add(menu_consultas);

        menu_relatorios.setText("Relatorios");
        menu_relatorioProdutos.setText("Produtos (com busca)");
        menu_relatorioProdutos.addActionListener(this::menu_relatorioProdutosActionPerformed);
        menu_relatorios.add(menu_relatorioProdutos);

        jMenuBar1.add(menu_relatorios);

        menu_sair.setText("Sair");
        menu_fechar.setText("Fechar sistema");
        menu_fechar.addActionListener(this::menu_fecharActionPerformed);
        menu_sair.add(menu_fechar);

        jMenuBar1.add(menu_sair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_cadProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_cadProdutoActionPerformed
        new FormCadastroProduto().setVisible(true);
    }//GEN-LAST:event_menu_cadProdutoActionPerformed

    private void menu_categoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_categoriasActionPerformed
        new FormCategorias().setVisible(true);
    }//GEN-LAST:event_menu_categoriasActionPerformed

    private void menu_consultaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_consultaProdutoActionPerformed
        new FormConsultaProduto().setVisible(true);
    }//GEN-LAST:event_menu_consultaProdutoActionPerformed

    private void menu_relatorioProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_relatorioProdutosActionPerformed
        new RelatorioProdutos().setVisible(true);
    }//GEN-LAST:event_menu_relatorioProdutosActionPerformed

    private void menu_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_fecharActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menu_fecharActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menu_cadastros;
    private javax.swing.JMenuItem menu_cadProduto;
    private javax.swing.JMenuItem menu_categorias;
    private javax.swing.JMenu menu_consultas;
    private javax.swing.JMenuItem menu_consultaProduto;
    private javax.swing.JMenu menu_relatorios;
    private javax.swing.JMenuItem menu_relatorioProdutos;
    private javax.swing.JMenu menu_sair;
    private javax.swing.JMenuItem menu_fechar;
    // End of variables declaration//GEN-END:variables
}
