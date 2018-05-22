package group1.spring_server.service;

import group1.spring_server.domain.model.Checklist;
import group1.spring_server.exceptions.FailedAddChecklistException;
import group1.spring_server.exceptions.ForbiddenException;
import group1.spring_server.exceptions.NoSuchChecklistException;
import group1.spring_server.repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;


@Service
@Transactional
public class ChecklistService {

    @Autowired
    ChecklistRepository checklistRepository;

    //public ChecklistService(ChecklistRepository repository){ this.checklistRepository = repository; }

    public Checklist addChecklist(Checklist checklist) throws FailedAddChecklistException {

        try {
            return checklistRepository.save(checklist);
        } catch (Exception e) {
            throw new FailedAddChecklistException();
        }
    }


    public Iterable<Checklist> getChecklists() {

        return checklistRepository.findAll();
    }


    public Iterable<Checklist> getUserCheckLists(String user_id) {

        Iterable<Checklist> all = checklistRepository.findAll();
        return () -> StreamSupport.stream(all.spliterator(), false)
                .filter(item -> item.getUser_id().equals(user_id))
                .iterator();
    }

    public Checklist getChecklist(int id, String userId) throws NoSuchChecklistException, ForbiddenException {

        Optional<Checklist> optionalChecklist = checklistRepository.findById(id);


        //verification if lists exists in database
        if (!optionalChecklist.isPresent()) throw new NoSuchChecklistException();


        Checklist checklist = optionalChecklist.get();

        //verification of user access to the list of the item
        if (!checklist.getUser_id().equals(userId))
            throw new ForbiddenException();


        return checklist;

    }

    public Checklist updateChecklist(Checklist checklist) throws NoSuchChecklistException {
        Optional<Checklist> optionalChecklist = checklistRepository.findById(checklist.getId());


        //verification if lists exists in database
        if (!optionalChecklist.isPresent()) throw new NoSuchChecklistException();


        Checklist checklistToEdit = optionalChecklist.get();
        checklistToEdit.setCompletionDate(checklist.getCompletionDate());
        checklistToEdit.setName(checklist.getName());

        checklistRepository.save(checklistToEdit);
        return checklistToEdit;

    }
}
