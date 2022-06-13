package br.com.williamsilva.escola.aplicacao.indicacao;

import br.com.williamsilva.escola.dominio.aluno.Aluno;

public interface EnviaEmailIndicacao {

    void enviarPara(Aluno indicado);
}
