package server.BusAlert.Twilio;

public class TwilioRequest {
    public String phoneNumber;
    public String message;
    
    public String getMessage(){ return this.message; }
    public String getPhoneNumber(){ return this.phoneNumber; }
    
}
