package box.ascension.app.nb2.game;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    List<Team> findByName(String lastName);
  
    Team findById(long id);

}
