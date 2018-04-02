package group1.spring_server.service;

import group1.spring_server.domain.model.Checklist;
import group1.spring_server.domain.model.ChecklistItem;
import group1.spring_server.domain.model.Template;
import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.FailedAddChecklistException;
import group1.spring_server.exceptions.FailedAddCheklistItemException;
import group1.spring_server.exceptions.ForbiddenException;
import group1.spring_server.exceptions.NoSuchChecklistException;
import group1.spring_server.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistItemService checklistItemService;


    public Template addTemplate(Template checklist) throws FailedAddChecklistException {

        try {
            return templateRepository.save(checklist);
        } catch (Exception e) {
            throw new FailedAddChecklistException();
        }
    }

    public Iterable<Template> getTemplates() {

        return templateRepository.findAll();
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Template getTemplate(int id, String userId) throws NoSuchChecklistException, ForbiddenException {

        Optional<Template> optionalTemplate = templateRepository.findById(id);

        //verification if lists exists in database
        if (!optionalTemplate.isPresent()) throw new NoSuchChecklistException();

        Template checklist = optionalTemplate.get();

        //verification of user access to the list of the item
        if (checklist.getUser_id().compareTo(userId) != 0) throw new ForbiddenException();

        return checklist;

    }

    @Transactional
    public Checklist useTemplate(Template x, String checkListName) {
        //TODO throw an exception instead
        if (x == null) return null;


        Checklist returnList = new Checklist();
        returnList.setName(checkListName);
        returnList.setUser_id(x.getUser_id());

        try {
            returnList = checklistService.addChecklist(returnList);

            Checklist finalReturnList = returnList;
            x.getItems().forEach(item -> {
                mapTemplateItem(item, finalReturnList.getId());
            });

            return returnList;

        } catch (FailedAddChecklistException e) {
            e.printStackTrace();
            //TODO Send error
            return null;
        }


    }


    private ChecklistItem mapTemplateItem(TemplateItem templateItem, int listId) {
        ChecklistItem item = new ChecklistItem();
        String state = "uncompleted";
        item.setDescription(templateItem.getDescription());
        item.setName(templateItem.getName());
        item.setState(state);
        item.setlist_id(listId);
        try {
            return checklistItemService.addCheckListItem(item);
        } catch (FailedAddCheklistItemException e) {
            return null;
        }


    }
}
