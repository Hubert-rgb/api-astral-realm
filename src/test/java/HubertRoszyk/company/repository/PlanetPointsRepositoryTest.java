/*
package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.enumTypes.PlanetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlanetPointsRepositoryTest {
    @Autowired
    PlanetPointsRepository underTest;

    @Test
    void findArmyPointsByPlanetId() {
        //given
        Planet planet = new Planet(
                PlanetType.SMALL,
                2,
                3,
                4,
                123,
                1451
        );
        PlanetPoints planetPoints = new PlanetPoints(
                planet
        );

        underTest.save(planetPoints);
        //when
        PlanetPoints gotPlanetPoints = underTest.findPlanetPointsByPlanetId(planet.getId());
        //then
        assertThat(gotPlanetPoints).isEqualTo(gotPlanetPoints);
    }
}*/
