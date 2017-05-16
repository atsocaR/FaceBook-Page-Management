# FaceBook-Page-Management
Java Classes to automate management of FaceBook "like" pages using FaceBook's Graph API

# function reference list

public static void postScheduledText(String pageID, String app_access_token, String message, Long publishTime)

public static void postImage(String pageID, String app_access_token, String picURL, String message)

public static void postScheduledImage(String pageID, String app_access_token, String picURL, String message, Long publishTime)

public static boolean PostComment( String app_access_token, String postID, String picURL, String message)

public static String getPageFeed(String pageID, String access_token)

public static String getPageLikeCount(String urlIn)

public static ArrayList<String> ScrapeFBSearch(String searchTerm, String access_token)

public static int searchAndPost(String logfilename, String filename, String keyword, String app_access_token)

