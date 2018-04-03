package group1.spring_server.repository;


import group1.spring_server.domain.model.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TemplateRepository extends CrudRepository<Template, Integer> {
}
