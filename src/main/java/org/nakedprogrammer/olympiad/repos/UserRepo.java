package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Userok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Userok, Long> {

    Userok findAnyByUsername(String username);
}
