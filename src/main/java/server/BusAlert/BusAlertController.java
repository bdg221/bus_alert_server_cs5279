package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.BusAlert.models.Route;

@RestController
public class BusAlertController {

    public static final String API_VERSION = "1.0";

    public static final String RIDER_PATH = "/api/" + API_VERSION +"/rider";
    public static final String STOP_PATH = "/api/" + API_VERSION +"/stop";
    public static final String ROUTE_PATH = "/api/" + API_VERSION +"/route";
    public static final String MODIFY_ROUTE_PATH = "/api/" + API_VERSION +"/route/{Id}";

    public static final String GPS_PATH = "/api/" + API_VERSION +"/gps";

    @Autowired
    private BusAlertService busAlertService;


    /**
     * The method uses takes an HTTP POST request containing
     *
     * @param routeId - this is the
     * @param latitude - latitude portion of GPS
     * @param longitude - longitude portion of GPS
     */
    @PostMapping(GPS_PATH)
    public void receiveGPS(
            @RequestParam("routeId") String routeId,
            @RequestParam("latitude") Float latitude,
            @RequestParam("longitude") Float longitude
    ){
        // pass the info to the BusAlertService to handle the business logic
    }

    @GetMapping(ROUTE_PATH)
    public Route getRoute(
            @RequestParam("Id") Long Id
    ){
        return busAlertService.getRoute(Id);
    }

    @PostMapping(ROUTE_PATH)
    public Route addRoute(
            @RequestParam("shortCode") String shortCode
    ){
        System.out.println("INSIDE ADD ROUTE");
        return busAlertService.addRoute(shortCode);
    }

    @PostMapping(MODIFY_ROUTE_PATH)
    public Route modifyRoute(
            @RequestParam("Id") Long Id,
            @RequestParam("shortCode") String shortCode
    ){
        return busAlertService.modifyRoute(Id, shortCode);
    }

    @DeleteMapping(ROUTE_PATH)
    public Route deleteRoute(
            @RequestParam("Id") Long Id
    ){
        return busAlertService.deleteRoute(Id);
    }

}
