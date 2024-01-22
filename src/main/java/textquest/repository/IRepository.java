package textquest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import textquest.model.BaseEntity;

@NoRepositoryBean
public interface IRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

}
