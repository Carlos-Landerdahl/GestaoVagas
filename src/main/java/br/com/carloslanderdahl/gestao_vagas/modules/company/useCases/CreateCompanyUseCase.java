package br.com.carloslanderdahl.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carloslanderdahl.gestao_vagas.exceptions.UserFoundExcepition;
import br.com.carloslanderdahl.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.carloslanderdahl.gestao_vagas.modules.company.repositories.CompanyRepositoy;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepositoy companyRepositoy;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepositoy.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundExcepition();
        });
     return this.companyRepositoy.save(companyEntity);
  }
}
