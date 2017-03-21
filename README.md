#MediaBrix Android MoPub Adapter

MediaBrix has created a MoPub adapter that allows publishers, using MoPub as their central ad server, to mediate the MediaBrix network as another demand source.  This is done by setting up MediaBrix as a Custom Native Network in MoPub.

***NOTE: The Android MoPub Adapter can only be used for one product in your integration. We do not support the use of Interstitial and Rewarded Video at the same time.***

##Prerequisites
* MediaBrix Appl_ID and Zone_Name(s)
* MediaBrix SDK jar file ([download/clone here](https://github.com/mediabrix/mediabrix-android-sdk/tree/master/Android/SDK%20Files))
* MoPub SDK jar file
* GooglePlayservice (Android only.  Required for MoPub SDK)
* Only tested on MoPub SDK version 4.0+.  Should work on earlier SDKs.  

##Unity Projects
**Step 1:** Download MediaBrix_MoPubUnity.unitypackage from [here](https://github.com/mediabrix/mediabrix-android-mopub-adapter/tree/master/Unity)

**Step 2:** Copy the MediaBrix_MoPubAdapter.jar and mediabrix-sdk-FBless.jar into the "Android/libs" folder.  Copy MediaBrixPlugin.cs and MediaBrixPluginAndroid.cs to same location where the script that is script requesting MoPub ads.

**Step 3:** Copy the following below into your project's AndroidManifest.xml:

``<activity
     android:name="com.mediabrix.android.service.AdViewActivity"
     android:configChanges="orientation|screenSize|keyboard"
     android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" >
</activity>``

``<service
      android:name="com.mediabrix.android.service.MediaBrixService" >
</service> ``

**Step 4:** Add the following in the script that requests Mopub's ads.

``void Start(){
     MediabrixPlugin.Resume();
}``

``private void OnApplicationPause(bool pauseStatus) { 
     if (pauseStatus) {
	MediabrixPlugin.Pause();
     } else {
	MediabrixPlugin.Resume();
     }        
}
``

**Step 5:** Follow the steps in **MoPub Setup** located towards the end of the page.

##Android Core Projects
**Step 1:** Add the MediaBrix SDK to your project ([which can be found here](https://github.com/mediabrix/mediabrix-android-sdk/tree/master/Android/SDK%20Files))

**Step 2:** Download **mediabrix-mopub-core.jar** from [here](https://github.com/mediabrix/mediabrix-android-mopub-adapter/tree/master/Android%20Core)

**Step 3:** Copy mediabrix-sdk-FBless.jar and mediabrix-mopub-core.jar into your project.
*  Android Studio: 
````
dependencies {
    compile 'com.android.support:support-v4:21.0.3'// This can be changed to
                                                   // whatever version you want  
    compile files('libs/mediabrix-sdk-FBless.jar') 
    compile files('libs/mediabrix-mopub-core.jar') 
}
````

* Eclipse:
  * Right Click your project, and select "Properties'
  * Select "Java Build Path", and select "Add External Jars"
  * Add the MediaBrix SDK and mediabrix-mopub-core.jar

**Step 4:** Copy the following below into your project's AndroidManifest.xml:

``<activity
     android:name="com.mediabrix.android.service.AdViewActivity"
     android:configChanges="orientation|screenSize|keyboard"
     android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" >
</activity>``

``<service
      android:name="com.mediabrix.android.service.MediaBrixService" >
</service> ``

**Step 5:** In the activity that is requesting the rewarded video/interstitial ad add the following lines to your activity's ``onResume`` and ``onPause`` methods.

````@Override
    public void onPause() {
        super.onPause();
        MediabrixAPI.getInstance().onPause(getApplicationContext());
    }
````

````@Override
    public void onResume() {
        super.onResume();
		MediabrixAPI.setDebug(true); // This method prints out logs from the MediaBrix SDK.
				     				//  To disable logs, set the method to false.
        MediabrixAPI.getInstance().onResume(getApplicationContext());
    }
````


##MoPub Setup

**Step 1:** Login into your MoPub account

**Step 2:** Select the "Networks" tab, and click on "Add a Network"

![Step 2](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_16_42_163.png)

**Step 3:** Select the "Custom Native Network"

![Step 3](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_16_39_002.png)

**Step 4:** Set the title to **"MediaBrix"**

![Step 4](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_16_44_344.png)

**Step 5:** Find your app, and add **"com.mopub.mobileads.MediaBrixInterstitial"** or **"com.mopub.mobileads.MediaBrixRewardedVideo"** into **"CustomEventClass"** field for fullscreen/reward ads

**Step 6:** In **Custom Event Class Data** copy the following:

``{"app":"APP_ID","zone":"ZONE_NAME"}``

**Step 7:** Add the app id and zone provided to you by MediaBrix into the "app" and "zone" nodes.

![Step 6-7](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_16_53_125.png)

**Step 8:** Save

**Step 9:** Select the "Orders" tab and click the "Add new Order" button

![Step 9](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_17_00_036.png)

**Step 10:** Create a **"Order Name"** and enter **"MediaBrix"** as the  advertiser

![Step 10](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_17_03_507.png)

**Step 11:** With in the Line Item Details Section, select **"Network"** for **"Type & Priority"**

**Step 12:** Within the Class section enter: **"com.mopub.mobileads.MediaBrixInterstitial"** or **"com.mopub.mobileads.MediaBrixRewardedVideo"**. 

**Step 13:** Enter ``{"app":"APP_ID","zone":"ZONE_NAME"}``, with the values you replaced in Step 7 into the **Data** field.

![Step 13](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_17_06_418.png)

**Step 14:** Within Ad Unit Targeting, select the fullscreen/reward ad units where you added the Custom Event Class in Step 5

![Step 14](https://cdn.mediabrix.com/o38%2Fdevsupportportal%2FMoPub%20Adapter%20Images%2F2016_03_07_17_09_439.png)

**Step 15:** Add other desired targeting and Save Order
