package group1.spring_server.repository;

import group1.spring_server.domain.model.ChecklistItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ChecklistItemRepository extends CrudRepository<ChecklistItem,Integer> {
}
