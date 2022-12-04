package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.Galaxy;
import HubertRoszyk.company.entiti_class.PlanetPoints;
import HubertRoszyk.company.entiti_class.Planet;
import HubertRoszyk.company.enumTypes.PlanetType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlanetPointsRepositoryTest {
    @Autowired
    PlanetPointsRepository underTest;

    private static PodamFactoryImpl podamFactory;
    @BeforeAll
    public static void setUp(){
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void findArmyPointsByPlanetId() {
        //given
        /*Planet planet = new Planet(
                PlanetType.SMALL,
                2,
                3,
                4,
                123,
                1451,
                new Galaxy()
        );*///Planet planet = podamFactory.manufacturePojo(Planet.class);
        PlanetPoints planetPoints = podamFactory.manufacturePojo(PlanetPoints.class);

        //planetPoints.setPlanet(planet);

        underTest.save(planetPoints);
        //when
        PlanetPoints gotPlanetPoints = underTest.findPlanetPointsByPlanetId(planetPoints.getPlanet().getId());
        //then
        assertThat(gotPlanetPoints).isEqualTo(gotPlanetPoints);
    }
}
