package notq.dccast.util;

public class Url {
  //public static final String SERVER_IP = "117.52.89.224";
  //MONGOL SERVER
  public static final String SERVER_IP = "66.181.167.116";
  //SOLONGOS SERVER
  //public static final String SERVER_IP = "121.125.77.40";
  public static final String baseUrl = "http://" + SERVER_IP;
  public static final String baseUrlWithPort = baseUrl + ":522";
  //ZUVHUN IP HAYG BN, HTTP GEJ EHELJ BOLOHGUI
  public static final String wowzaUrl = SERVER_IP + ":8088";
  public static final String hostUrl = baseUrl + ":5050";
  public static final String nodeUrl = baseUrl;
  public static final String dcIdUrl = "https://dcid.dcinside.com";
  public static final String mobileDcIdUrl = "http://m.dcinside.com";

  public static String SHARE_URL = baseUrl + "/dc_live/";
}