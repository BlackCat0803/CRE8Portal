package com.pharma.core.webservices.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
/**
 * 
 * The class PharmacyClient that Sends the JSON Object from the Pharma Web Portal to the Pioneer Web Service 
 * for pushing the data into the Pioneer System
 *
 */
public class PharmacyClient {

	
	/**
	 * webServiceConnectionWrapper-returns the JSON Object request from web service
	 * @param url
	 * @param mediaType
	 * @param methodType
	 * @param json
	 * @return
	 */
	public static JSONObject webServiceConnectionWrapper(String url,String mediaType, String methodType, String json) {
		JSONObject js = new JSONObject();
		try {			
			js = getJsonData(url, mediaType, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js;
	}

	/**
	 * getJsonData-sends the JSON Object to the Pioneer Web Service for Pushing the data from the Portal to the Pioneer System
	 * @param urlStr
	 * @param mediaType
	 * @param json
	 * @return
	 */
	private static JSONObject getJsonData(String urlStr, String mediaType, String json){
		JSONObject jsonObject = new JSONObject();
	
		try {
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			/*connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);*/
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(json.toString());
			out.close();
	
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
			String output = "";
			while ((output = in.readLine()) != null) {
				//System.out.println("output ========="+output);
				jsonObject = new JSONObject(output);	
			}
			in.close();
			
			if(urlStr.indexOf("PatientSave")!=-1 || urlStr.indexOf("PrescriberSave")!=-1)
			{
				//System.out.println("urlStr==="+urlStr);
				//System.out.println("js webServiceNewID==="+jsonObject.get("webServiceNewID"));
				//System.out.println("js webServiceCallResult==="+jsonObject.get("webServiceCallResult"));
				//System.out.println("js webServiceTableName==="+jsonObject.get("webServiceTableName"));
				new WebServiceResult().updateWebServiceResult(jsonObject);
				
			}
		
			
		} catch (Exception e) {
			//System.out.println("\nError while calling Pharmacy REST Service");
			//System.out.println(e);
		}
		return jsonObject;
	
		
		
}

	
}
