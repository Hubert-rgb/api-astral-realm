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
    /** get requests */
    //TODO time to build
    //Jakieś pobieranie timer actions i przedstawianie ich w frontendzie???

    /** general changes */
    //TODO radial positioning
    //TODO galaxy settings (time in cycle)

    /** attack update*/
    //TODO pillage
    //TODO planet protection period after battle (maby it can be checked in fronted?)

    /** code refreshment*/
    //TODO JACSON - every json to entity
    //TODO DAO pattern
    //TODO tests

    /** code debuging*/
    //DONE TODO before upgrading army is added to its level and then subtracted so its stays unchanged or even higher
    //TO WATCH TODO switched place where level is upgraded while purchase
    public static void main(String[] args) {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
