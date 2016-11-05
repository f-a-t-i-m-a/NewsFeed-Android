package ir.appson.sportfeed.views.firstPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.SessionStartProxy;
import ir.appson.sportfeed.proxy.dto.Operator;
import ir.appson.sportfeed.proxy.dto.SessionStartInput;
import ir.appson.sportfeed.proxy.dto.SessionStartResponse;

public class SessionStartAsync extends AsyncTask<Object, Object, SessionStartResponse> {
    private Context context;
    private SessionStartProxy proxy;

    public SessionStartAsync(Context context) {
        this.context = context;
        this.proxy = new SessionStartProxy(context);
    }

    protected SessionStartResponse doInBackground(Object... params) {
        try {
            return proxy.post(prepareInput());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onPostExecute(SessionStartResponse responseObject) {
        if (responseObject == null) {
            return;
        }

        checkForUpdate(responseObject);
    }

    public SessionStartInput prepareInput() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService((Context.TELEPHONY_SERVICE));
        String subscriberId = tm.getSubscriberId();
        if (subscriberId == null)
            subscriberId = "";

        String serialNumber = tm.getSimSerialNumber();
        if (serialNumber == null)
            serialNumber = "";

        Operator operator = getCurrentOperator(tm);

        String sessionId = ((Application9090) context.getApplicationContext()).getSessionId();

        return new SessionStartInput(subscriberId, serialNumber, operator, sessionId);
    }

    private Operator getCurrentOperator(TelephonyManager tm) {
        String networkOperator = tm.getSimOperatorName().toUpperCase();
        if (networkOperator.contains("RIGH")||networkOperator.contains("IRN"))
            return Operator.RIGHTEL;
        if (networkOperator.contains("MTN")||networkOperator.contains("IRANCELL")||networkOperator.contains("IRAN"))
            return Operator.MTN;
        if (networkOperator.contains("MCI")||networkOperator.contains("TCI"))
            return Operator.MCI;
        return Operator.UNKNOWN;
    }

    private boolean checkForUpdate(SessionStartResponse response) {
        String suggestedVersion = response.getSuggestedVersion();
        String requiredVersion = response.getRequiredVersion();

        if (suggestedVersion != null && !suggestedVersion.isEmpty()) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.suggested_update_title)
                    .setMessage(R.string.suggested_update_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else if ((requiredVersion != null && !requiredVersion.isEmpty())) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.required_update_title)
                    .setMessage(R.string.required_update_message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            System.exit(0);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;

        }
        return false;
    }

}
