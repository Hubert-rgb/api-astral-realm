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
    //TODO comments
    //TODO code refreshment
    // building management
    // battle management strategy

    /** stroages update */
    //DONE TODO industry points stored on planet
    //DONE TODO redo industryPoints and FactoryPoints to galaxyPoints and PlanetPoints
    //TODO storage building
    //TODO default small storage on planet
    //TODO GET Buildings types

    /** code review update*/
    //TODO JACSON

    /** code debuging*/
    //TODO distance between planet issue
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
