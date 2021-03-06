package group1.spring_server.service;

import group1.spring_server.domain.model.Checklist;
import group1.spring_server.domain.model.ChecklistItem;
import group1.spring_server.domain.model.Template;
import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.*;
import group1.spring_server.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ChecklistService checklistService;

    @Autowired
    private ChecklistItemService checklistItemService;

    @Autowired
    private TemplateItemService templateItemService;


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

    public Iterable<Template> getUserTemplates(String user_id) {

        Iterable<Template> all = templateRepository.findAll();
        return () -> StreamSupport.stream(all.spliterator(), false)
                .filter(item -> item.getUser_id().equals(user_id))
                .iterator();
    }


    public Template getTemplate(int id, String userId) throws NoSuchChecklistException, ForbiddenException, NoSuchTemplateException {

        Optional<Template> optionalTemplate = templateRepository.findById(id);

        //verification if lists exists in database
        if (!optionalTemplate.isPresent()) throw new NoSuchTemplateException();

        Template checklist = optionalTemplate.get();

        //verification of user access to the list of the item
        if (!checklist.getUser_id().equals(userId)) throw new ForbiddenException();

        return checklist;

    }

    @Transactional
    public Checklist useTemplate(Template x, String checkListName) throws MyException {

        Checklist returnList = new Checklist();
        returnList.setName(checkListName);
        returnList.setUser_id(x.getUser_id());

        returnList = checklistService.addChecklist(returnList);

        Checklist finalReturnList = returnList;
        templateItemService.getItemsforTemplate(x.getId(), x.getUser_id()).forEach(item -> {
            mapTemplateItem(item, finalReturnList.getId());
        });

        return returnList;


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
    public Template updateTemplate(Template checklist) throws NoSuchChecklistException {
        Optional<Template> optionalChecklist = templateRepository.findById(checklist.getId());


        //verification if lists exists in database
        if (!optionalChecklist.isPresent()) throw new NoSuchChecklistException();


        Template checklistToEdit = optionalChecklist.get();
        checklistToEdit.setName(checklist.getName());

        templateRepository.save(checklistToEdit);
        return checklistToEdit;

    }
}
