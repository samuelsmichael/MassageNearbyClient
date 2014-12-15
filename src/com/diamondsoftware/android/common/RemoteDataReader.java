package com.diamondsoftware.android.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

public class RemoteDataReader {
	public String getRemoteData(String url,List<NameValuePair> mParameters) throws IOException {
		InputStream is=null;
		if(mParameters!=null) {
			HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost(url);

		    try {
		        // Add your data
		        httppost.setEntity(new UrlEncodedFormEntity(mParameters,HTTP.UTF_8));

		        // Execute HTTP Post Request
		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity entity=response.getEntity();
		        is=entity.getContent();
		    } catch (ClientProtocolException e) {
		        return null;
		    }
		} else {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestProperty("content-type", "text/json; charset=utf-8");
			conn.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
			conn.setRequestProperty("Accept","*/*");
			conn.setUseCaches(false);
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(3000);
			long start = System.nanoTime();
			conn.connect();
			long end=System.nanoTime();
			long diff2=end-start;
			if (diff2 > 2508117678l) {
				try {
					conn.disconnect();
				}
				catch (Exception e) {}
				throw new IOException("Probably not connected to internet");
			}
			OutputStream out = conn.getOutputStream();
			PrintWriter pw = new PrintWriter(out);
			pw.close();

			is=conn.getInputStream();
		}		
		InputStreamReader is2 = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(is2);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString();
	}

}
