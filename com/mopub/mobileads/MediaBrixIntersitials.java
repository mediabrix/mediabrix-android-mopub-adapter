package com.mopub.mobileads;

import android.content.Context;
import android.util.Log;

import com.mediabrix.android.MediaBrix;
import com.mediabrix.android.api.IAdEventsListener;
import com.mediabrix.android.api.MediabrixAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammad on 3/2/16.
 */
public class MediaBrixIntersitials extends CustomEventInterstitial implements IAdEventsListener{

    private final static String BASE_URL = "http://mobile.mediabrix.com/v2/manifest/";
    private final static String APP_ID = "";
    private final static String RALLY = "";
    private Context context;
    private CustomEventInterstitialListener interstitialListener;
    private boolean init = false;
    @Override
    protected void loadInterstitial(Context context, CustomEventInterstitialListener customEventInterstitialListener,
                                    Map<String, Object> localExtras, Map<String, String> serverExtras) {
        this.context = context;
        this.interstitialListener = customEventInterstitialListener;

        MediabrixAPI.getInstance().onResume(this.context);
        MediabrixAPI.getInstance().initialize(this.context, BASE_URL, APP_ID, this);

    }

    @Override
    protected void showInterstitial() {
        MediabrixAPI.getInstance().show(context, RALLY);
        interstitialListener.onInterstitialShown();
    }

    @Override
    protected void onInvalidate() {
        MediabrixAPI.getInstance().onDestroy(this.context);
    }

    @Override
    public void onAdRewardConfirmation(String s) {

    }

    @Override
    public void onAdClosed(String s) {
        interstitialListener.onInterstitialDismissed();
    }

    @Override
    public void onAdReady(String s) {
        interstitialListener.onInterstitialLoaded();
    }

    @Override
    public void onAdUnavailable(String s) {
        interstitialListener.onInterstitialFailed(MoPubErrorCode.UNSPECIFIED);
    }

    @Override
    public void onStarted(String s) {
        MediabrixAPI.getInstance().load(context, RALLY, new HashMap<String, String>());
    }

    @Override
    public void onAdShown(String s) {

    }
}
