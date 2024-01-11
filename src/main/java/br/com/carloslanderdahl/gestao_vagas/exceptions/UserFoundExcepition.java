package br.com.carloslanderdahl.gestao_vagas.exceptions;

public class UserFoundExcepition extends RuntimeException{
  public UserFoundExcepition() {
    super("Usuário já existe");
  }
}
