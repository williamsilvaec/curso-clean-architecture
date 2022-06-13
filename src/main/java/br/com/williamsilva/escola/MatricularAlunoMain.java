package br.com.williamsilva.escola;

import br.com.williamsilva.escola.aplicacao.aluno.matricular.MatricularAluno;
import br.com.williamsilva.escola.aplicacao.aluno.matricular.MatricularAlunoDto;
import br.com.williamsilva.escola.infraestrutura.aluno.RepositorioDeAlunosEmMemoria;

public class MatricularAlunoMain {

    public static void main(String[] args) {
        String nome = "Fulano da Silva";
        String cpf = "123.456.321-00";
        String email = "fulano@email.com";

        RepositorioDeAlunosEmMemoria repositorioDeAlunosEmMemoria = new RepositorioDeAlunosEmMemoria();

        MatricularAluno matricularAluno = new MatricularAluno(repositorioDeAlunosEmMemoria);
        matricularAluno.executa(new MatricularAlunoDto(nome, cpf, email));
    }
}
