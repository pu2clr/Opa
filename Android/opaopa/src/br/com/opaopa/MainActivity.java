package br.com.opaopa;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.provider.Settings.System;

public class MainActivity extends ListActivity {

    private Services services;
    private String deviceId;
    private String serialNumber;
    private String statusDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        if ( deviceId == null ) {
        	deviceId = System.getString(this.getContentResolver(),System.ANDROID_ID);
        	serialNumber = telephonyManager.getSubscriberId();
        }
        else {
           serialNumber = telephonyManager.getSimSerialNumber();
        }

        statusDevice = OpaServer.statusDevice(deviceId);

        Intent it = new Intent();
        Bundle params = new Bundle();
        params.putString("deviceId", deviceId);
        params.putString("serialNumber", serialNumber);
        it.putExtras(params);


        if (statusDevice.equals("Device not found")) {
            // registra o dispositivo
            Toast.makeText(this, "Dispositivo não registrado! Favor preencha o formulário de registro.", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Dispositivo não registrado! Favor preencha o formulário de registro.", Toast.LENGTH_LONG).show();
            it.setClass(this, DeviceRegister.class);
            it.setAction("android.intent.action.REGISTER");
            startActivity(it);

        } else if (!statusDevice.equals("Activated")) {
            // habilita o dispositivo 
            Toast.makeText(this, "Favor confirme o código de ativação!", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Favor confirme o código de ativação!", Toast.LENGTH_LONG).show();
            it.setClass(this, DeviceConfirmation.class);
            it.setAction("android.intent.action.CONFIRMATION");
            startActivity(it);
        } else {
            services = new Services();
            List<Service> list = services.getServices();

            ArrayAdapter<Service> adapter = new ServiceArrayAdapter(this,list);
            setListAdapter(adapter);

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Service o = (Service) this.getListAdapter().getItem(position);


        // Intent intent = new Intent(this, Send.class);

        Intent intent = new Intent();
        intent.setClass(this, Send.class);
        intent.setAction("android.intent.action.SEND");


        Bundle params = new Bundle();
        params.putString("serviceName", o.getServiceName());
        params.putInt("serviceId", o.getServiceId());

        params.putString("deviceId", deviceId);
        intent.putExtras(params);

        startActivity(intent);

    }

}
