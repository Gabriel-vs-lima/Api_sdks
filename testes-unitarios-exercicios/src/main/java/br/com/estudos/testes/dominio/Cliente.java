package br.com.estudos.testes.dominio;

import java.util.Objects;

public class Cliente {

    private Long id;
    private String nome;
    private String email;
    private boolean ativo = true;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente outro)) {
            return false;
        }
        return Objects.equals(id, outro.id) && Objects.equals(email, outro.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nome='" + nome + "', email='" + email + "', ativo=" + ativo + '}';
    }
}
