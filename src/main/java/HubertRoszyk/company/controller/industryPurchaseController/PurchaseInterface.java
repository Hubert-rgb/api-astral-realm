package HubertRoszyk.company.controller.industryPurchaseController;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.entiti_class.ship.Ship;
import HubertRoszyk.company.enumStatus.IndustryPurchaseStatus;
import HubertRoszyk.company.service.*;

public interface PurchaseInterface<T> { //TODO redo also for buying science cards
    PlanetPointsService getPlanetPointsService();
    PlanetService getPlanetService();


    default IndustryPurchaseStatus executePurchase(int planetId, T object, int setLevel, int...amount) {
        PlanetPointsService planetPointsService = getPlanetPointsService();
        PlanetService planetService = getPlanetService();

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Planet planet = planetService.getPlanetById(planetId);

        double price = getPrice(object, amount);
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
            purchaseOk(object, planetPoints, setLevel, planetPointsService, gotIndustryPoints, price, amount);
            return IndustryPurchaseStatus.OK;
        } else if (!isNotOnMaximumLevel) {
            return IndustryPurchaseStatus.MAX_LEVEL;
        } else if (!isEnoughSpaceOnPlanet){
            return IndustryPurchaseStatus.NOT_ENOUGH_SPACE;
        } else if(!isShipYardEnoughLevel) {
            return IndustryPurchaseStatus.NOT_ENOUGH_SHIP_YARD_LEVEL;
        } else {
            return IndustryPurchaseStatus.NOT_ENOUGH_POINTS;
        }
    }
    default void purchaseOk(T object, PlanetPoints planetPoints, int setLevel, PlanetPointsService planetPointsService, double gotIndustryPoints, double price, int...amount) {
        double setIndustryPoints = gotIndustryPoints - price;
        planetPoints.setIndustryPoints(setIndustryPoints);

        saveObject(object, planetPoints.getPlanet().getId());

        executeTimerAction(object, planetPoints.getPlanet().getId(), setLevel);

        upgradeLevel(object, setLevel, planetPoints.getPlanet().getId(), amount);
        System.out.println(setLevel);

        planetPointsService.savePoints(planetPoints);
    }

    boolean getIsEnoughSpace(T object, Planet planet);

    void saveObject(T object, int planetId);

    void upgradeLevel(T object, int setLevel, int planetId, int...amount);
    boolean getIsNotOnMaximumLevel(T object, int planetId);
    double getPrice(T object, int...amount);

    void executeTimerAction(T object, int planetId, int setLevel);

}
