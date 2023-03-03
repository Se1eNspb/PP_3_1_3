package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
@Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
    this.roleRepository = roleRepository;
}
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        Hibernate.initialize(user.get().getRoles());
        return new UserDetailsImp(user.get(), user.get().getRoles());
    }
    @Transactional(readOnly = true)
    public List<User> index() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User read(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    public void create(User user) {
        userRepository.save(user);
    }

    public void update(int id, User user) {
        user.setId(id);
        userRepository.save(user);
    }
    public void delete(int id) {
        userRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Role> listAllRoles(){
    return roleRepository.findAll();
    }
}
