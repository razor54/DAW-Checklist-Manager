package group1.spring_server.service;

import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.*;
import group1.spring_server.repository.TemplateItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateItemService {

    @Autowired
    private TemplateItemRepository templateItemRepository;

    @Autowired
    private TemplateService templateService;


    public TemplateItem addTemplateItem(TemplateItem template) throws FailedAddCheklistItemException {

        try {
            return templateItemRepository.save(template);
        } catch (Exception e) {
            throw new FailedAddCheklistItemException();
        }
    }

    public Iterable<TemplateItem> getItemsforTemplate(int templateId,String userId) throws MyException {
        return templateService.getTemplate(templateId,userId).getItems();
    }

    public TemplateItem getTemplateItem(int itemId, String userId) throws MyException {

        Optional<TemplateItem> checklistItemOptional = templateItemRepository.findById(itemId);

        //verification if item exists in database
        if (!checklistItemOptional.isPresent()) throw new NoSuchTemplateItemException();

        TemplateItem templateItem = checklistItemOptional.get();

        //verification of user access to the list of the item
        templateService.getTemplate(
                templateItem.getTemplate_id(),
                userId
        );

        return templateItem;
    }
}
