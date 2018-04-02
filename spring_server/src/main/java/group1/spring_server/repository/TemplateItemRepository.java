package group1.spring_server.repository;

import group1.spring_server.domain.model.TemplateItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateItemRepository extends CrudRepository<TemplateItem,Integer> {
}
