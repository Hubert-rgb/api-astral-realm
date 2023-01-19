package HubertRoszyk.company;

import HubertRoszyk.company.configuration.GameProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//management
@SpringBootApplication
@EnableJpaRepositories
@EntityScan()
public class Main {
    //DNT - Done not tested
    //D - DONE

    /** science cards */
    //TODO create concept for all cards
    //TODO create those cards execution

    /** uninhabited planets */
    //TODO longer protection period after colonising this planet
    //TODO change type after colonising


    /** general changes */
    //TODO radial positioning

    /** code refreshment*/
    //TODO JACSON - every json to entity
    //TODO DAO pattern
    //TODO tests

    /** code debuging*/

    public static void main(String[] args) {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
