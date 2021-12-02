package com.efluvi.capacitoradididfa;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.common.util.concurrent.Futures;

@CapacitorPlugin(name = "AdId")
public class AdIdPlugin extends Plugin {

    // private AdId implementation = new AdId();

    @PluginMethod
    public void echo(PluginCall call) {
        determineAdvertisingInfo(call);
    }

    private void determineAdvertisingInfo(PluginCall call) {
        if (AdvertisingIdClient.isAdvertisingIdProviderAvailable()) {
            ListenableFuture<AdvertisingIdInfo> advertisingIdInfoListenableFuture = AdvertisingIdClient.getAdvertisingIdInfo(
                getApplicationContext()
            );

            Futures.addCallback(
                advertisingIdInfoListenableFuture,
                new FutureCallback<AdvertisingIdInfo>() {
                    @Override
                    public void onSuccess(AdvertisingIdInfo adInfo) {
                        String id = adInfo.getId();
                        String providerPackageName = adInfo.getProviderPackageName();
                        boolean isLimitTrackingEnabled = adInfo.isLimitTrackingEnabled();
                        // Any exceptions thrown by getAdvertisingIdInfo()
                        // cause this method to get called.

                        ret.put("value", id);
                        call.resolve(ret);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("log", "Failed to connect to Advertising ID provider.");
                        // Try to connect to the Advertising ID provider again,
                        // or fall back to an ads solution that doesn't require
                        // using the Advertising ID library.
                        ret.put("value", 'none');
                        call.resolve(ret);
                    }
                }
            );
        } else {
            // The Advertising ID client library is unavailable. Use a different
            // library to perform any required ads use cases.
            ret.put("value", 'none');
            call.resolve(ret);
        }
    }
}
