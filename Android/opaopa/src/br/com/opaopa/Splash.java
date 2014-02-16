/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.opaopa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * 
 * @author rcaratti
 */
public class Splash extends Activity {

	/**
	 * The thread to process splash screen events
	 */
	private Thread mSplashThread;

	private LocationManager locationManager;
	private String provider;
	private static final String NET_INATIVA = "O Opa!!! precisa de uma conexão com a Internet. Verifique o seu acesso a rede (3G ou Wi-Fi) e execute novamente o programa!";
	private static final String GPS_INATIVO = "ATENÇÃO: Verifique se o GPS ou a sua Rede 3G está ativa antes de utilizar este aplicativo.";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Splash screen view
		setContentView(R.layout.splash);

		final Splash sPlashScreen = this;
		
		if (!isConnected()) {
			Toast.makeText(this, NET_INATIVA, Toast.LENGTH_LONG).show();
			Toast.makeText(this, NET_INATIVA, Toast.LENGTH_LONG).show();
			Toast.makeText(this, NET_INATIVA, Toast.LENGTH_LONG).show();
			finish();
		} else {

			// The thread to wait for splash screen events
			mSplashThread = new Thread() {
				@Override
				public void run() {
					try {
						synchronized (this) {
							// Wait given period of time or exit on touch
							wait(3000);
						}
					} catch (InterruptedException ex) {
					}

					finish();

					// Run next activity
					Intent intent = new Intent();
					intent.setClass(sPlashScreen, MainActivity.class);
					startActivity(intent);
//					stop();
				}
			};

			mSplashThread.start();
		}
	}

	/**
	 * Processes splash screen touch events
	 */
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mSplashThread) {
				mSplashThread.notifyAll();
			}
		}
		return true;
	}

	private boolean isConnected() {
		try {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo().isConnectedOrConnecting();
		} catch (Exception e) {
			return false;
		}
	}


}
