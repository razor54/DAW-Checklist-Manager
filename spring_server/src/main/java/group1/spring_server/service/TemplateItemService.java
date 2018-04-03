package group1.spring_server.service;

import group1.spring_server.domain.model.Checklist;
import group1.spring_server.domain.model.Template;
import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.*;
import group1.spring_server.repository.TemplateItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
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

    public Iterable<TemplateItem> getItemsforTemplate(int templateId, String userId) throws MyException {

        Template template = templateService.getTemplate(templateId, userId);

        Iterable<TemplateItem> templateItems = templateItemRepository.findAll();

        return () -> StreamSupport.stream(templateItems.spliterator(), false)
                .filter(item -> item.getTemplate_id() == templateId)
                .iterator();

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
