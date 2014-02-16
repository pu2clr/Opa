/*
 * Registra um dispositivo móvel no sistema Opa
 * 
 * 
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
public class DeviceRegister extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here     
        setContentView(R.layout.device_register);
    }

    public void handleClick(View v) {

        EditText userName = (EditText) findViewById(R.id.editTextUserName);
        EditText email = (EditText) findViewById(R.id.editTextEmail);
        EditText ddd = (EditText) findViewById(R.id.editTextDDD);
        EditText phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        EditText ddd2 = (EditText) findViewById(R.id.editTextDDD2);
        EditText phoneNumber2 = (EditText) findViewById(R.id.editTextPhoneNumber2);
        
        if ( ddd.getText().toString().trim().equals("") ) {
            Toast.makeText(this, "Informe o número do DDD da cidade de origem do seu celular (Exemplo: 61).", Toast.LENGTH_LONG).show();  
            return; 
        } 
        else if ( phoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Informe o número do Celular (Exemplo: 99990011).", Toast.LENGTH_LONG).show();  
            return;
        }
        else if (!ddd.getText().toString().equals(ddd2.getText().toString())  || !phoneNumber.getText().toString().equals(phoneNumber2.getText().toString())) {
            Toast.makeText(this, "Os números do DDD e do celular devem ser corfirmados corretamente.", Toast.LENGTH_LONG).show();  
            return;
        }

        Intent it = getIntent();

        String deviceId = it.getStringExtra("deviceId");
        String serialNumber = it.getStringExtra("serialNumber");
        String result = OpaServer.registerDevice(deviceId, serialNumber, 
                userName.getText().toString(),
                email.getText().toString(),
                ddd.getText().toString(),
                phoneNumber.getText().toString());

        if (result.equals("Pending")) {
            Toast.makeText(this, "Um código de ativação será enviado para conclusão do seu registro.", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Um código de ativação será enviado para conclusão do seu registro.", Toast.LENGTH_LONG).show();
            // Chama o formulário de confirmação do Registro
            Intent itConfirm = new Intent();
            Bundle params = new Bundle();
            params.putString("deviceId", deviceId);
            itConfirm.putExtras(params);
            itConfirm.setClass(this, DeviceConfirmation.class);
            itConfirm.setAction("android.intent.action.DeviceListActivity");
            startActivity(itConfirm);  
            finish();
        }
        else {
            Toast.makeText(this, "Erro ao tentar registrar o dispositivo: " + result, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Erro ao tentar registrar o dispositivo: " + result, Toast.LENGTH_LONG).show();
        }



    }
}
