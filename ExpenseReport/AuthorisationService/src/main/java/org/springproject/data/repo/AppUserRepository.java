package org.springproject.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.data.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<Long, AppUser> {

    Optional<AppUser> findByEmail(String email);
    boolean existsEmail(String email);
}
