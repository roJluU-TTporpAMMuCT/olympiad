package org.nakedprogrammer.olympiad;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Userok;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TestDataLoader implements ApplicationRunner {

    private UserRepo userRepo;
    private PasswordEncoder passEnc;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepo.save(new Userok(null, passEnc.encode("negros"), "negro", null, null, null));
    }
}
