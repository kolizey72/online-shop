package com.github.kolizey72.onlineshop.service;

import com.github.kolizey72.onlineshop.dto.UserRegistrationForm;
import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final long DEFAULT_ADMIN_ID = 1;
    private final long DEFAULT_USER_ID = 2;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public List<User> findAllOrdered(int page, int userPerPage) {
        return userRepository.findAll(PageRequest.of(page, userPerPage, Sort.by("id"))).getContent();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public long count() {
        return userRepository.count();
    }

    @Transactional
    public void register(UserRegistrationForm userForm) {
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));

        User user = new User(userForm);
        user.setRegistration(new Date());
        user.setRole("ROLE_USER");
        user.setBanned(false);

        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User updatedUser) {
        userRepository.findById(id).ifPresent(userToUpdate -> {
            if (id != DEFAULT_ADMIN_ID && id != DEFAULT_USER_ID) {
                userToUpdate.setUsername(updatedUser.getUsername());
                userToUpdate.setRole(updatedUser.getRole());
                if (userToUpdate.getRole().equals("ROLE_ADMIN")) {
                    userToUpdate.setBanned(false);
                }
            }
            userToUpdate.setEmail(updatedUser.getEmail());
            userToUpdate.setUserClass(updatedUser.getUserClass());
            userToUpdate.setUserProfession(updatedUser.getUserProfession());
            userToUpdate.setBirthday(updatedUser.getBirthday());
        });
    }

    @Transactional
    public void toggleBanned(long id) {
        userRepository.findById(id).ifPresent(user -> {
            if (!user.getRole().equals("ROLE_ADMIN")) {
                user.setBanned(!user.getBanned());
            }
        });
    }

    @Transactional
    public void delete(long id) {
        if (id == DEFAULT_ADMIN_ID || id == DEFAULT_USER_ID) {
            throw new IllegalArgumentException("Can not delete default admin");
        }
        userRepository.deleteById(id);
    }
}
