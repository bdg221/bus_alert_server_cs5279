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
import server.BusAlert.Twilio.TwilioRequest;
import server.BusAlert.Twilio.TwilioService;

import java.util.List;

/**
 * This is the main controller for the REST API of busAlert. This utilizes
 * the Java Spring framework to get different REST API calls and then call
 * the appropriate service to handle the business logic and perform the
 * required tasks.
 */
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
    public static final String TEST_TWILIO_SERVICE_PATH = "/api/" + API_VERSION +"/twilio";

    @Autowired
    private BusAlertService busAlertService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @Autowired
    private RiderService riderService;

    @GetMapping("/")
    public String defaultGet(){
        return "Hello World";
    }


    /**
     * The method uses takes an HTTP POST request containing the GPS coordinates.
     * This is called by the Driver mobile app.
     *
     * @param locationRequest is the object with for the GPS coords
     */
    @PostMapping(GPS_PATH)
    public void receiveGPS(
            @RequestBody LocationRequest locationRequest
    ){
        // pass the info to the BusAlertService to handle the business logic
    }

    /**
     * This is a GET request to return all routes in the database. The
     * business logic is handled in the RouteService class.
     * @return a List of Route object in the HTTP Response body
     */
    @GetMapping(ROUTE_PATH)
    public List<Route> getRoutes(){
        return routeService.getRoutes();
    }

    /**
     * This is a GET request to return a specific route based on an Id. The
     * business logic is handled in the RouteService class.
     * @param Id of the route to return in the URL path
     * @return the Route object or null if not found
     */
    @GetMapping(SPECIFIC_ROUTE_PATH)
    public Route getRoute(
            @PathVariable ("Id") Long Id
    ){
        return routeService.getRoute(Id);
    }

    /**
     * This is a POST request to add a new route. The business logic is handled in
     * the RouteService class.
     * @param shortCode for new route object
     * @return the newly created Route object
     */
    @PostMapping(ROUTE_PATH)
    public Route addRoute(
            @RequestParam("shortCode") String shortCode
    ){
        return routeService.addRoute(shortCode);
    }

    /**
     * This is a POST request to modify an existing route. The business logic is
     * handled in the RouteService class.
     * @param Id of the existing route in the URL path
     * @param shortCode value to modify in teh existing route
     * @return the newly modified route object or null if route does not exist
     */
    @PostMapping(SPECIFIC_ROUTE_PATH)
    public Route modifyRoute(
            @PathVariable("Id") Long Id,
            @RequestParam("shortCode") String shortCode
    ){
        return routeService.modifyRoute(Id, shortCode);
    }

    /**
     * This is a DELETE request to delete an existing route. The business logic is
     * handled in the RouteService class.
     * @param Id of the route to delete in the URL path
     */
    @DeleteMapping(SPECIFIC_ROUTE_PATH)
    public void deleteRoute(
            @PathVariable("Id") Long Id
    ){
        routeService.deleteRoute(Id);
    }

    /**
     * This is a GET request to return all stops in the database. The bussiness
     * logic is handled in the StopService class.
     * @return a list of stop objects
     */
    @GetMapping(STOP_PATH)
    public List<Stop> getStops(){
        return stopService.getStops();
    }

    /**
     * This is a GET request to return a specific stop based off the provided Id.
     * The business logic is handled in the StopService class.
     * @param Id of stop to return from the URL path
     * @return stop object if it is in the database
     */
    @GetMapping(SPECIFIC_STOP_PATH)
    public Stop getStop(
            @PathVariable("Id") Long Id
    ){
        return stopService.getStop(Id);
    }

    /**
     * This is a POST request to add a new stop to the database. The business logic
     * is handled in the StopService class.
     * @param shortCode is a readable string value
     * @param longitude part of the GPS coordinates
     * @param latitude part of the GPS coordinates
     * @param routeId of associated route
     * @return newly created stop
     */
    @PostMapping(STOP_PATH)
    public Stop addStop(
            @RequestParam("shortCode") String shortCode,
            @RequestParam("longitude") Float longitude,
            @RequestParam("latitude") Float latitude,
            @RequestParam("route") Long routeId
    ){
        return stopService.addStop(shortCode, longitude, latitude, routeId);
    }

    /**
     * This is a POST request to modify an existing stop. The business logic is
     * handled in the StopService class.
     * @param Id of the stop to modify from the URL path
     * @param shortCode updated value (null if not to be modified)
     * @param longitude updated value (null if not to be modified)
     * @param latitude updated value (null if not to be modified)
     * @param routeId updated value (null if not to be modified)
     * @return modified stop object
     */
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

    /**
     * This is a DELETE request to delete a stop from the database. The business
     * logic is handled in the StopService class.
     * @param Id of the stop to be deleted from the URL path
     */
    @DeleteMapping(SPECIFIC_STOP_PATH)
    public void deleteStop(
            @PathVariable("Id") Long Id
    ){
        stopService.deleteStop(Id);
    }

    /**
     * This is a GET request to get all riders in the database. The business logic
     * is handled in the RiderService class.
     * @return list of rider objects
     */
    @GetMapping(RIDER_PATH)
    public List<Rider> getRiders(){
        return riderService.getRiders();
    }

    /**
     * This is a GET request to return a single rider based on the provided Id. The
     * business logic is handled in the RiderService class.
     * @param Id of rider to return from the URL path
     * @return rider object or null if not found
     */
    @GetMapping(SPECIFIC_RIDER_PATH)
    public Rider getRider(
            @PathVariable("Id") Long Id
    ){
        return riderService.getRider(Id);
    }

    /**
     * This is a POST request to create a new rider. The business logic is
     * handled in the RiderService class
     * @param phone is the phone number of the rider
     * @param stopId is Id of a stop associated with the rider
     * @return the newly created rider
     */
    @PostMapping(RIDER_PATH)
    public Rider addRider(
            @RequestParam("phone") String phone,
            @RequestParam("stop") Long stopId
    ){
        return riderService.addRider(phone, stopId);
    }

    /**
     * This is a POST mapping for modifying an existing rider. The business
     * logic is handled in the RiderService class.
     * @param Id of the rider to modify from the URL path
     * @param phone updated value (null if not to be modified)
     * @param stopId updated value (null if not to be modified)
     * @return modified rider
     */
    @PostMapping(SPECIFIC_RIDER_PATH)
    public Rider modifyRider(
            @PathVariable("Id") Long Id,
            @RequestParam(required = false, name="phone") String phone,
            @RequestParam(required = false, name="stop") Long stopId
    ){
        return riderService.modifyRider(Id, phone, stopId);
    }

    /**
     * This is a DELETE request to delete an existing rider. The business
     * logic is handled in the RiderService class.
     * @param Id of the rider to delete from the URL path
     */
    @DeleteMapping(SPECIFIC_RIDER_PATH)
    public void deleteRider(
            @PathVariable("Id") Long Id
    ){
        riderService.deleteRider(Id);
    }

    /**
     * Endpoint is used for testing purposes only
     * @param twilioRequest phone number and message as a TwilioRequest
     */
    @PostMapping(TEST_TWILIO_SERVICE_PATH)
    public boolean testTwilio(
            @RequestBody TwilioRequest twilioRequest
    ) {
        return TwilioService.SendMessage(twilioRequest);
    }
}
