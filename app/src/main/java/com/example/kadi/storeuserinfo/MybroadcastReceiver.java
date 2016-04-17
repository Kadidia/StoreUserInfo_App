package com.example.kadi.storeuserinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kadi on 07/04/2016.
 */
public class MybroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extra = intent.getExtras();
        if(extra != null){
            myDBHandler dbHandler = new myDBHandler(context);
            String website = extra.getString("website");
            HashMap<String, Integer> data = dbHandler.getAllUserInfos();

            if(data.containsKey(website)){

                dbHandler.addlink(website);
                Toast.makeText(context, "Broadcast message received : " + "Visit updated", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "message is not right" + website, Toast.LENGTH_SHORT).show();
            }



        }

    }


}
