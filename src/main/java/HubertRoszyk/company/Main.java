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
    /** get requests */
    //SOLVED (it's received with planet points)  TODO get army

    /** general changes */
    //TODO radial positioning
    //DONE TODO default army generation redo
    //D TODO give number (id) to every player joining galaxy

    /** attack update*/
    //On FRONTEND TODO planet protection period after battle (maby it can be checked in fronted?)

    /** code refreshment*/
    //TODO JACSON - every json to entity
    //TODO DAO pattern
    //TODO tests

    /** code debuging*/
    //TO WATCH TODO switched place where level is upgraded while purchase
    //D TODO building level change after building time
    public static void main(String[] args) {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
