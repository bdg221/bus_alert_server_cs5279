package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.BusAlert.Location.LocationRequest;
import server.BusAlert.Rider.Rider;
import server.BusAlert.Rider.RiderService;
import server.BusAlert.Route.RouteService;
import server.BusAlert.Stop.Stop;
import server.BusAlert.Stop.StopService;
import server.BusAlert.Route.Route;

import java.util.List;

@RestController
public class BusAlertController {

    public static final String API_VERSION = "1.0";

    public static final String RIDER_PATH = "/api/" + API_VERSION +"/rider";
    public static final String SPECIFIC_RIDER_PATH = RIDER_PATH +"/{Id}";

    public static final String STOP_PATH = "/api/" + API_VERSION +"/stop";
    public static final String SPECIFIC_STOP_PATH = STOP_PATH +"/{Id}";

    public static final String ROUTE_PATH = "/api/" + API_VERSION +"/route";
    public static final String SPECIFIC_ROUTE_PATH = ROUTE_PATH +"/{Id}";

    public static final String GPS_PATH = "/api/" + API_VERSION +"/gps";

    @Autowired
    private BusAlertService busAlertService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @Autowired
    private RiderService riderService;

    /**
     * The method uses takes an HTTP POST request containing
     *
     * @param routeId - this is the
     * @param latitude - latitude portion of GPS
     * @param longitude - longitude portion of GPS
     */
    @PostMapping(GPS_PATH)
    public void receiveGPS(
            @RequestBody LocationRequest locationRequest
    ){
        // pass the info to the BusAlertService to handle the business logic
    }

    @GetMapping(ROUTE_PATH)
    public List<Route> getRoutes(){
        return routeService.getRoutes();
    }

    @GetMapping(SPECIFIC_ROUTE_PATH)
    public Route getRoute(
            @PathVariable ("Id") Long Id
    ){
        return routeService.getRoute(Id);
    }

    @PostMapping(ROUTE_PATH)
    public Route addRoute(
            @RequestParam("shortCode") String shortCode
    ){
        return routeService.addRoute(shortCode);
    }

    @PostMapping(SPECIFIC_ROUTE_PATH)
    public Route modifyRoute(
            @PathVariable("Id") Long Id,
            @RequestParam("shortCode") String shortCode
    ){
        return routeService.modifyRoute(Id, shortCode);
    }

    @DeleteMapping(SPECIFIC_ROUTE_PATH)
    public void deleteRoute(
            @PathVariable("Id") Long Id
    ){
        routeService.deleteRoute(Id);
    }

    @GetMapping(STOP_PATH)
    public List<Stop> getStops(){
        return stopService.getStops();
    }

    @GetMapping(SPECIFIC_STOP_PATH)
    public Stop getStop(
            @PathVariable("Id") Long Id
    ){
        return stopService.getStop(Id);
    }

    @PostMapping(STOP_PATH)
    public Stop addStop(
            @RequestParam("shortCode") String shortCode,
            @RequestParam("longitude") Float longitude,
            @RequestParam("latitude") Float latitude,
            @RequestParam("route") Long routeId
    ){
        return stopService.addStop(shortCode, longitude, latitude, routeId);
    }

    @PostMapping(SPECIFIC_STOP_PATH)
    public Stop modifyStop(
            @PathVariable("Id") Long Id,
            @RequestParam(required = false, name="shortCode") String shortCode,
            @RequestParam(required = false, name="longitude") Float longitude,
            @RequestParam(required = false, name="latitude") Float latitude,
            @RequestParam(required = false, name="route") Long routeId
    ){
        return stopService.modifyStop(Id, shortCode, longitude, latitude, routeId);
    }

    @DeleteMapping(SPECIFIC_STOP_PATH)
    public void deleteStop(
            @PathVariable("Id") Long Id
    ){
        stopService.deleteStop(Id);
    }

    @GetMapping(RIDER_PATH)
    public List<Rider> getRiders(){
        return riderService.getRiders();
    }

    @GetMapping(SPECIFIC_RIDER_PATH)
    public Rider getRider(
            @PathVariable("Id") Long Id
    ){
        return riderService.getRider(Id);
    }

    @PostMapping(RIDER_PATH)
    public Rider addRider(
            @RequestParam("phone") String phone,
            @RequestParam("stop") Long stopId
    ){
        return riderService.addRider(phone, stopId);
    }

    @PostMapping(SPECIFIC_RIDER_PATH)
    public Rider modifyRider(
            @PathVariable("Id") Long Id,
            @RequestParam(required = false, name="phone") String phone,
            @RequestParam(required = false, name="stop") Long stopId
    ){
        return riderService.modifyRider(Id, phone, stopId);
    }

    @DeleteMapping(SPECIFIC_RIDER_PATH)
    public void deleteRider(
            @PathVariable("Id") Long Id
    ){
        riderService.deleteRider(Id);
    }
}
