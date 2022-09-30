package HubertRoszyk.company.controller;

import HubertRoszyk.company.entiti_class.Ship;
import HubertRoszyk.company.entiti_class.TravelRoute;
import HubertRoszyk.company.service.ShipService;
import HubertRoszyk.company.service.TravelRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShipController {
    @Autowired
    ShipService shipService;

    @Autowired
    TravelRouteService travelRouteService;

    @PostMapping("ship-controller/ship")
    public Ship buildShip(){
        //Industry points
    }
    @PutMapping("")
    public Ship upgradeShip(){
        //Industry points and shipYard level
    }
    @PutMapping
    public Ship loadShip(){

    }
    @PutMapping
    public Ship unloadShip(){

    }
    @PutMapping
    public TravelRoute sendShip(){

    }
}
