package br.com.carloslanderdahl.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.carloslanderdahl.gestao_vagas.exceptions.UserFoundExcepition;
import br.com.carloslanderdahl.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.carloslanderdahl.gestao_vagas.modules.company.repositories.CompanyRepositoy;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepositoy companyRepositoy;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepositoy.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundExcepition();
        });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

     return this.companyRepositoy.save(companyEntity);
  }
}
