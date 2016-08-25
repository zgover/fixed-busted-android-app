// Zachary Gover
// JAV1 - 1608
// NetworkUtils

package com.fullsail.android.busted.net;

import java.io.IOException;
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
		InputStream is = null;
		HttpURLConnection connection = null;

		try {
			URL url = new URL(_url);
			connection = (HttpURLConnection) url.openConnection();

			connection.connect();
			is = connection.getInputStream();

			return IOUtils.toString(is);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (connection != null) {
				connection.disconnect();
			}
		}

		return null;
	}
}