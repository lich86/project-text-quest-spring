package textquest.repository;

import org.springframework.stereotype.Repository;
import textquest.model.Question;

@Repository
public interface QuestionRepository extends IRepository<Question> {
}
