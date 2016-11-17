package com.khoonat.news.proxy;

import android.content.Context;
import com.khoonat.news.ApplicationNEWS;
import com.khoonat.news.proxy.dto.SessionStartInput;
import com.khoonat.news.proxy.dto.SessionStartResponse;
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
            client.AddHeader(ApplicationNEWS.USER_AGENT, getUserAgentString());

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
