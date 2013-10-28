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

public class HttpTaskPost extends AsyncTask<String, Void, String> {
	private PostInfo postInfo_;
	
	public HttpTaskPost(PostInfo mainPost) {
		postInfo_ = mainPost;
	}
	
	@Override
	protected String doInBackground(String... params) {	
		try {		
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(params[0]);
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("rating", params[1]));
			pairs.add(new BasicNameValuePair("comment", params[2]));
			pairs.add(new BasicNameValuePair("loc", params[3]));	
			pairs.add(new BasicNameValuePair("temp", params[4]));
			pairs.add(new BasicNameValuePair("light", params[5]));	
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
			request.setEntity(entity);
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
			
			return response;		
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}		
	}
	
	@Override
	protected void onPostExecute(String result) {
		postInfo_.last(result);
	}
}
