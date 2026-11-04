package Beans;

/**
 * Espelha a tabela categorias (lado "1" da relacao).
 *
 * @author leo zorzi
 */
public class Categoria {
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // o JComboBox mostra o que o toString() devolve
    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }

    // o setSelectedItem() do JComboBox usa equals() para achar o item
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.id == ((Categoria) obj).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
