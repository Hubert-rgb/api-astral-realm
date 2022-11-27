package HubertRoszyk.company;

import HubertRoszyk.company.configuration.GameProperties;
import HubertRoszyk.company.configuration.WebConfigure;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

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

    /** planet generation update */
    //TODO radial positioning

    /** attack update*/
    //TODO pillage
    //TODO colonisation
    //TODO upgrading army divisions

    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    //ON FRONTEND TODO Ships only travels between users planets and only there can load points except attack situation
    //TODO army subtracting and adding
    //TODO user assignment
    public static void main(String[] args) throws SQLException, IOException, ParseException {

        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
