package br.com.carloslanderdahl.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.carloslanderdahl.gestao_vagas.exceptions.UserFoundExcepition;
import br.com.carloslanderdahl.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.carloslanderdahl.gestao_vagas.modules.candidate.controllers.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundExcepition();
        });
    var password = passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(password);

    return this.candidateRepository.save(candidateEntity);
  }
}
