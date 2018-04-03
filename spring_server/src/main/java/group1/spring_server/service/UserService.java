package group1.spring_server.service;


import group1.spring_server.domain.model.User;
import group1.spring_server.exceptions.FailedAddUserException;
import group1.spring_server.exceptions.NoSuchUserException;
import group1.spring_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User addUser(User user) throws FailedAddUserException {

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new FailedAddUserException();
        }
    }


    public Iterable<User> getUsers() {

        return userRepository.findAll();
    }


    public User getUser(String id) throws NoSuchUserException {

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) return user.get();

        throw new NoSuchUserException();

    }
}