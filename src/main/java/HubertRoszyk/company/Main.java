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

    // TODO Building redo:
    //  building class abstract or interface
    //  every building separate class with own variables

    /** ship update */
    //DONE TODO ships are built on shipyard level
    //DONE TODO upgrade ship on planet with higher shipyard level
    //DONE TODO building ships takes industry points as buildings
    //DONE TODO loading ships
    //DONE TODO deloading ships
    //DONE TODO ships are stored in harbour

    /** planet generation update */
    //TODO radial positioning
    //DONE TODO planets doesn't have default buildings, just generating points

    /** FUTURE battle update*/
    //TODO changing planet status after getting it
    //TODO battle time in cycles

    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    //DONE TODO Storage upgrade
    //DONE TODO Getting industry income on planet 2
    //TODO Ships only travels between users planets and only there can load points except battle situation
    public static void main(String[] args) throws SQLException, IOException, ParseException {

        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
