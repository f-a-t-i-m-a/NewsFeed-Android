package com.khoonat.news.proxy.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionStartInput {
    private String subscriberId;
    private String serialNumber;
    private Operator operator;
    private String sessionId;

    public SessionStartInput(String subscriberId, String serialNumber, Operator operator, String sessionId) {
        this.subscriberId = subscriberId;
        this.serialNumber = serialNumber;
        this.operator = operator;
        this.sessionId = sessionId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Operator getOperator() {
        return operator;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String toJson()
    {
        JSONObject result = new JSONObject();
        try {
            result.put("phoneOperator", operator.toString());
            result.put("phoneSerialNumber", serialNumber);
            result.put("phoneSubscriberId", subscriberId);
            result.put("sessionId", sessionId);
            return result.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
