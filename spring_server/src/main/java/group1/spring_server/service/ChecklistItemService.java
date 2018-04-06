package group1.spring_server.service;


import group1.spring_server.domain.model.Checklist;
import group1.spring_server.domain.model.ChecklistItem;
import group1.spring_server.exceptions.FailedAddCheklistItemException;
import group1.spring_server.exceptions.ForbiddenException;
import group1.spring_server.exceptions.NoSuchChecklistException;
import group1.spring_server.exceptions.NoSuchChecklistItemException;
import group1.spring_server.repository.ChecklistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ChecklistItemService {

    @Autowired
    ChecklistItemRepository checklistItemRepository;

    @Autowired
    private ChecklistService checklistService;

    //public ChecklistItemService(ChecklistItemRepository checklistItemRepository) { this.checklistItemRepository = checklistItemRepository; }


    public ChecklistItem addCheckListItem(ChecklistItem checklist) throws FailedAddCheklistItemException {

        try {
            return checklistItemRepository.save(checklist);
        } catch (Exception e) {
            throw new FailedAddCheklistItemException();
        }
    }



    public Iterable<ChecklistItem> getChecklistItems(int listId, String userId) throws ForbiddenException, NoSuchChecklistException {

        //listed to verify identity
        Checklist list = checklistService.getChecklist(listId, userId);

        Iterable<ChecklistItem> listItems = checklistItemRepository.findAll();


        return () -> StreamSupport.stream(listItems.spliterator(), false)
                .filter(item -> item.getlist_id() == listId)
                .iterator();
    }


    public ChecklistItem getChecklistItem(int id, String userId) throws NoSuchChecklistItemException, ForbiddenException, NoSuchChecklistException {

        Optional<ChecklistItem> checklistItemOptional = checklistItemRepository.findById(id);


        //verification if item exists in database
        if (!checklistItemOptional.isPresent()) throw new NoSuchChecklistItemException();

        ChecklistItem checklistItem = checklistItemOptional.get();

        //verification of user access to the list of the item
        checklistService.getChecklist(
                checklistItem.getlist_id(),
                userId
        );


        return checklistItem;
    }

}