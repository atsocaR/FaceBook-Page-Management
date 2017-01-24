import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PostInfo {

  // posts image to page feed
	public static void postImage(String pageID, String app_access_token, String picURL, String message)
			throws MalformedURLException, IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("access_token").append('=').append(app_access_token);
		buffer.append('&').append("message=").append(message).append('&').append("link").append("=").append(picURL);
		String content = buffer.toString();
		// System.out.print(content);

		URLConnection connection = new URL("https://graph.facebook.com/v2.8/" + pageID + "/feed").openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", Integer.toString(content.length()));

		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
		}

		in.close();
	} //-end-method-//

  // puts an image to be scheduled, publishTime is a unix timestamp
	public static void postScheduledImage(String pageID, String app_access_token, String picURL, String message,
			Long publishTime) throws MalformedURLException, IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("caption=").append(message);
		buffer.append('&').append("url").append("=").append(picURL);
		buffer.append('&').append("published").append('=').append("false");
		buffer.append('&').append("scheduled_publish_time=").append(Long.toString(publishTime));
		buffer.append('&').append("access_token").append('=').append(app_access_token);
		String content = buffer.toString(); //
		System.out.println();
		System.out.print(content);
		System.out.println();
		URLConnection connection = new URL("https://graph.facebook.com/v2.8/" + pageID + "/photos").openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", Integer.toString(content.length()));

		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}

			in.close();
		} catch (IOException e) {
			System.out.println("ERROR ON THIS ONE....");
		} // end try catch
	} //-end-method-//

  // posts a comment as your page to a post using postID as input
	public static boolean PostComment( String app_access_token, String postID, String picURL, String message) throws MalformedURLException, IOException{
		StringBuffer buffer = new StringBuffer();
		buffer.append("message=").append(message);
		buffer.append("&").append("attachment_url=").append(picURL);
		buffer.append('&').append("access_token").append('=').append(app_access_token);
		String content = buffer.toString();
		System.out.print(content);
		System.out.println();
		URLConnection connection = new URL("https://graph.facebook.com/v2.8/" + postID + "/comments").openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", Integer.toString(content.length()));

		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(content);
		out.flush();
		out.close();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			}

			in.close();
		} catch (IOException e) {
			System.out.println("ERROR ON THIS ONE....");
			return false;
		} // end try catch
		return true;
	} //-end-method-//
	
} //-end-class-//
