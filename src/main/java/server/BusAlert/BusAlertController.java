package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.BusAlert.models.Route;

@RestController
public class BusAlertController {

    public static final String API_VERSION = "1.0";

    public static final String RIDER_PATH = "/api/" + API_VERSION +"/rider";
    public static final String STOP_PATH = "/api/" + API_VERSION +"/stop";
    public static final String ROUTE_PATH = "/api/" + API_VERSION +"/route";

    public static final String GPS_PATH = "/api/" + API_VERSION +"/gps";

    @Autowired
    private BusAlertService busAlertService;


    /**
     * The method uses takes an HTTP POST request containing
     *
     * @param routeId - this is the
     * @param latitude
     * @param longitude
     */
    @PostMapping(GPS_PATH)
    public void receiveGPS(
            @RequestParam("routeId") String routeId,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude
    ){
        // pass the info to the BusAlertService to handle the business logic
    }

    @PostMapping(ROUTE_PATH)
    public Route addRoute(
            @RequestParam("code") String code
    ){
        return busAlertService.addRoute(code);
    }

}
