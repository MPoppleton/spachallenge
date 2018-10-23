package au.com.hypothesisconsulting.spachallenge.rest;

public class ResponseDTO {

    public String message;
    public String provider;
    public boolean error;

    public ResponseDTO(String message, String provider, boolean error) {
        this.message = message;
        this.provider = provider;
        this.error = error;
    }
}
