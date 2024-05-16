package org.nakedprogrammer.olympiad;

import org.nakedprogrammer.olympiad.models.Userok;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableWebSecurity
public class OlympiadApplication {

    @Autowired
    UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(OlympiadApplication.class, args);
    }

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return (String username) -> {
                Userok user = userRepo.findAnyByUsername(username);
                return user;
            };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/quests/**").permitAll()
                        .requestMatchers("/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/**").authenticated()
                )
                .formLogin(formLogin -> formLogin.permitAll())
                //.rememberMe(Customizer.withDefaults())
                .csrf().disable();

        return http.build();
    }

}
