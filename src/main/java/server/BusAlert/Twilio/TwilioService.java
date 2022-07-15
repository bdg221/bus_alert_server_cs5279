package server.BusAlert.Twilio;

import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    public static boolean SendMessage(TwilioRequest request) {
        System.out.println("Sending \"" + request.message + "\" to " + request.phoneNumber);
        return true;
    }
}
