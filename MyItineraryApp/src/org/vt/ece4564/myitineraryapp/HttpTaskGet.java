package org.vt.ece4564.myitineraryapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.R.string;
import android.os.AsyncTask;

public class HttpTaskGet extends AsyncTask<String, Void, String> {
	private GetInfo getInfo_;
	
	public HttpTaskGet(GetInfo mainGet) {
		getInfo_ = mainGet;
	}
	
	@Override
	protected String doInBackground(String... params) {	
		try {		
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(params[0]);
			HttpResponse result = client.execute(request);
			
			InputStream in = result.getEntity().getContent();

			String line = "";

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			String response = sb.toString();
			//always close an InputStream when you are done
			in.close();		
			
//			JSONArray jArray = new JSONArray(response);	
//			int i = 0;
//			if (Integer.parseInt(params[2]) > 3) i = Integer.parseInt(params[2]) - 3;
//			
//			StringBuilder out_new = new StringBuilder();
//			
//			for(int j = i; j < Integer.parseInt(params[2]); j++){
//				
//				out_new.append("Location: " + jArray.getString(j) + "\n");
//			}
//			
//			String out = out_new.toString();
			
			return response;	
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}		
	}
	
	@Override
	protected void onPostExecute(String result) {
		getInfo_.last(result);
	}
}
