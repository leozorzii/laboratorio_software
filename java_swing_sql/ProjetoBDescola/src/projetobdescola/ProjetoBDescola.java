/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetobdescola;

import Beans.Aluno;
import Beans.Professor;
import DAO.AlunoDAO;
import DAO.ProfessorDAO;
import conexao.Conexao;

/**
 *
 * @author leo zorzi
 */
public class ProjetoBDescola {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexao c = new Conexao();
        c.getConexao();
        Aluno a = new Aluno();
        a.setNome("Leozorzi");
        a.setIdade(20);
        a.setCurso("Sistemas de Informação");
        AlunoDAO adao = new AlunoDAO();
        adao.inserir(a);
        
        Professor p = new Professor();
        p.setNome("Ricardo");
        p.setIdade(35);
        p.setDisciplina("Laboratorio de Software");
        ProfessorDAO pdao = new ProfessorDAO();
        pdao.inserir(p);
        
                
    }
}
