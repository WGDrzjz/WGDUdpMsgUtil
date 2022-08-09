package com.jndv.jnudpmsg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jndv.jnudplibrary.JNUdpManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = "{" +
                        "   \"GPSItem\" : {" +
                        "      \"Angle\" : \"\"," +
                        "      \"DataOp\" : \"\"," +
                        "      \"Extend\" : {" +
                        "         \"action\" : \"解锁进入home\"," +
                        "         \"actionId\" : \"\"," +
                        "         \"actionType\" : \"1\"," +
                        "         \"altitude\" : \"4.9E-324\"," +
                        "         \"cellSignalType\" : \"WIFI\"," +
                        "         \"gpsAccuracyStatus\" : \"\"," +
                        "         \"isConnectNetwork\" : \"1\"," +
                        "         \"isOnline\" : \"1\"," +
                        "         \"loctype\" : \"网络定位结果 \"," +
                        "         \"networkLocationType\" : \"cl\"," +
                        "         \"operationTime\" : \"2022-07-13 14:59:39\"" +
                        "      },\n" +
                        "      \"Lat\" : \"\"," +
                        "      \"Lon\" : \"\"," +
                        "      \"ReportType\" : \"\"," +
                        "      \"Speed\" : \"\"," +
                        "      \"Step\" : \"\"," +
                        "      \"UTC\" : \"\"" +
                        "   }," +
                        "   \"UserName\" : \"\"" +
                        "}";
                JNUdpManager.getInstance().sendMsgThread(msg.getBytes());
            }
        });

    }
}