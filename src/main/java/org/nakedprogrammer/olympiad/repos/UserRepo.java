package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Userok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET})
@Repository
public interface UserRepo extends JpaRepository<Userok, Long> {

    Userok findAnyByUsername(String username);
}
