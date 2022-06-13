package br.com.williamsilva.escola.dominio.aluno;

import br.com.williamsilva.escola.dominio.aluno.CPF;

public class AlunoNaoEncontrado extends RuntimeException {

    public AlunoNaoEncontrado(CPF cpf) {
        super(String.format("Cpf de número %s não encontrado!", cpf.getNumero()));
    }
}
