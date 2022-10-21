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
    //ABANDONED  building class abstract or interface
    //ABANDONED  every building separate class with own variables
    //DONE TODO building in time
    //DONE TODO returning building and ship Types values

    /** planet generation update */
    //TODO radial positioning

    /** FUTURE battle update*/
    //TODO changing planet status after getting it
    //TODO battle time in cycles
    //TODO making army in barrack as ships. Max army level is barrack level
    //TODO selecting army and ships level while creating them
    //TODO saving army as hashMap on planet
    //TODO loading army on ship while attack

    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    //TODO Ships only travels between users planets and only there can load points except battle situation
    //NOT SEEN TODO while updating storage level, all points disappear
    //DONE TODO not returning every value when requesting getBuilding ec.
    //DONE TODO changing ship status while upgrading to IN_BUILD
    public static void main(String[] args) throws SQLException, IOException, ParseException {

        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
