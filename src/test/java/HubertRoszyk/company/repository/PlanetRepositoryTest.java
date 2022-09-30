package HubertRoszyk.company.repository;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumTypes.PlanetType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class PlanetRepositoryTest {
    @Autowired
    private PlanetRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void itShouldGetPlanetsByGalaxyId() {
        //given
        Planet planet = new Planet(
                PlanetType.SMALL,
                2,
                3,
                4,
                123,
                1451
        );
        Galaxy galaxy = new Galaxy();
        planet.setGalaxy(galaxy);

        underTest.save(planet);
        //when
        Set<Planet> gotPlanets = underTest.findByGalaxyId(galaxy.getId());
        //then
        assertThat(gotPlanets).contains(planet);
    }
    @Test
    void itShouldFindAllUserPlanetsInGalaxy() {
        //given
        Planet planet = new Planet(
                PlanetType.SMALL,
                2,
                3,
                4,
                123,
                1451
        );
        User user = new User();
        Galaxy galaxy = new Galaxy();

        planet.setGalaxy(galaxy);
        planet.setUser(user);

        entityManager.persist(user);
        entityManager.persist(galaxy);
        entityManager.persist(planet);
        entityManager.flush();

        //underTest.save(planet);

        //when
        Set<Planet> gotPlanets = underTest.findByUserIdAndGalaxyId(user.getId(), galaxy.getId());
        //then
        assertThat(gotPlanets).contains(planet);
    }
    @Test
    public void shouldFindAllPlanetsByUserId() {
        //given
        Planet planet = new Planet(
                PlanetType.SMALL,
                2,
                3,
                4,
                123,
                1451
        );
        User user = new User();
        planet.asignUser(user);

        underTest.save(planet);
        //when
        Set<Planet> gotPlanets = underTest.findByUserId(user.getId());
        //then
        assertThat(gotPlanets).contains(planet);
    }
}