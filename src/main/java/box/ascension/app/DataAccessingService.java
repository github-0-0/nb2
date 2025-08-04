package box.ascension.app;

import org.springframework.stereotype.Service;

import box.ascension.app.nb2.game.TeamRepository;
import box.ascension.app.nb2.game.neurons.Neuron;
import box.ascension.app.nb2.game.neurons.NeuronRepository;
import jakarta.annotation.PostConstruct;
import box.ascension.app.nb2.game.Team;

import org.springframework.boot.CommandLineRunner;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DataAccessingService implements CommandLineRunner {

    private final NeuronRepository neuronRepository;
    private final TeamRepository teamRepository;

    public static DataAccessingService instance;

    @Autowired
    public DataAccessingService(NeuronRepository neuronRepository, TeamRepository teamRepository) {
        this.neuronRepository = neuronRepository;
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    @Override
    public void run(String... args) {
        teamRepository.deleteAll();
        neuronRepository.deleteAll();


        var team1 = new Team("_TestTeam1");
        var team2 = new Team("_TestTeam2");
        var neuron1 = new Neuron("_TestNeuron1");
        var neuron2 = new Neuron("_TestNeuron2");
        var neuron3 = new Neuron("_TestNeuron3");
        var neuron4 = new Neuron("_TestNeuron4");
        var neuron5 = new Neuron("_TestNeuron5");
        var neuron6 = new Neuron("_TestNeuron6");
        var neuron7 = new Neuron("_TestNeuron7");
        var neuron8 = new Neuron("_TestNeuron8");
        var neuron9 = new Neuron("_TestNeuron9");
        var neuron10 = new Neuron("_TestNeuron10");
        team1.neurons.add(neuron1.id);
        team1.neurons.add(neuron2.id);
        team1.neurons.add(neuron3.id);
        team1.neurons.add(neuron4.id);
        team1.neurons.add(neuron5.id);
        team2.neurons.add(neuron6.id);
        team2.neurons.add(neuron7.id);
        team2.neurons.add(neuron8.id);
        team2.neurons.add(neuron9.id);
        team2.neurons.add(neuron10.id);
        teamRepository.save(team1);
        teamRepository.save(team2);
        neuronRepository.save(neuron1);
        neuronRepository.save(neuron2);
        neuronRepository.save(neuron3);
        neuronRepository.save(neuron4);
        neuronRepository.save(neuron5);
        neuronRepository.save(neuron6);
        neuronRepository.save(neuron7);
        neuronRepository.save(neuron8);
        neuronRepository.save(neuron9);
        neuronRepository.save(neuron10);

        teamRepository.findAll().forEach(p ->
            System.out.println(p.toString()));

        neuronRepository.findAll().forEach(p ->
            System.out.println(p.toString()));
    }

    public long getTeamId(String name) {
        var team = teamRepository.findByName(name);
        if (team.isPresent()) {
            return team.get().id;
        }
        return -1;
    }

    public Optional<Team> getTeam(long id) {
        return teamRepository.findById(id);
    }

    public Optional<Neuron> getNeuron(long id) {
        return neuronRepository.findById(id);
    }

}