package produtos;

import view.MenuPrincipal;

/**
 * Ponto de entrada alternativo: abre o menu principal.
 * (No NetBeans a classe principal tambem pode ser view.MenuPrincipal.)
 *
 * @author leo zorzi
 */
public class Produtos {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }

}
