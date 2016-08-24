package com.fullsail.android.busted.net;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	
	public static boolean isConnected(Context _context) {
		
		ConnectivityManager mgr = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if(mgr != null) {
			NetworkInfo info = mgr.getActiveNetworkInfo();

			if(info != null) {
				if(info.isConnected()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static String getNetworkData(String _url) {
		
		URL url = new URL(_url);
		
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		
		connection.connect();
		
		InputStream is = connection.getInputStream();
		
		String data = IOUtils.toString(is);
		
		is.close();
		
		connection.disconnect();
		
		return data;
	}
}