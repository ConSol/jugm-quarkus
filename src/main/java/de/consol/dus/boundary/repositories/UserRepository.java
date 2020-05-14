package de.consol.dus.boundary.repositories;

import de.consol.dus.boundary.entities.UserEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

  public Optional<UserEntity> findByName(String name) {
    return find("name", name).firstResultOptional();
  }
}
