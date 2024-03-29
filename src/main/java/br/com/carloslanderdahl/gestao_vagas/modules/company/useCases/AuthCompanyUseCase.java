package br.com.carloslanderdahl.gestao_vagas.modules.company.useCases;

import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.carloslanderdahl.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.carloslanderdahl.gestao_vagas.modules.company.repositories.CompanyRepositoy;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secreteKey;

  @Autowired
  private CompanyRepositoy companyRepositoy;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = this.companyRepositoy.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
        () -> {
          throw new UsernameNotFoundException("Username/password incorrect");
        });

    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
    if (!passwordMatches) {
      throw new AuthenticationException();
    } else {
      Algorithm algorithm = Algorithm.HMAC256(secreteKey);
      var token = JWT.create().withIssuer("javagas")
          .withExpiresAt(Instant.now().plus(java.time.Duration.ofHours(2)))
          .withSubject(company.getId().toString())
          .sign(algorithm);
      return token;
    }
  }
}
