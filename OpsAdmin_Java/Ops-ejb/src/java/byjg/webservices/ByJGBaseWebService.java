/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package byjg.webservices;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jg
 */
public class ByJGBaseWebService 
{
    protected static final String WS_BYJG = "http://www.byjg.com.br/site/webservice.php/ws/";

    protected String executeWebService(String service, String method, HashMap<String, String> params) throws MalformedURLException, IOException, ByJGWebServiceException
    {
        String strUrl = ByJGBaseWebService.WS_BYJG;

        strUrl += service + "?httpmethod=" + method;

        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                    strUrl += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
            }
        }

        URL ws_byjg = new URL(strUrl);

        BufferedReader in = new BufferedReader(	new InputStreamReader(ws_byjg.openStream()));

	String inputLine;
        String result = "";
	while ((inputLine = in.readLine()) != null)
	    result += inputLine;

	in.close();

        if (result.contains("ERR|"))
        {
            throw new ByJGWebServiceException(result.substring(4));
        }

        return result.substring(3);

    }
}
