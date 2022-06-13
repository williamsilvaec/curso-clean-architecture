package br.com.williamsilva.escola.aplicacao.aluno.matricular;

import br.com.williamsilva.escola.dominio.aluno.Aluno;
import br.com.williamsilva.escola.dominio.aluno.CPF;
import br.com.williamsilva.escola.infraestrutura.aluno.RepositorioDeAlunosEmMemoria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatricularAlunoTest {

    @Test
    void alunoDeveriaSerPersistido() {
        RepositorioDeAlunosEmMemoria repositorio = new RepositorioDeAlunosEmMemoria();
        MatricularAluno useCase = new MatricularAluno(repositorio);

        MatricularAlunoDto dados = new MatricularAlunoDto(
                "Fulano",
                "123.456.778-99",
                "fulano@email.com");

        useCase.executa(dados);

        Aluno alunoEncontrado = repositorio.buscarPorCPF(new CPF(dados.getCpf()));

        assertEquals("Fulano", alunoEncontrado.getNome());
        assertEquals("123.456.778-99", alunoEncontrado.getCpf());
        assertEquals("fulano@email.com", alunoEncontrado.getEmail());
    }
}