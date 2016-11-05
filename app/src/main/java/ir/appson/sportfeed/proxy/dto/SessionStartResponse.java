package ir.appson.sportfeed.proxy.dto;

public class SessionStartResponse {
    private int statusCode = 0;
    private String sessionId;
    private String suggestedVersion;
    private String requiredVersion;

    public SessionStartResponse(int statusCode, String sessionId, String suggestedVersion, String requiredVersion){
        this.statusCode = statusCode;
        this.sessionId = sessionId;
        this.suggestedVersion = suggestedVersion;
        this.requiredVersion = requiredVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getSuggestedVersion() {
        return suggestedVersion;
    }

    public String getRequiredVersion() {
        return requiredVersion;
    }
}
