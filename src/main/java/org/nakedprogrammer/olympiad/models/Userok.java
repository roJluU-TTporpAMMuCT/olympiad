package org.nakedprogrammer.olympiad.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userok implements UserDetails {

    @Id @GeneratedValue
    private Long id;

    private String password;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Quest> quests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Translation> translations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userok")
    private List<Solution> solutions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
