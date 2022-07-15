package server.BusAlert.Stop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Rider.Rider;
import server.BusAlert.Rider.RiderService;
import server.BusAlert.Route.RouteService;
import server.BusAlert.Route.Route;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The StopService class contains the business logic to handle Stops.
 * The service is called from the BusAlertController.
 */
@Service
public class StopService {

    /**
     * The StopRepository is used to access the Stop table in the database
     */
    @Autowired
    private StopRepository stopRepository;

    /**
     * Since the Stop is associated with a Route, the RouteService is used
     */
    @Autowired
    private RouteService routeService;


    /**
     * The getStops method returns all Stops in the database
     * @return a List of Stop objects
     */
    public List<Stop> getStops(){
        return stopRepository.findAll();
    }

    /**
     * The getStop method calls a helper method to return either null or the Stop
     * associated with a provided Id.
     * @param Id of the Stop
     * @return either null if not found or a Stop object
     */
    public Stop getStop(Long Id){
        // call the helper method which handles error case of the Id not being found
        return getStopFromRepository(Id);
    }

    /**
     * The addStop method saves a new Stop to the database.
     * @param stop object to be created and saved
     * @return the newly created Stop object
     */
    public Stop addStop(
            Stop stop
    ){
        // save full Route object to stop
        stop.setRoute(routeService.getRoute(stop.getRouteIdOnly()));

        // save the Stop to the database which auto-generates the Id value
        stop = stopRepository.save(stop);

        // add the Stop to the Route
        stop.getRoute().addStop(stop);

        // return the newly created Stop
        return stop;
    }

    /**
     * The modifyStop method uses an Id to find an existing Stop and then modifies
     * it based on the provided values.
     * @param Id - required - Long value of the Stop's Id
     * @param shortcode - optional - new shortCode value
     * @param longitude - optional - new longitude value
     * @param latitude - optional - new latitude value
     * @param routeId - optional - new routeId value
     * @return the modified Stop object
     */
    public Stop modifyStop(
            Long Id,
            String shortcode,
            Float longitude,
            Float latitude,
            Long routeId
    ){
        // call the helper method to get the existing Stop object
        Stop stop = getStopFromRepository(Id);

        // if the Stop cannot be found from the Id return null
        if (stop == null) return null;

        // if a new shortcode is provided update the Stop
        if (shortcode != null) stop.setShortCode(shortcode);

        // if a new longitude is provided update the Stop
        if (longitude != null) stop.setLongitude(longitude);

        // if a new latitude is provided update the Stop
        if (latitude != null) stop.setLatitude(latitude);

        // if a routeId is provided do more steps
        if (routeId != null){
            // verify the Stop has an existing route
            if(stop.getRoute() != null ){
                // delete the stop from the original Route
                stop.getRoute().deleteStop(stop);
            }
            // get the new Route from the routeId
            Route new_route = routeService.getRoute(routeId);

            // make sure the new routeId is valid
            if(new_route != null) {
                // add the Stop to the new Route
                new_route.addStop(stop);
                // set the Route to Stop
                stop.setRoute(new_route);
            }
        }
        // save the modified Stop and return it
        return stopRepository.save(stop);

    }

    /**
     * The deleteStop method deletes the Stop and also makes sure to remove
     * the Stop from an associated Route.
     * @param Id value of the Stop
     */
    public void deleteStop(Long Id){
        // use the helper method to get the Stop to be deleted from the Id
        Stop stop = getStopFromRepository(Id);
        // make sure there is a Stop to delete
        if(stop != null){
            // remove the Stop from the associated Route
            if(stop.getRoute() != null) stop.getRoute().deleteStop(stop);

            // delete the Stop from the database
            stopRepository.delete(stop);
        }
    }

    /**
     * The getStopFromRepository method is a helper method to handle the error
     * case of there not being a Stop with the passed in Id.
     * @param Id value for the database query
     * @return either null if the Stop is not found or the Stop object
     */
    private Stop getStopFromRepository(Long Id){
        // Call findById which returns an Optional
        Optional<Stop> checkStop = stopRepository.findById(Id);
        // check if the Optional is empty and if so return null
        // otherwise return the Stop object in the Optional
        return (checkStop.orElse(null));
    }




    public void failedRiderNotifications(Rider rider){
        // do logging or other notifications that the communication
        // failed at some points and we assume the Rider was NOT
        // notified

        System.out.println("FAILURE - Message to rider ID "+rider.getId()+ " failed.");
    }



}