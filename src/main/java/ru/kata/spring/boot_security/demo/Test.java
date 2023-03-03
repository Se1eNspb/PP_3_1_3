package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Component
public class Test {
    private final UserRepository userRepository;

    @Autowired
    public Test(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    @Transactional
    public void addDefaultUsers() {
        User user = new User("user", "user", "user@gmail.com");
        Role roleUser = new Role("ROLE_USER");
        user.addRoleToUser(roleUser);
        roleUser.addUserToRole(user);
        User admin = new User("admin", "admin", "admin@gmail.com");
        Role roleAdmin = new Role("ROLE_ADMIN");
        admin.addRoleToUser(roleAdmin);
        admin.addRoleToUser(roleUser);
        roleAdmin.addUserToRole(admin);
        userRepository.save(user);
        userRepository.save(admin);
    }
}
