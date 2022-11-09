package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Attack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<Attack, Integer> {
    List<Attack> findBattleByUserId(int userId);
    List<Attack> findAttackByDefencePlanetId(int defencePlanetId);
}
