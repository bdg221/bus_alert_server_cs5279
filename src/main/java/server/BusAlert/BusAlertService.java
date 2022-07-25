package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Location.LocationRequest;
import server.BusAlert.Rider.RiderService;
import server.BusAlert.Route.Route;
import server.BusAlert.Route.RouteService;
import server.BusAlert.Stop.Stop;
import server.BusAlert.Stop.StopService;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class BusAlertService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RiderService riderService;

    @Autowired
    private StopService stopService;

    public String receiveGPS(LocationRequest locationRequest){
        AtomicReference<String> retStop = new AtomicReference<>("");

        if(locationRequest.isFirstPing()){
            routeService
            // use the routeService to pull the route from the ShortCode
            // which was passed inside the locationRequest
                    .getRouteByShortCode(locationRequest.getRouteId())

                    // From the Route object that matches the Route's ShortCode
                    // get a List of Stops
                    .getStops()

                    // turn this into parallel streams for better processing
                    .parallelStream()

                    // call helper method atStop which says if GPS coordinates passed
                    // in with LocationRequest is with 0.5 km of each stop
                    // only the True response remain in the stream

                    .limit(2)
                    .forEach(stop -> {
                        retStop.set(stop.getShortCode());
                        stop
                                .getRiders()
                                .parallelStream()
                                .map( rider ->
                                        riderService
                                                .notifyRider(rider, "Your bus has started its route."))
                                .filter(Objects::nonNull)
                                .forEach(rider -> stopService.failedRiderNotifications(rider));
                            }

                    );
            return retStop.get();

        }

        if(routeService.getRouteByShortCode(locationRequest.getRouteId()) != null) {

            routeService
                    // use the routeService to pull the route from the ShortCode
                    // which was passed inside the locationRequest
                    .getRouteByShortCode(locationRequest.getRouteId())

                    // From the Route object that matches the Route's ShortCode
                    // get a List of Stops
                    .getStops()

                    // turn this into parallel streams for better processing
                    .parallelStream()

                    // call helper method atStop which says if GPS coordinates passed
                    // in with LocationRequest is with 0.5 km of each stop
                    // only the True response remain in the stream

                    .filter(stop -> checkOtherStops(locationRequest, stop))
                    .forEach(stop -> {
                        retStop.set(stop.getShortCode());
                        stop
                            .getRoute()
                            .getStops()

                            .get(stop.getRoute().getStops().indexOf(stop) + 2)
                            .getRiders()
                            .parallelStream()
                            .map(rider ->
                                    riderService
                                            .notifyRider(rider, null)
                            )
                            .filter(Objects::nonNull)
                            .forEach(rider -> stopService.failedRiderNotifications(rider));}
                    );
            return retStop.get();
        }else{
            System.err.println("Error - Route with shortCode "+locationRequest.getRouteId()+" does not exist.");
        }

        return null;
    }

    private boolean checkOtherStops(LocationRequest locationRequest, Stop stop){
        Route route = stop.getRoute();

        List<Long> stopIds = route.getStops()
                .stream()
                .map(Stop::getId)
                .collect(Collectors.toList());



        // TODO handle scenario of starting the day - notify stops 1 and 2
        // if start of the day - lastStop according to the route matches the last stop in the list of stops of a route
        if(Objects.equals(stopIds.get(stopIds.size() - 1), route.getLastStop())){

            // checking the first stop
            if (Objects.equals(stop, route.getStops().get(0))){
                return true;
            }
            // checking the second stop
            if (Objects.equals(stop, route.getStops().get(1))){
                return true;
            }
        }

        Long lastStop = route.getLastStop() == null ? stopIds.get(stopIds.size()-1) : route.getLastStop();

        if( atStop(locationRequest, stop) ){
            if(Objects.equals(stop.getId(), stopIds.get(0)) && Objects.equals(lastStop, stopIds.get(stopIds.size()-1))){
                // reached first stop on the route


                routeService.updateLastStop(route, stop.getId());
                return true;
            }
            if(stop.getId() == lastStop+1){
                route.setLastStop(stop.getId());
                return true;
            }
        }
        return false;
    }

    /**
     * The atStop is a helper method that uses math to determine if the current GPS
     * location is at a stop.
     * @param locationRequest is the current GPS coordinates and route
     * @param stop is the stop to compare with GPS coordinates
     * @return boolean true if GPS coords are at the stop or not
     */
    private boolean atStop(LocationRequest locationRequest, Stop stop) {

        // lat1 and lon1 are bus GPS coords lat2 and lon2 are stop
        // must be Double objects because of Math.sin requirements
        double lat1 = locationRequest.getLatitude().doubleValue();
        double lon1 = locationRequest.getLongitude().doubleValue();
        double lat2 = stop.getLatitude().doubleValue();
        double lon2 = stop.getLongitude().doubleValue();

        // M = Miles, K = Kilometer, N = Nautical Miles
        // Pull ENVIRONMENTAL VAR
        String env_unit = System.getenv("UNIT");
        // If ENVIRONMENTAL VAR is valid use that, otherwise use K for kilometer
        char unit = env_unit != null &&
                (env_unit.charAt(0)  == 'K' || env_unit.charAt(0) == 'M' || env_unit.charAt(0) == 'N')
                ? env_unit.charAt(0) : 'K';

        // limit is used to determine the farther from a bus stop
        // a bus can be and still be considered at the stop
        // Pull ENVIRONMENTAL VAR
        String env_limit = System.getenv("LIMIT");
        // If ENVIRONMENTAL VAR is valid use that, otherwise use 0.25 kilometeres
        double limit = env_limit != null ? Double.parseDouble(env_limit) : 0.25;
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        System.out.println("Distance for stop " + stop.getShortCode() + " is: " + dist);
         return (dist <= limit);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
