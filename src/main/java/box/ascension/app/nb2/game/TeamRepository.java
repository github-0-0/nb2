package box.ascension.app.nb2.game;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Optional<Team> findByName(String name);

}
