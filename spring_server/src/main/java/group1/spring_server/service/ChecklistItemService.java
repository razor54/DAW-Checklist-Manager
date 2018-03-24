package group1.spring_server.service;


import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.exceptions.FailedAddCheklistItemException;
import group1.spring_server.exceptions.NoSuchChecklistItemException;
import group1.spring_server.repository.ChecklistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChecklistItemService {

    @Autowired
    ChecklistItemRepository checklistItemRepository;

    //public ChecklistItemService(ChecklistItemRepository checklistItemRepository) { this.checklistItemRepository = checklistItemRepository; }



    public ChecklistItem addCheckListItem(ChecklistItem checklist) throws FailedAddCheklistItemException{

        try{
            return checklistItemRepository.save(checklist);
        }catch (Exception e){
            throw  new FailedAddCheklistItemException();
        }
    }


    public Iterable<ChecklistItem> getChecklistItems() throws NoSuchChecklistItemException {

        return checklistItemRepository.findAll();
    }

    public ChecklistItem getChecklistItem(int id) throws NoSuchChecklistItemException {

        Optional<ChecklistItem> checklistItem= checklistItemRepository.findById(id);

        if(checklistItem.isPresent()) return checklistItem.get();

        throw  new NoSuchChecklistItemException();
    }

}