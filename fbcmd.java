import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
import javax.net.*;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

public class fbcmd{

	public static void postScheduledText(String pageID, String app_access_token, String message,
			Long publishTime) throws MalformedURLException, IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("message=").append(message);
		buffer.append('&').append("published").append('=').append("false");
		buffer.append('&').append("scheduled_publish_time=").append(Long.toString(publishTime));
		buffer.append('&').append("access_token").append('=').append(app_access_token);
		String content = buffer.toString(); //
		System.out.println();
		System.out.print(content);
		System.out.println();
		URLConnection connection = new URL("https://graph.facebook.com/v2.8/" + pageID + "/feed").openConnection();
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
			e.printStackTrace();
			System.out.println("ERROR ON THIS ONE....");
		} // end try catch

	}

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
	}

	public static void postScheduledImage(String pageID, String app_access_token, String picURL, String message,
			Long publishTime) throws MalformedURLException, IOException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("caption=").append(message);
		buffer.append('&').append("url").append("=").append(picURL).append(".jpg");
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

	}

	public static boolean PostComment( String app_access_token, String postID, String picURL, String message) throws MalformedURLException, IOException{
		StringBuffer buffer = new StringBuffer();
//		buffer.append("message=").append(message);
		buffer.append("").append("attachment_url=").append(picURL);
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
//				System.out.println(inputLine);
			}

			in.close();
		} catch (IOException e) {
			System.out.println("ERROR ON THIS ONE....");
			return false;
		} // end try catch
		return true;
	}

	public static String getPageFeed(String pageID, String access_token) throws IOException{
		String urls = "https://graph.facebook.com/v2.8/" + pageID + "/feed?access_token=" + access_token;
//		System.out.println( urls );
		URL url = new URL(urls);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setDoOutput(true);
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

			// bufferedReader to string
			String inLine = "";
			while ((inLine = in.readLine()) != null) {
//				response = response + inLine;
				String temp = inLine;
				if (temp.contains("\"id\"")) {
//					System.out.println(temp);
					temp = temp.substring(temp.indexOf("\"id\"") + 6);
					temp = temp.substring(0, temp.indexOf("\""));
//					 System.out.println( temp );
					return temp;
				} else {
					return "";
				}
//				System.out.println(inLine);
			}
//			 System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}

	public static String getPageLikeCount(String urlIn) {
		URL url;
		try {
			url = new URL("https://www.facebook.com/v2.5/plugins/like.php?locale=en_US&href=" + urlIn);
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
				if (inLine.contains("<span class=\"hidden_elem\" id=\"u_0_1\">")) {
					String result = inLine.substring(inLine.indexOf("u_0_1") + 7);
					result = result.substring(0, result.indexOf(".<"));
					// turn into char array

					// cut everything before the first int in the string
					char[] temp = result.toCharArray();
					for (int i = 0; i < temp.length; i++) {
						if (!Character.isLetter(temp[i]) && !Character.toString(temp[i]).matches(" ")) {
							result = result.substring(i);
							break;
						}
					}

					// cut everything from the next white space onwards
					temp = result.toCharArray();
					for (int i = 0; i < result.length(); i++) {
						if (Character.toString(temp[i]).matches(" ")) {
							result = result.substring(0, i);
							break;
						}
					}
	
					return result;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

		return urlIn;
	}
	// get list of FaceBook page URLs from search string
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
	} //-end-method-//


	public static int searchAndPost(String logfilename, String filename, String keyword, String app_access_token)
			throws MalformedURLException, IOException{

		ArrayList<String> log = new ArrayList<String>();
		ArrayList<String> picURLs = new ArrayList<String>();
		ArrayList<String> pageIDs = new ArrayList<String>();
		ArrayList<String> postIDs = new ArrayList<String>();
		pageIDs = ScrapeFBSearch(keyword, app_access_token);
		
		String newpostid;
		for (int i = 0; i < pageIDs.size(); i++) {
			System.out.println(pageIDs.get(i));
			newpostid = "";
			newpostid = getPageFeed(pageIDs.get(i), app_access_token);
//			System.out.println( "adding postID: " +  newpostid );
			postIDs.add( newpostid );
		}

		// get list of images
		try {
			System.out.println(filename);
			// Prepare to read from the file, using a Scanner object
			File file = new File(filename);
			Scanner in = new Scanner(file);

			// Read each line until end of file is reached
			while (in.hasNextLine()) {
				picURLs.add(in.nextLine());
			} // end while
			in.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("File not found");

		}

		// open log
		try {

			// Prepare to read from the file, using a Scanner object
			File file = new File(logfilename);
			Scanner in = new Scanner(file);

			// Read each line until end of file is reached
			while (in.hasNextLine()) {
				log.add(in.nextLine());
			} // end while
			in.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("File not found");

		}

		Random randPaul = new Random();
		String picURL;
		int count = 0;
		
		// loop through postIDs posting random image
		for (int i = 0; i < postIDs.size(); i++) {
			// check log
			if (log.contains(postIDs.get(i))) {
				System.out.println("Already posted on thread. ( " + postIDs.get(i) + " ) Skipping.");
				continue;
			} else {
				System.out.println("\nPosting on " + postIDs.get(i));
				picURL = picURLs.get(randPaul.nextInt(picURLs.size())) + ".jpg";
				System.out.println("with picture: " + picURL);
				System.out.println("on post: " + postIDs.get(i));

				if( ClientPost.PostComment(app_access_token, postIDs.get(i), picURL, "") ){
					count++;
				}
				System.out.println();
				// add to log
				log.add(postIDs.get(i));
			}
		}

		// write new logging information
		File logFile = new File(logfilename);
		BufferedWriter out = new BufferedWriter(new FileWriter(logFile));
		// loop through logArray
		for (int i = 0; i < log.size(); i++) {
			out.write(log.get(i));
			if (i != log.size()) {
				out.newLine();
			}
		} // end for loop
		out.close();
		System.out.println();
		return count;
	}
}
