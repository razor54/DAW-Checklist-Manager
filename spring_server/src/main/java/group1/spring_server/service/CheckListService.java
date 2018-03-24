package group1.spring_server.service;

import group1.spring_server.domain.Checklist;
import group1.spring_server.repository.CheckListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CheckListService {

    @Autowired
    CheckListRepository checkListRepository;

    /*public CheckListService(CheckListRepository repository){
        this.userRepository = repository;
    } */

    public Checklist addCheckList(Checklist checklist) {
        checkListRepository.save(checklist);
        return checklist;
    }


    public Iterable<Checklist> getCheckLists() {

        return checkListRepository.findAll();
    }

    public Checklist getCheckList(int id) {

        //todo throw exception if err
        return checkListRepository.findById(id).get();
    }
}
