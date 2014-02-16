/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.opaopa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 *
 * @author rcaratti
 */
public class DeviceConfirmation extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here 
        setContentView(R.layout.device_confirmation);
    }

    public void handleClick(View v) {

        EditText typedCode = (EditText) findViewById(R.id.editTextTypedCode);

        Intent it = getIntent();

        String deviceId = it.getStringExtra("deviceId");

        String result = OpaServer.enableDevice(deviceId, typedCode.getText().toString());

        if (result.equals("Activated")) {
            Toast.makeText(this, "Dispositivo Ativado com Sucesso.", Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
