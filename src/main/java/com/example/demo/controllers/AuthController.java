package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.dto.PersonDTO;
import com.example.demo.models.Role;
import com.example.demo.models.RoleRepository;
import com.example.demo.models.PersonRepository;
import com.example.demo.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.util.HashSet;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody PersonDTO personDTO) {
        Person person = new Person();
        person.setName(personDTO.getName());
        person.setEmail(personDTO.getEmail());
        person.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        person.setRoles(new HashSet<>());
        person.getRoles().add(userRole);
        personRepository.save(person);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PersonDTO personDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        personDTO.getEmail(),
                        personDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(personDTO.getEmail());
        return ResponseEntity.ok(token);
    }
}