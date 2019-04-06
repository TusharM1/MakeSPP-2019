package com.example.makespp_2019;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    public WifiP2pManager manager;
    public WifiP2pManager.Channel channel;
    public MainActivity activity;
    public static final String TAG = "WiFiDirectBroadcastReceiver";
    Communicate communicate;

    public WifiDirectBroadcastReceiver(WifiP2pManager managerIn, WifiP2pManager.Channel channelIn, MainActivity activityIn){
        super();
        this.manager = managerIn;
        this.channel = channelIn;
        this.activity = activityIn;
        this.communicate = (Communicate)activityIn;
    }

    public WifiP2pManager.GroupInfoListener groupInfoListener=new WifiP2pManager.GroupInfoListener() {
        @Override
        public void onGroupInfoAvailable(WifiP2pGroup group) {
            if(group!=null){
                Log.d(MainActivity.TAG, group.getClientList().toString());
                communicate.connection(group.getClientList().toString());
            }
        }
    };

    public WifiP2pManager.PeerListListener peerListListener=new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            if(peers.getDeviceList().size() == 0){
                Log.d(MainActivity.TAG, "No Devices Found");
            }else {
                Log.d(MainActivity.TAG, peers.getDeviceList().toString());
                communicate.onGetPeerList(peers);
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals((action))){
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if(state =- WifiP2pManager.WIFI_P2P_STATE_ENABLED)
        }
    }
}
