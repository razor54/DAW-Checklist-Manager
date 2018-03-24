package group1.spring_server.repository;

import group1.spring_server.domain.ChecklistItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistItemRepository extends CrudRepository<ChecklistItem,Integer> {
}
