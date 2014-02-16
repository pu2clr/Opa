/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.opaopa;


import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author rcaratti
 */
public class Send extends Activity implements LocationListener {

	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; 
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 100; 
	private static final String GPS_INATIVO = "ATENÇÃO: Não foi possível obter a sua localização. Verifique se o GPS ou a sua Rede 3G está ativa.";

	private String latitude;
	private String longitude;

	private String serviceName;
	private Integer serviceId;
	private String deviceId;

	private String provider;
	
	private Bitmap bitmap;
	private ImageView img;

	protected LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// ToDo add your GUI initialization code here

		// this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		bitmap = null;
		
		setContentView(R.layout.send);

		Intent intent = getIntent();
		
		serviceName = intent.getStringExtra("serviceName");
		deviceId = intent.getStringExtra("deviceId");
		
		serviceId = new Integer(intent.getIntExtra("serviceId", -1));

		TextView label = (TextView) findViewById(R.id.labelMsg);
		label.setText("Mensagem para (" + serviceName + ")");

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		provider = locationManager.getBestProvider(criteria, false /* true */);

		// As 5 linhas a seguir: TESTE . Não configura uma solução.
		int i = 0;
		Location location = null;
		do {
			location = locationManager.getLastKnownLocation(provider);
		} while (location == null && i++ < 10);

		if (location == null) {
			Toast.makeText(this, GPS_INATIVO, Toast.LENGTH_LONG).show();
			Toast.makeText(this, GPS_INATIVO, Toast.LENGTH_LONG).show();
			Toast.makeText(this, GPS_INATIVO, Toast.LENGTH_LONG).show();
		} else {
			this.latitude = new Double(location.getLatitude()).toString();
			this.longitude = new Double(location.getLongitude()).toString();
		}

		locationManager.requestLocationUpdates(provider,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);

	}

	protected void OnResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider,
				MINIMUM_TIME_BETWEEN_UPDATES,
				MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);

	}

	protected void OnPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	public void onLocationChanged(Location location) {

		latitude = new Double(location.getLatitude()).toString();
		longitude = new Double(location.getLongitude()).toString();

	}

	public void onStatusChanged(String s, int i, Bundle b) {

	}

	public void onProviderDisabled(String s) {

	}

	public void onProviderEnabled(String s) {

	}

	/**
	 * Obtem a localização corrente (latitude e longitude) e atribui os valores
	 * capturados nas propriedades privadas do tipo String latitude e longitude.
	 * 
	 */
	protected void getCurrentLocation() {

		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			latitude = new Double(location.getLatitude()).toString();
			longitude = new Double(location.getLongitude()).toString();
		} else {
			latitude = "0.0";
			longitude = "0.0";
		}

	}

	public void handleAudio(View v) {

		Toast.makeText(this, "Função de áudio ainda não implementada!",
				Toast.LENGTH_LONG).show();

		// For using MediaRecorder methods, AndroidManifest.xml must have the
		// following permission:
		// <uses-permission android:name="android.permission.RECORD_AUDIO"/>
		MediaRecorder recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile("/tmp/xyz.gpp"); // The file must already exist
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.start();

	}

	public void handleVideo(View v) {
		// Executa a câmara do dispositivo móvel 
		// O resultado final desta operação poderá ser obitido ou tratado pelo método onActivityResult
		Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(i, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null) {
			Bundle bundle = data.getExtras();
			if (bundle != null) {
				// Recupera o Bitmap retornado pela c‚mera
				bitmap = Bitmap.createBitmap((Bitmap) bundle.get("data"));
				// Atualiza a imagem na tela
				img = (ImageView) findViewById(R.id.previewphoto);
				img.setImageBitmap(bitmap);
			}
		}
	}
	
	
	
	public void handleClick(View v) {

		String fileName = null;
		getCurrentLocation();
		EditText message = (EditText) findViewById(R.id.editText1);

		// Tenta processar o upload
		Integer mediaType = null;
		try {
			if ( bitmap != null ) {
			   fileName = OpaServer.executeMultipartPost(bitmap, deviceId);
			   mediaType = new Integer(2);
			}
			else {
			   fileName = "nomedia.jpg";
			   mediaType = new Integer(0);
			}
			bitmap = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = OpaServer.requestService(deviceId, latitude, longitude,
				serviceId, serviceName + ": " + message.getText().toString(), mediaType, fileName);

		String msgResult = null;

		if (result.equals("OK"))
			msgResult = "A sua solicitação foi encaminhada com sucesso e será atendida em dois minutos. Aguarde!";
		else
			msgResult = "ATENÇÃO: " + result;

		Toast.makeText(this, msgResult, Toast.LENGTH_LONG).show();
		Toast.makeText(this, msgResult, Toast.LENGTH_LONG).show();

	}
	

}
