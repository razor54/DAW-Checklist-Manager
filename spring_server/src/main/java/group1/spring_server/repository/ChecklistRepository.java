package group1.spring_server.repository;

import group1.spring_server.domain.model.Checklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends CrudRepository<Checklist, Integer> {
}
