package com.github.kolizey72.onlineshop.service;

import com.github.kolizey72.onlineshop.entity.User;
import com.github.kolizey72.onlineshop.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void create(User user) {
        user.setRegistration(new Date());
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
            userToUpdate.setBanned(updatedUser.getBanned());
        });
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
