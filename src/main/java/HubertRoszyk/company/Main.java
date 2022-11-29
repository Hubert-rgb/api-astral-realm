package HubertRoszyk.company;

import HubertRoszyk.company.configuration.GameProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//management
@SpringBootApplication
@EnableJpaRepositories
@EntityScan()

public class Main {
    //TODO tests

    /** code refreshment */
    //TODO DAO pattern

    /** get requests */
    //TODO time to build
    //Jakieś pobieranie timer actions i przedstawianie ich w frontendzie???

    /** general changes */
    //TODO radial positioning
    //TODO galaxy settings (time in cycle)

    /** attack update*/
    //TODO pillage
    //DONE TODO colonisation
    //TODO upgrading army divisions

    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    //ON FRONTEND TODO Ships only travels between users planets and only there can load points except attack situation
    //DONE TODO army subtracting and adding
    //POSTMAN ISSUE TODO user assignment, user is deAssigning
    //TODO wrong army value
    public static void main(String[] args) {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
