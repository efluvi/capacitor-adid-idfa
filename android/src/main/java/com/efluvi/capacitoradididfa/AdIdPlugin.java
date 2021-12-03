package com.efluvi.capacitoradididfa;

import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;


@CapacitorPlugin(name = "AdId")
public class AdIdPlugin extends Plugin {
    private class GoogleAppIdTask extends AsyncTask<Void, Void, String> {
        PluginCall call;

        public GoogleAppIdTask(PluginCall call) {
            this.call = call;
        }

        protected String doInBackground(final Void... params) {
            try {
                String adId = AdvertisingIdClient.getAdvertisingIdInfo(getActivity().getApplicationContext()).getId();
                if (TextUtils.isEmpty(adId)) {
                    adId = "none";
                }
                return adId;
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.d("log", "IllegalStateException");
                return "none";
            }
        }

        protected void onPostExecute(String adId) {
            //작업 수행

            JSObject ret = new JSObject();
            ret.put("id", adId);
            call.resolve(ret);

        }
    }


    @PluginMethod
    public void getAdId(PluginCall call) {
        determineAdvertisingInfo(call);
    }


    private void determineAdvertisingInfo(PluginCall call) {

        GoogleAppIdTask getTask = new GoogleAppIdTask(call);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            getTask.execute();
        }
    }
}
