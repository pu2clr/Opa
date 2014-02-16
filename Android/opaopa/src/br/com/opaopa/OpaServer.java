/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.opaopa;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.util.Log;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author rcaratti
 */
public class OpaServer {

	// URL base para consumo dos serviços do Opa!!!
    private static String opaServerURL = "http://www.consultoriaavaliar.com.br/OpsAdmin/public/oparest/";
    // URL da localização dos arquivos de imagens
    private static String opaImageURL = "http://www.consultoriaavaliar.com.br/OpsAdmin/public/images/";
    // URL do serviço de Upload de arquivos de media
    private static String opaImageUpload = "http://www.consultoriaavaliar.com.br/OpsAdmin/public/upload/upload";
    
    
	private static Bitmap LoadImage(String URL, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();
		} catch (IOException e1) {
		}
		return bitmap;
	}

	private static InputStream OpenHttpConnection(String strURL) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpConn.getInputStream();
			}
		} catch (Exception ex) {
		}
		return inputStream;
	}	

	private static Bitmap getImage(String iconName ) {
		StringBuilder url = new StringBuilder(opaImageURL);
		url.append(iconName);
		return LoadImage(url.toString(), null);
	}
   
    
    /**
     * Obtem um objeto JSON a partir de uma URL
     * 
     * 
     * @param url String contendo a URL que retornará um Stream JSON
     * 
     * @return  JSONObject
     */
    private static JSONObject getJSONfromURL(String url) {

        // initialize
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;
        

        // http post
        try {
            
            URI uri = new URI(url.replace(" ", "%20"));

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(uri);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error: http connection " + e.toString());
        }

        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();

            jArray = new JSONObject(result);
            
        } catch (Exception e) {
            Log.e("log_tag", "Error: converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error: parsing data " + e.toString());
        }

        return jArray;
    }

    /**
     * Obtem a lista de serviços do servidor. Com base no nome do serviço, um URL é formada e uma requisição é feita a um servidor.
     * 
     * @param jsonService - Nome do Serviço no servidor.
     * @return			  - Uma lista de serviços.
     */
    private static List<Service> getServiceList(String jsonService) {
    	
    	List<Service> services = new ArrayList<Service>();
  

        StringBuilder strUrl = new StringBuilder(opaServerURL);
        strUrl.append(jsonService);

        JSONObject json = getJSONfromURL(strUrl.toString());
  
        try {

            JSONArray jsonArray = json.getJSONArray(jsonService);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject e = jsonArray.getJSONObject(i);
                Service service = new Service();
                service.setServiceId(e.getInt("service_id"));
                service.setServiceName(e.getString("service_name"));
                service.setDescription(e.getString("description"));
                service.setIconName(e.getString("icon_name"));
                service.setImage(getImage(e.getString("icon_name")));	
                services.add(service);
            }

        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        
    	return services;
    }

    private static String execute(String jsonService) {

        StringBuilder strUrl = new StringBuilder(opaServerURL);
        strUrl.append(jsonService);

        JSONObject json = getJSONfromURL(strUrl.toString());
        String result = null;
        try {
            result = json.getString("result");
        } catch (JSONException ex) {
            Log.e("Erro JSON", ex.toString());
         }

        return result;
    }

    public static String requestService(String deviceId, String latitude, String longitude, Integer serviceId,
            String message, Integer mediaType, String fileName) {

        StringBuilder jsonService = new StringBuilder(0);
        
        jsonService.append("requestservice/deviceId/").append(deviceId);
        jsonService.append("/latitude/").append(latitude);
        jsonService.append("/longitude/").append(longitude);
        jsonService.append("/serviceId/").append(serviceId);
        jsonService.append("/message/").append(message);
        jsonService.append("/mediaType/").append(mediaType.intValue());
        jsonService.append("/fileName/").append(fileName);
        

        return execute(jsonService.toString());
    }

    public static String registerDevice(String deviceId, String serialNumber, String userName, String email,
            String ddd, String phoneNumber) {

        // Obter o deviceId ou IMEI, deviceName
        // Montar url com parâmetros
        // Chamar execute

    	StringBuilder deviceName = new StringBuilder(Build.MANUFACTURER); 
        deviceName.append(": ").append(Build.MODEL);
    	
    	String osName = "Android";
    	String osVersion = Build.PRODUCT + ": " +  Build.VERSION.RELEASE;
    	
    	
        StringBuilder jsonService = new StringBuilder(0);
        jsonService.append("registerdevice/deviceId/").append(deviceId);
        jsonService.append("/serialNumber/").append(serialNumber);
        jsonService.append("/osName/").append(osName);
        jsonService.append("/osVersion/").append(osVersion);
        jsonService.append("/deviceName/").append(deviceName.toString());
        jsonService.append("/userName/").append(userName);
        jsonService.append("/email/").append(email);
        jsonService.append("/phoneDDD/").append(ddd);      
        jsonService.append("/phoneNumber/").append(phoneNumber);        

        return execute(jsonService.toString());
    }

    public static String enableDevice(String deviceId, String typedCode) {
    
        StringBuilder jsonService = new StringBuilder(0);
        jsonService.append("activedevice/deviceId/").append(deviceId);
        jsonService.append("/typedCode/").append(typedCode);
        return execute(jsonService.toString());
    }
    
    public static String statusDevice(String deviceId) {
        StringBuilder jsonService = new StringBuilder(0);
        jsonService.append("statusdevice/deviceId/").append(deviceId);
        return execute(jsonService.toString());
    }

    
    public static List<Service> getTopServices() {
    	return getServiceList("topservices");
    }
    
    public static List<Service> getAllServices() {
    	return getServiceList("allservices");
    }
    
    /**
     * Data um objeto bitmap executa o upload do objeto (imagem) no servidor.
     * @param bitmap
     * @throws Exception
     */
	public static String executeMultipartPost(Bitmap bitmap, String deviceId) throws Exception {

		// Compõe o nome do arquivo que será enviado ao servidor
		StringBuilder fileName = new StringBuilder("Android_");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String strDate = sdf.format(new Date());
		fileName.append(deviceId).append("_X_").append(strDate).append(".jpg");
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 75, bos);
			byte[] data = bos.toByteArray();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(opaImageUpload);
			ByteArrayBody bab = new ByteArrayBody(data, fileName.toString());

			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			
			reqEntity.addPart("file", bab);
			reqEntity.addPart("description", new StringBody("Foto Baixada do Opa!!!"));
			postRequest.setEntity(reqEntity);
			httpClient.execute(postRequest);

		} catch (Exception e) {
			// handle exception here
			Log.e(e.getClass().getName(), e.getMessage());
		}

		return fileName.toString();

	}
    
}
