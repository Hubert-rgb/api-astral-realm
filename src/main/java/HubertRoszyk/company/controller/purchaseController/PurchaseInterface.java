package HubertRoszyk.company.controller.purchaseController;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.service.*;

public interface PurchaseInterface<T> { //TODO redo also for buying science cards
    PlanetPointsService getPlanetPointsService();
    PlanetService getPlanetService();


    default PurchaseStatus executePurchase(int planetId, T object, int setLevel) {
        PlanetPointsService planetPointsService = getPlanetPointsService();
        PlanetService planetService = getPlanetService();

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Planet planet = planetService.getPlanetById(planetId);

        //TODO po czasie
        upgradeLevel(object, setLevel, planetId);
        System.out.println(setLevel);

        double price = getPrice(object);
        double gotIndustryPoints = planetPoints.getIndustryPoints();

        System.out.println("cena: " + price);
        System.out.println("industry: " + gotIndustryPoints);

        boolean isEnoughPoints;
        boolean isNotOnMaximumLevel;
        boolean isEnoughSpaceOnPlanet;

        boolean isShipYardEnoughLevel;
        if(object instanceof Ship) {
            int shipYardLevel = planetPoints.getShipYardLevel();
            isShipYardEnoughLevel = shipYardLevel >= setLevel;
        } else {
            isShipYardEnoughLevel = true;
        }

        isEnoughPoints = gotIndustryPoints >= price;

        isNotOnMaximumLevel = getIsNotOnMaximumLevel(object, planetId);
        isEnoughSpaceOnPlanet = getIsEnoughSpace(object, planet);

        //TODO maybe strategy not if
        if (isEnoughPoints && isNotOnMaximumLevel  && isEnoughSpaceOnPlanet && isShipYardEnoughLevel) {  //strategy
            purchaseOk(object, planetPoints, setLevel, planetPointsService, gotIndustryPoints, price);
            return PurchaseStatus.OK;
        } else if (!isNotOnMaximumLevel) {
            return PurchaseStatus.MAX_LEVEL;
        } else if (!isEnoughSpaceOnPlanet){
            return PurchaseStatus.NOT_ENOUGH_SPACE;
        } else if(!isShipYardEnoughLevel) {
            return PurchaseStatus.NOT_ENOUGH_SHIP_YARD_LEVEL;
        } else {
            return PurchaseStatus.NOT_ENOUGH_POINTS;
        }
    }
    default void purchaseOk(T object, PlanetPoints planetPoints, int setLevel, PlanetPointsService planetPointsService, double gotIndustryPoints, double price) {
        double setIndustryPoints = gotIndustryPoints - price;
        planetPoints.setIndustryPoints(setIndustryPoints);

        saveObject(object, planetPoints.getPlanet().getId());

        executeTimerAction(object, planetPoints.getPlanet().getId(), setLevel);
        //TODO method not if
        planetPointsService.savePoints(planetPoints);
    }

    boolean getIsEnoughSpace(T object, Planet planet);

    void saveObject(T object, int planetId);

    void upgradeLevel(T object, int setLevel, int planetId);
    boolean getIsNotOnMaximumLevel(T object, int planetId);
    double getPrice(T object);

    void executeTimerAction(T object, int planetId, int setLevel);

}
