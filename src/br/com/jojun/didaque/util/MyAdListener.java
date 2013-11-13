package br.com.jojun.didaque.util;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest.ErrorCode;

// Receives callbacks on various events related to fetching ads.  In this sample,
// the application displays a message on the screen.  A real application may,
// for example, fill the ad with a banner promoting a feature.
public class MyAdListener implements AdListener {

    @Override
    public void onDismissScreen(Ad ad) {}

    @Override
    public void onFailedToReceiveAd(Ad ad, ErrorCode errorCode) {
//        mAdStatus.setText(R.string.error_receive_ad);
    }

    @Override
    public void onLeaveApplication(Ad ad) {}

    @Override
    public void onPresentScreen(Ad ad) {}

    @Override
    public void onReceiveAd(Ad ad) { 
//    	mAdStatus.setText(""); 
    }
}