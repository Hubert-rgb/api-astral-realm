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

    /** code refreshment */
    //TODO building management
    //TODO battle management strategy
    //TODO static classes with multipurpose methods
    //TODO DAO pattern

    /** get requests */
    //TODO time to build
    //Jakieś pobieranie timer actions i przedstawianie ich w frontendzie???

    /** planet generation update */
    //TODO radial positioning

    /** battle update*/
    //DONE TODO changing planet status after getting it
    //DONE TODO battle time in cycles
    //DONE TODO making army in barrack as ships. Max army level is barrack level
    //DONE TODO selecting army and ships level while creating them
    //DONE TODO saving army as hashMap on planet
    //DONE TODO loading army on ship while attack
    //DONE TODO subtracting army after battle
    //TODO pillage
    //TODO colonisation
    //TODO loading army after won attack


    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    //ON FRONTEND TODO Ships only travels between users planets and only there can load points except battle situation
    //DONE TODO changing user num in galaxy
    public static void main(String[] args) throws SQLException, IOException, ParseException {

        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
