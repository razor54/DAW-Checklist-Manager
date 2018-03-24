package group1.spring_server.service;


import group1.spring_server.domain.User;
import group1.spring_server.exceptions.FailedAddUserException;
import group1.spring_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Iterable<User> getUsers(){

        Iterable<User> as = userRepository.findAll();
        return as;
    }

    public User getUser(int id){

        //todo throw exception if err
        return userRepository.findById(id).get();
    }
}