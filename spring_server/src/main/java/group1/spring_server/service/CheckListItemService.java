package group1.spring_server.service;


import group1.spring_server.domain.ChecklistItem;
import group1.spring_server.repository.CheckListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckListItemService {

    //@Autowired
    CheckListItemRepository checkListRepository;

    public CheckListItemService(CheckListItemRepository repository){
        this.checkListRepository = repository;
    }

    public ChecklistItem addCheckList(ChecklistItem checklist) {
        checkListRepository.save(checklist);

        return checklist;
    }


    public Iterable<ChecklistItem> getCheckListItems() {

        Iterable<ChecklistItem> as = checkListRepository.findAll();
        return as;
    }

    public ChecklistItem getCheckListItem(int id) {

        //todo throw exception if err
        return checkListRepository.findById(id).get();
    }

}