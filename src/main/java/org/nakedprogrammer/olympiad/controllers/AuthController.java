package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Userok;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class AuthController {
    private UserRepo userRepository;
    private SessionUserHolder holder;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Userok userForm, @RequestHeader(name = "Authorization") String token) {
        Userok user = userRepository.findAnyByUsername(userForm.getUsername());
        if(user == null || !user.getPassword().equals(userForm.getPassword()))
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        holder.container.put(token, user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Userok user) {
        if (userRepository.findAnyByUsername(user.getUsername()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username is already taken");

        user = userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
