package com.mopub.mobileads;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mediabrix.mopubtestapp.RewardedVideo;
import com.mopub.common.MoPubReward;
import com.mopub.common.util.Streams;
import com.mopub.mobileads.CustomEventRewardedVideo;


import com.mediabrix.android.api.IAdEventsListener;
import com.mediabrix.android.api.MediabrixAPI;
import com.mopub.common.LifecycleListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammad on 4/11/16.
 */
public class MediaBrixRewardedVideo extends CustomEventRewardedVideo implements IAdEventsListener {

    private final static String BASE_URL = "http://mobile.mediabrix.com/v2/manifest/";
    private final static String APP_ID = "";
    private final static String RESCUE = "";
    private Context context;
    private boolean started;
    private boolean adLoaded;

    @Override
    protected boolean checkAndInitializeSdk(@NonNull Activity launcherActivity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        this.context = launcherActivity;
        MediabrixAPI.getInstance().initialize(this.context,BASE_URL,APP_ID,this);
        return true;
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull Activity activity, @NonNull Map<String, Object> localExtras, @NonNull Map<String, String> serverExtras) throws Exception {
        if(started) {
            MediabrixAPI.getInstance().load(this.context, RESCUE, new HashMap<String, String>());
        }
    }

    @Override
    protected void showVideo() {
        if(adLoaded) {
            MediabrixAPI.getInstance().show(this.context, RESCUE);
        }else {
            MoPubRewardedVideoManager.onRewardedVideoPlaybackError(MediaBrixRewardedVideo.class, RESCUE, MoPubErrorCode.VIDEO_PLAYBACK_ERROR);
        }
    }

    @Override
    public void onStarted(String s) {
        this.started = true;
        try {
            loadWithSdkInitialized((Activity)context,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAdReady(String s) {
        this.adLoaded = true;
        MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(MediaBrixRewardedVideo.class,RESCUE);
    }

    @Override
    public void onAdUnavailable(String s) {
        this.adLoaded = false;
        MoPubRewardedVideoManager.onRewardedVideoLoadFailure(MediaBrixRewardedVideo.class,RESCUE,MoPubErrorCode.UNSPECIFIED);
    }

    @Override
    public void onAdShown(String s) {
        MoPubRewardedVideoManager.onRewardedVideoStarted(
                MediaBrixRewardedVideo.class,
                RESCUE);
    }

    @Override
    public void onAdClicked(String s){
        MoPubRewardedVideoManager.onRewardedVideoClicked(MediaBrixRewardedVideo.class, RESCUE);
    }

    @Override
    public void onAdRewardConfirmation(String s) {
        MoPubRewardedVideoManager.onRewardedVideoCompleted(
                MediaBrixRewardedVideo.class,
                RESCUE,
                MoPubReward.success("Success",1));


    }

    @Override
    public void onAdClosed(String s) {
        MoPubRewardedVideoManager.onRewardedVideoClosed(MediaBrixRewardedVideo.class, RESCUE);
        MediabrixAPI.reset();
    }


    @Nullable
    @Override
    protected CustomEventRewardedVideoListener getVideoListenerForSdk() {
        return null;
    }

    @Nullable
    @Override
    protected LifecycleListener getLifecycleListener() {
        return null;
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        return RESCUE;
    }

    @Override
    protected void onInvalidate() {
        MediabrixAPI.getInstance().onDestroy(context);
    }

    @Override
    protected boolean hasVideoAvailable() {
        if(!adLoaded)
            return false;
        return true;
    }
}
