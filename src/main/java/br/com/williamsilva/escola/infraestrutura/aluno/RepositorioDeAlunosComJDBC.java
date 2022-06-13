package br.com.williamsilva.escola.infraestrutura.aluno;

import br.com.williamsilva.escola.dominio.aluno.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioDeAlunosComJDBC implements RepositorioDeAlunos {

    private final Connection connection;

    public RepositorioDeAlunosComJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void matricular(Aluno aluno) {
        String sql = "INSERT INTO ALUNO VALUES(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getEmail());
            ps.execute();

            sql = "INSERT INTO TELEFONE VALUES (?, ?)";
            ps = connection.prepareStatement(sql);

            for (Telefone telefone : aluno.getTelefones()) {
                ps.setString(1, telefone.getDdd());
                ps.setString(2, telefone.getNumero());
                ps.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Aluno buscarPorCPF(CPF cpf) {
        try {
            String sql = "SELECT id, nome, email FROM ALUNO WHERE cpf = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cpf.getNumero());

            ResultSet rs = ps.executeQuery();
            boolean encontrou = rs.next();
            if (!encontrou) {
                throw new AlunoNaoEncontrado(cpf);
            }

            String nome = rs.getString("nome");
            Email email = new Email(rs.getString("email"));
            Aluno alunoEncontrado = new Aluno(cpf, nome, email);

            Long id = rs.getLong("id");
            sql = "SELECT ddd, numero FROM TELEFONE WHERE aluno = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                String numero = rs.getString("numero");
                String ddd = rs.getString("ddd");
                alunoEncontrado.adicionarTelefone(ddd, numero);
            }

            return alunoEncontrado;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Aluno> listarTodosAlunosMatriculados() {
        try {
            String sql = "SELECT id, nome, email, cpf FROM ALUNO";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Aluno> alunos = new ArrayList<>();

            while (rs.next()) {
                CPF cpf = new CPF(rs.getString("cpf"));
                String nome = rs.getString("nome");
                Email email = new Email(rs.getString("email"));
                Aluno aluno = new Aluno(cpf, nome, email);

                Long id = rs.getLong("id");
                String sqlTelefone = "SELECT ddd, numero FROM TELEFONE WHERE aluno_id = ?";
                PreparedStatement psTelefone = connection.prepareStatement(sqlTelefone);
                psTelefone.setLong(1, id);
                ResultSet rsTelefone = psTelefone.executeQuery();
                while (rsTelefone.next()) {
                    String numero = rsTelefone.getString("numero");
                    String ddd = rsTelefone.getString("ddd");
                    aluno.adicionarTelefone(ddd, numero);
                }

                alunos.add(aluno);
            }

            return alunos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
