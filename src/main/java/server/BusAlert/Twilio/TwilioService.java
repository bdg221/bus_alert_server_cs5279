package server.BusAlert.Twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Autowired
    private ConfigProperties configProp;

    public boolean SendMessage(TwilioRequest request) {
        String twilioUsername = configProp.getConfigValue("twilio.username");
        String twilioAuthToken = configProp.getConfigValue("twilio.auth-token");
        String twilioPhoneNumber = configProp.getConfigValue("twilio.phone-number");

        try {
            Twilio.init(twilioUsername, twilioAuthToken);

            Message.creator(new PhoneNumber(request.phoneNumber),
                    new PhoneNumber(twilioPhoneNumber), request.message).create();
            System.out.println("Sending \"" + request.message + "\" to " + request.phoneNumber);

            return true;
        } catch (Exception err) {
            System.out.println("Failed to send message to " + request.phoneNumber);
        }

        return false;


    }
}
