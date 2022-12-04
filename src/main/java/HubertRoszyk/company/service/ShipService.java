package HubertRoszyk.company.service;

import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.ShipStatus;
import HubertRoszyk.company.enumTypes.ShipType;
import HubertRoszyk.company.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipService {
    @Autowired
    ShipRepository repository;

    public Ship saveShip(Ship ship) {
        return repository.save(ship);
    }

    //nie wiem dlaczego to nie chce działać z childami
    public List<Ship> saveShipList(List<Ship> ships) {
        return repository.saveAll(ships);
    }
    public List<Ship> getShipsList() {
        return repository.findAll();
    }

    public List<Ship> getShipsListByIdList(List<Integer> idList){
        return repository.findAllById(idList);
    }
    public Ship getShipById(int id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteShips() {
        repository.deleteAll();
        System.out.println("All Ships deleted");
    }

    public void deleteShipById(int id) {
        repository.deleteById(id);
    }

    public List<Ship> getShipsByPlanetId(int planetId){
        return repository.findByShipStatusAndTravelRoute_ArrivalPlanet_Id(ShipStatus.DOCKED, planetId);
    }
    public List<Ship> getShipsByPlanetIdAndType(int planetId, ShipType shipType){
        return repository.findByTravelRoute_ArrivalPlanet_IdAndShipStatusAndShipType(planetId,ShipStatus.DOCKED, shipType);
    }


    public void removeShipsList(List<Ship> shipList){
        repository.deleteAllInBatch(shipList);
    }
    /*public Ship getShipByIdWithRoutes(int id){
        return repository.findShipById(id);
    }*/
    /*public List<Ship> getShipsByPlanetId(int planetId) {
        return repository.findByTravelRoute_DeparturePlanet_Id(planetId);
    }*/
}
