package box.ascension.app;

import org.springframework.stereotype.Service;

import box.ascension.app.nb2.game.TeamRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DataAccessingService implements CommandLineRunner {

    private final TeamRepository teamRepository;

    @Autowired
    public DataAccessingService(TeamRepository repository) {
        this.teamRepository = repository;
    }

    @Override
    public void run(String... args) {
        teamRepository.findAll().forEach(p ->
            System.out.println(p.toString()));
    }

}