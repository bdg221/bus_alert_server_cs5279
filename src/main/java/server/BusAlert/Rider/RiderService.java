package server.BusAlert.Rider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.BusAlert.Stop.Stop;
import server.BusAlert.Stop.StopService;
import server.BusAlert.Twilio.TwilioRequest;
import server.BusAlert.Twilio.TwilioService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The RiderService class contains the business logic to handle Riders.
 * The service is called from the BusAlertController.
 */

@Service
public class RiderService {

    /**
     * The RiderRepository is used to access the Rider database table.
     */
    @Autowired
    private RiderRepository riderRepository;

    /**
     * Since a Rider is associated with a Stop, we need to access the
     * stopService in order to manipulate the Stop object.
     */
    @Autowired
    private StopService stopService;

    /**
     * Dependecy injects twilioService to send messages to riders
     */
    @Autowired
    private TwilioService twilioService;


    /**
     * The getRiders method returns all Riders in the database table.
     * @return a List of Rider objects
     */
    public List<Rider> getRiders(){
        return riderRepository.findAll();
    }

    /**
     * The getRider method returns a specific Rider by using the Id.
     * @param Id - the unique Id of a rider
     * @return - a Rider object associated with the Id
     */
    public Rider getRider(Long Id){
        // the getRiderFromRepository is a helper method that takes care of some error handling
        return getRiderFromRepository(Id);
    }


    /**
     * The addRider method creates a new Rider, adds the Rider to a Stop, and finally
     * saves the Rider to the Rider table.
     *
     * @param rider - Rider to be added and saved
     * @return the new Rider object that has been created and saved
     */
    public Rider addRider(
            Rider rider
    ){
        // First get the Stop object from the stopId
        Stop stop = stopService.getStop(rider.getStopIdOnly());
        if(stop == null){
            System.err.println("Failed to create rider " + rider.getPhone() + " because stop id" + rider.getStopIdOnly() + " does not exist.");
            return null;
        }

        // create a new Rider object with the passed in phone and Stop
        rider.setStop(stop);

        // save the Rider in the database (auto-generates the Rider's Id value)
        rider = riderRepository.save(rider);

        // now add the Rider to the Stop
        stop.addRider(rider);

        // return the newly created Rider
        return rider;
    }

    /**
     * The modifyRider method requires an Id parameter and then has optional other parameters
     * that can be modified.
     *
     * @param Id - required Id value of the Rider to be modified
     * @param phone - optional String value of a new phone number
     * @param stopId - optional Stop Id of a new Stop
     * @return the newly modified Rider object
     */
    public Rider modifyRider(
            Long Id,
            String phone,
            Long stopId
    ){
        // get the existing Rider from the passed in Id
        Rider rider = getRiderFromRepository(Id);

        // error handling if the Rider is not found
        if(rider == null) return null;

        // if the phone number is passed in then update the Rider's phone number
        if (phone != null) rider.setPhone(phone);

        // if the stop Id is passed then we will need to modify the Stop
        if (stopId != null){

            // make sure the Rider has a Stop already set
            if(rider.getStop() != null ){

                // delete the Rider from the previous Stop object
                rider.getStop().deleteRider(rider);
            }

            // get the new Stop object to be associated with the Rider
            Stop new_stop = stopService.getStop(stopId);

            // add the Rider to the Stop object
            new_stop.addRider(rider);

            // set the Stop value to the Rider
            rider.setStop(new_stop);
        }

        // save the updated Rider to the database and return it
        return riderRepository.save(rider);
    }

    /**
     * The deleteRider removes the Rider from the database and removes the Rider from
     * the associated Stop object.
     * @param Id - the Id of the Rider to be deleted
     */
    public void deleteRider(
            Long Id
    ){
        // get the Rider from the database using the passed in Id
        Rider rider = getRiderFromRepository(Id);

        // make sure the Rider exists
        if(rider != null) {

            // make sure the Rider has a Stop
            if(rider.getStop() != null){

                // remove the Rider from the Stop object
                rider.getStop().deleteRider(rider);
            }
            // remove the Rider from the database
            riderRepository.delete(rider);
        }
    }

    /**
     * The getRiderFromRepository is a helper method to handle the error
     * case where the Id passed in does not match a Rider.
     * @param Id - Rider's Id value
     * @return either null if not found or the Rider object
     */
    private Rider getRiderFromRepository(Long Id){
        // findById returns an optional which can be empty so we need to check
        Optional<Rider> checkRider = riderRepository.findById(Id);

        // if the Optional is empty return null otherwise return the Rider
        return (checkRider.orElse(null));
    }

    public Rider notifyRider(Rider rider, String msg){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
        Date date = new Date();

        // Default message:
        msg = (msg == null) ? msg = "Your bus is two stops away. "+ dateFormat.format(date) : msg + " " + dateFormat.format(date);


        // This is where we would integrate with different outputs
        // Currently we are working on Twilio integration
        // until that is complete, we will be saying all communication
        // attempts were successful and returning true
        if(twilioService.SendMessage(new TwilioRequest(rider.getPhone(), msg))){
            System.out.println("RiderService->notifyRider - SUCCESS - Message for rider ID "+rider.getId()+" was successful.");
            return null;
        }
        System.out.println("RiderService->notifyRider - FAILURE - Message for rider ID "+rider.getId()+" was NOT successful.");
        return rider;
    }

}
