import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class GetInfo {

	// get pageID from FaceBook page URL
	public static String getPageID(String urlIn) {
		URL url;
		try {
			url = new URL(urlIn);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "ERROR";
		}
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inLine = "";
			while ((inLine = in.readLine()) != null) {
				// System.out.println(inLine);
				if (inLine.contains("\"pageID\":")) {
					String result = inLine.substring(inLine.indexOf("\"pageID\":") + 10);
					result = result.substring(0, result.indexOf("\","));
					return result;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

		return urlIn;
	}

	// get list of FB URLs from search string
	public static ArrayList<String> ScrapeFBSearch(String searchTerm, String access_token) {

		ArrayList<String> results = new ArrayList<String>();
		URL url;
		try {
			url = new URL("https://graph.facebook.com/v2.8/search?access_token=" + access_token + "&type=page&q="
					+ searchTerm);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return results;
		}
		try {
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inLine = "";
			while ((inLine = in.readLine()) != null) {
				System.out.println(inLine);

				if (inLine.contains("\"id\"")) {
					String result = inLine;
					while (result.contains("\"id\"")) {
						result = result.substring(result.indexOf("\"id\"") + 6);
						String tmpresult = result.substring(0, result.indexOf("\""));
						results.add(tmpresult);
//						System.out.println(tmpresult);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return results;
		}

		return results;
	}

}
