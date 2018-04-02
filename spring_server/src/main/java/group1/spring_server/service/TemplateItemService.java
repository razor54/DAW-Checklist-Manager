package group1.spring_server.service;

import group1.spring_server.domain.model.TemplateItem;
import group1.spring_server.exceptions.FailedAddCheklistItemException;
import group1.spring_server.repository.TemplateItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
