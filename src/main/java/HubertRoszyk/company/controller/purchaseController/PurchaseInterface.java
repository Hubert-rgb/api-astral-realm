package HubertRoszyk.company.controller.purchaseController;

import HubertRoszyk.company.entiti_class.*;
import HubertRoszyk.company.enumStatus.PurchaseStatus;
import HubertRoszyk.company.enumTypes.TimerActionType;
import HubertRoszyk.company.service.*;

import java.util.HashMap;
import java.util.Map;

public interface PurchaseInterface<T> {
    PlanetPointsService getPlanetPointsService();
    PlanetService getPlanetService();


    default PurchaseStatus executePurchase(int planetId, T object) {
        PlanetPointsService planetPointsService = getPlanetPointsService();
        PlanetService planetService = getPlanetService();

        PlanetPoints planetPoints = planetPointsService.getPointsByPlanetId(planetId);
        Planet planet = planetService.getPlanetById(planetId);

        int setLevel = upgradeLevel(object);
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

        isNotOnMaximumLevel = getIsNotOnMaximumLevel(object);
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

        saveObject(object);

        executeTimerAction(object, planetPoints.getPlanet().getId(), setLevel);
        //TODO method not if
        planetPointsService.savePoints(planetPoints);
    }

    boolean getIsEnoughSpace(T object, Planet planet);

    void saveObject(T object);

    int upgradeLevel(T object);
    boolean getIsNotOnMaximumLevel(T object);
    double getPrice(T object);

    void executeTimerAction(T object, int planetId, int setLevel);

}
