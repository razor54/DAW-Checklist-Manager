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
public class UserService {

    @Autowired
    UserRepository userRepository;


    //public CheckListService(CheckListRepository repository){this.userRepository = repository;}

    public User addUser(User user) throws FailedAddUserException {

        try {
            return userRepository.save(user);
        }
        catch (Exception e){
            throw new FailedAddUserException();
        }
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Iterable<User> getUsers(){

        Iterable<User>users =userRepository.findAll();
        return users;
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public User getUser(String id) throws NoSuchUserException {

        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) return user.get();

        throw  new NoSuchUserException();

    }
}