package server.BusAlert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Location.LocationRequest;
import server.BusAlert.Route.Route;
import server.BusAlert.Route.RouteService;
import server.BusAlert.Stop.Stop;
import server.BusAlert.Stop.StopService;

import java.util.List;
import java.util.stream.Stream;

@Service
public class BusAlertService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    public void receiveGPS(LocationRequest locationRequest){


        routeService
                // use the routeService to pull the reoute from the ShortCode
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
                .filter( stop -> atStop(locationRequest, stop))

                .forEach(stop -> stopService.notifyRiders(stop));

    }

    private boolean atStop(LocationRequest locationRequest, Stop stop) {

        // lat1 and lon1 are bus GPS coords lat2 and lon2 are stop
        // must be Double objects because of Math.sin requirements
        double lat1 = locationRequest.getLatitude().doubleValue();
        double lon1 = locationRequest.getLongitude().doubleValue();
        double lat2 = stop.getLatitude().doubleValue();
        double lon2 = stop.getLongitude().doubleValue();

        // M = Miles, K = Kilometer, N = Nautical Miles
        char unit = 'K';
        // limit is used to determine the farther from a bus stop
        // a bus can be and still be considered at the stop
        double limit = 0.5;
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
         return (dist <= limit);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
