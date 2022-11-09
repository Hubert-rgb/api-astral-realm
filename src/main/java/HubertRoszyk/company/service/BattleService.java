package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.Attack;
import HubertRoszyk.company.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {
    @Autowired
    BattleRepository repository;

    public Attack saveBattle(Attack attack) {
        return repository.save(attack);
    }

    public List<Attack> saveBattlesList(List<Attack> attacks) {
        return repository.saveAll(attacks);
    }

    public List<Attack> getBattlesList() {
        return repository.findAll();
    }

    public Attack getBattleById(int id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteBattle() {
        repository.deleteAll();
        System.out.println("All Battles deleted");
    }

    public List<Attack> getBattleByUserId(int userId) {
        return repository.findBattleByUserId(userId);
    }

    public List<Attack> getAttackByDefencePlanetId(int planetId){
        return repository.findAttackByDefencePlanetId(planetId);
    }
}
