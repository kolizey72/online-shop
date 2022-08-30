package com.github.kolizey72.onlineshop.service;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistration(new Date());
        user.setRole("ROLE_USER");
        user.setBanned(false);
        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User updatedUser) {
        userRepository.findById(id).ifPresent(userToUpdate -> {
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setUserClass(updatedUser.getUserClass());
            userToUpdate.setUserProfession(updatedUser.getUserProfession());
            userToUpdate.setBirthday(updatedUser.getBirthday());
        });
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
