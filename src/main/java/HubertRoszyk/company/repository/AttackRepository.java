package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Attack;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttackRepository extends JpaRepository<Attack, Integer> {
    List<Attack> findByUserId(int userId);
    //List<Attack> findByDefencePlanetId(int defencePlanetId);

    @Query("select a from Attack a where a.defencePlanet.id = ?1")
    List<Attack> findByDefencePlanet_Id(int id);


    @Override
    @EntityGraph(attributePaths = {
            "user",
            "army",
            "defencePlanet"
    })
    Optional<Attack> findById(Integer integer);
}
