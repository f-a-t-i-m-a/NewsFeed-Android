package ir.appson.sportfeed.proxy;

import android.content.Context;
import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.proxy.dto.SessionStartInput;
import ir.appson.sportfeed.proxy.dto.SessionStartResponse;
import org.json.JSONException;

public class SessionStartProxy extends ProxyBase {

    private static final String url =
            "http://" + apiServerName + apiUrlBase + "/session/start";

    public SessionStartProxy(Context context) {
        super(context);
    }

    public SessionStartResponse post(SessionStartInput sessionStartInput) {
        String sessionId = "";

        try {

            RestClient client = new RestClient(url);
            client.AddHeader(Application9090.USER_AGENT, getUserAgentString());

            client.AddParam("phoneOperator", sessionStartInput.getOperator().toString());
            client.AddParam("phoneSerialNumber", sessionStartInput.getSerialNumber());
            client.AddParam("phoneSubscriberId", sessionStartInput.getSubscriberId());
            client.AddParam("SessionID", sessionStartInput.getSessionId());
//            client.AddParam(sessionStartInput.toJson());

            client.Execute(RestClient.RequestMethod.POST);

            String response = client.getResponse();
            response = response.trim();
            if (response.startsWith("\""))
                response = response.replace("\"","");

            return new SessionStartResponse(
                    client.getResponseCode(),
                    sessionStartInput.getSessionId(),
                    client.getResponseSuggestedVersion(),
                    client.getResponseRequiredVersion());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new SessionStartResponse(200, sessionId, null, null);
    }
}
