package group1.spring_server.repository;


import group1.spring_server.domain.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer> {
}
