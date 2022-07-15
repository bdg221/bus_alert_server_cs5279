package server.BusAlert.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The RouteService class contains the business logic to handle Routes.
 * The service is called from the BusAlertController.
 */
@Service
public class RouteService {

    /**
     * The RouteRepository is used to access the Route table in the database.
     */
    @Autowired
    private RouteRepository routeRepository;


    /**
     * The getRoutes returns all Routes in the database table.
     * @return a List of Route objects in the table
     */
    public List<Route> getRoutes(){
        return routeRepository.findAll();
    }

    /**
     * The getRoute returns a specific Route object based on the Id
     * @param Id - Long with the value of the Id for the Route
     * @return the Route associated with the Id or null if not found
     */
    public Route getRoute(Long Id){
        // call helper method getRouteFromRepository for basic error handling
        return getRouteFromRepository(Id);
    }

    public Route getRouteByShortCode(String shortCode){
        return getRouteFromShort_Code(shortCode);
    }


    /**
     * The addRoute method creates a Route and saves it to the Route table
     * @param route - Full route object with just shortCode
     * @return the newly created Route object
     */
    public Route addRoute(Route route){

        // save the Route object to the database which also
        // auto-generates the Id field and return it
        return routeRepository.save(new Route(route.getShortCode()));
    }

    /**
     * The modifyRoute method takes the Id allows for modifying the shortCode parameter.
     * @param Id - unique Id of the Route to be modified
     * @param shortCode - new shortCode value to assign to the Route
     * @return the newly modified Route object
     */
    public Route modifyRoute(Long Id, String shortCode){
        // call helper method to get the Route from the database using the Id
        Route route = getRouteFromRepository(Id);
        // if nothing is found with the Id return null
        if(route == null){
            return null;
        }
        // update the shortCode value for the Route
        route.setShortCode(shortCode);

        // save the modified Route object and return it
        return routeRepository.save(route);
    }

    /**
     * Delete the Route from the table
     * @param Id of the Route to be deleted
     */
    public void deleteRoute(Long Id){
        routeRepository.deleteById(Id);
    }

    /**
     * The getRouteFromRepository is a helper method to handle the error if the
     * Route Id is not found in the database.
     * @param Id of the Route in the database
     * @return either null if not found or the Route with the associated Id
     */
    private Route getRouteFromRepository(Long Id){
        // call findById to get an Optional Route object
        Optional<Route> checkRoute = routeRepository.findById(Id);
        // if the Optional is empty return null otherwise return the Route
        return (checkRoute.orElse(null));
    }



    /**
     * The getRouteFromShort_Code is a helper method to hanlde the error if the
     * database does not have a Route with the shortCode. If one or more Routes
     * are found, then just return the first one.
     * @param shortCode to be looked up in the database
     * @return either null if nothing is found or the Route with the associated shortCode
     */
    private Route getRouteFromShort_Code(String shortCode){
        // call findByShortCode to get an Optional Route object
        List<Route> checkRoute = routeRepository.findByShortCode(shortCode);
        // if the Optional is empty return null otherwise return the first Route object in the List
        return (checkRoute.isEmpty() ?  null : checkRoute.get(0));
    }
}
