package naveed.khakhrani.miscellaneous.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {

	private static NetworkConnection mCheckConnection;

	public static NetworkConnection getInstance() {
		if (mCheckConnection == null) {
			mCheckConnection=new NetworkConnection();
		}
		return mCheckConnection;
	}

	public static boolean isConnection(Context ctx) {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        return ni != null && ni.isAvailable() && ni.isConnected();
	}
}
