package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Galaxy;
import HubertRoszyk.company.entiti_class.GalaxyPoints;
import HubertRoszyk.company.entiti_class.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class FactoryGalaxyPointsRepositoryTest {
    @Autowired
    GalaxyPointsRepository galaxyPointsRepository;

    @Test
    void itShouldFindPointsByUserId() {
        //given
        User user = new User();
        Galaxy galaxy = new Galaxy();

        GalaxyPoints galaxyPoints = new GalaxyPoints(user, galaxy);

        galaxyPointsRepository.save(galaxyPoints);

        //when
        Set<GalaxyPoints> gotPoints = galaxyPointsRepository.findGalaxy_PointsByUserId(user.getId());
        //then
        assertThat(gotPoints).contains(galaxyPoints);
    }
    @Test
    void itShouldFindPointsByUserIdAndGalaxyId() {
        //given
        User user = new User();
        Galaxy galaxy = new Galaxy();

        GalaxyPoints galaxyPoints = new GalaxyPoints(user, galaxy);

        galaxyPointsRepository.save(galaxyPoints);

        //when
        GalaxyPoints gotGalaxyPoints = galaxyPointsRepository.findGalaxy_PointsByUserIdAndGalaxyId(user.getId(), galaxy.getId());

        //then
        assertThat(gotGalaxyPoints).isEqualTo(galaxyPoints);
    }
}