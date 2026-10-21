package view;

import Beans.Aluno;
import DAO.AlunoDAO;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leo zorzi
 */
public class RelatorioAlunosFiltro extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RelatorioAlunosFiltro.class.getName());

    /**
     * Creates new form RelatorioAlunosFiltro
     */
    public RelatorioAlunosFiltro() {
        initComponents();
        cmb_curso.setSelectedIndex(-1);
        preeencheTabela();
    }

     public void preeencheTabela() {
        AlunoDAO aDAO = new AlunoDAO();
        String curso;
        if(cmb_curso.getSelectedIndex() == -1){
            curso = "";
        }else{
            curso = cmb_curso.getSelectedItem().toString();
        }
        List<Aluno> listaAlunos = aDAO.getAlunos(txt_nome.getText(), curso);

        DefaultTableModel tabelaAlunos = (DefaultTableModel) tbl_relatorio_alunos.getModel();
        tabelaAlunos.setNumRows(0);
        for (Aluno a : listaAlunos) {
            Object[] obj = new Object[]{
                a.getId(),
                a.getNome(),
                a.getIdade(),
                a.getCurso()};
            tabelaAlunos.addRow(obj);
        }
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_relatorio_alunos = new javax.swing.JTable();
        lbl_titulo = new javax.swing.JLabel();
        lbl_nome = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        btn_clear = new javax.swing.JButton();
        cmb_curso = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tbl_relatorio_alunos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nome", "idade", "curso"
            }
        ));
        jScrollPane1.setViewportView(tbl_relatorio_alunos);

        lbl_titulo.setForeground(new java.awt.Color(255, 0, 0));
        lbl_titulo.setText("Relatorio de Alunos com filtro");

        lbl_nome.setText("Nome para filtrar");

        txt_nome.addCaretListener(this::txt_nomeCaretUpdate);

        btn_clear.setText("clear");
        btn_clear.addActionListener(this::btn_clearActionPerformed);

        cmb_curso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sistemas de Informação", "Ciência da Computação" }));
        cmb_curso.addActionListener(this::cmb_cursoActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbl_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbl_titulo)
                        .addGap(145, 145, 145))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(cmb_curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_clear)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbl_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_clear)
                    .addComponent(cmb_curso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nomeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txt_nomeCaretUpdate
        preeencheTabela();
    }//GEN-LAST:event_txt_nomeCaretUpdate

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        txt_nome.setText("");
        cmb_curso.setSelectedIndex(-1);
        preeencheTabela();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void cmb_cursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_cursoActionPerformed
        preeencheTabela();
    }//GEN-LAST:event_cmb_cursoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new RelatorioAlunosFiltro().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JComboBox<String> cmb_curso;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_nome;
    private javax.swing.JLabel lbl_titulo;
    private javax.swing.JTable tbl_relatorio_alunos;
    private javax.swing.JTextField txt_nome;
    // End of variables declaration//GEN-END:variables
}
