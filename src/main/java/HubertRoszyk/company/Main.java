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
    //TODO not storage capacity and points stored, not generating points while is full
    // TODO Building redo:
    //  building class abstract or interface
    //  every building separate class with own variables

    /** planet generation update */
    //TO DISCUSS TODO radial positioning
    //SEMI DONE (not after battle) TODO planet status
    //DONE TODO default buildings industry, storage and shipyard
    //DONE TODO planet size = places to build => random size * 2

    /** timer update */
    //SEMI DONE (battle not done) TODO global timer (for galaxy) repeating every 15min
    //DONE TODO get left time on timer request

    /** FUTURE battle update*/
    //TODO changing planet status after getting it
    //TODO battle time in cycles

    /** code review update*/
    //TODO JACSON - every json to entity

    /** code debuging*/
    public static void main(String[] args) throws SQLException, IOException, ParseException {
        GameProperties configOperator = new GameProperties();

        SpringApplication.run(Main.class, args);
    }
}
