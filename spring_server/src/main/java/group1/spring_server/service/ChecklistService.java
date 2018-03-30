package group1.spring_server.service;

import group1.spring_server.domain.Checklist;
import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.exceptions.FailedAddChecklistException;
import group1.spring_server.exceptions.FailedAddCheklistItemException;
import group1.spring_server.exceptions.ForbiddenException;
import group1.spring_server.exceptions.NoSuchChecklistException;
import group1.spring_server.repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ChecklistService {

    @Autowired
    ChecklistRepository checklistRepository;

    //public ChecklistService(ChecklistRepository repository){ this.checklistRepository = repository; }

    public Checklist addChecklist(Checklist checklist) throws FailedAddChecklistException {

        try {
            return checklistRepository.save(checklist);
        }catch (Exception e ){
            throw new FailedAddChecklistException();
        }
    }


    public Iterable<Checklist> getChecklists() {

        return checklistRepository.findAll();
    }

    public Checklist getChecklist(int id, String userId) throws NoSuchChecklistException, ForbiddenException {

        Optional<Checklist> optionalChecklist= checklistRepository.findById(id);


        //verification if lists exists in database
        if(!optionalChecklist.isPresent()) throw new NoSuchChecklistException();


        Checklist checklist  = optionalChecklist.get();

        //verification of user access to the list of the item
        if(checklist.getUser_id().compareTo(userId)!=0) throw  new ForbiddenException();


        return checklist;

    }
}
