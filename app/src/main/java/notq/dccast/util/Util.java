package notq.dccast.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import notq.dccast.DCCastApplication;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.ads.ModelAds;
import notq.dccast.model.ads.ModelShowCategory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import timber.log.Timber;

public class Util {
  private static final int SECOND_MILLIS = 1000;
  private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
  private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
  private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
  private static Handler mHandler;
  public String VIDEO_CHOOSE_TYPE = "VOD";
  private Context context;

  public Util(Context context) {
    this.context = context;
  }

  public static String bytesToHexString(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte aByte : bytes) {
      String hex = Integer.toHexString(0xFF & aByte);
      if (hex.length() == 1) {
        sb.append('0');
      }
      sb.append(hex);
    }
    return sb.toString();
  }

  public static String checkNetworkStatus(final Context context) {

    String networkStatus = "";

    // Get connect mangaer
    final ConnectivityManager connMgr = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);

    // check for wifi
    final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

    // check for mobile data
    final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    if (wifi.isConnected()) {
      networkStatus = "wifi";
    } else if (mobile.isConnected()) {
      networkStatus = "mobileData";
    } else {
      networkStatus = "noNetwork";
    }

    return networkStatus;
  }

  public static int getNavigationDifference(Context context) {
    Point appUsableSize = getAppUsableScreenSize(context);
    Point realScreenSize = getRealScreenSize(context);

    if (appUsableSize.y < realScreenSize.y) {
      return realScreenSize.y - appUsableSize.y;
    }

    return 0;
  }

  public static boolean getNavigationBarSize(Context context) {
    Point appUsableSize = getAppUsableScreenSize(context);
    Point realScreenSize = getRealScreenSize(context);

    if (appUsableSize.y < realScreenSize.y) {
      return true;
    }

    return false;
  }

  public static Point getAppUsableScreenSize(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size;
  }

  public static Point getRealScreenSize(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();

    if (Build.VERSION.SDK_INT >= 17) {
      display.getRealSize(size);
    } else if (Build.VERSION.SDK_INT >= 14) {
      try {
        size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
        size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
      } catch (IllegalAccessException e) {
      } catch (InvocationTargetException e) {
      } catch (NoSuchMethodException e) {
      }
    }

    return size;
  }

  public static boolean hasSoftKeys(WindowManager windowManager) {
    Display d = windowManager.getDefaultDisplay();

    DisplayMetrics realDisplayMetrics = new DisplayMetrics();
    d.getRealMetrics(realDisplayMetrics);

    int realHeight = realDisplayMetrics.heightPixels;
    int realWidth = realDisplayMetrics.widthPixels;

    DisplayMetrics displayMetrics = new DisplayMetrics();
    d.getMetrics(displayMetrics);

    int displayHeight = displayMetrics.heightPixels;
    int displayWidth = displayMetrics.widthPixels;

    return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
  }

  public static float convertDpToPixel(float dp, Context context) {
    return dp * ((float) context.getResources().getDisplayMetrics().densityDpi
        / DisplayMetrics.DENSITY_DEFAULT);
  }

  public static float convertPixelsToDp(float px, Context context) {
    return px / ((float) context.getResources().getDisplayMetrics().densityDpi
        / DisplayMetrics.DENSITY_DEFAULT);
  }

  public static Handler getHandler() {
    if (mHandler == null) {
      mHandler = new Handler();
    }

    return mHandler;
  }

  public static String getDateString(String date) {
    long time = 0;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      time = sdf.parse(date).getTime() + TimeZone.getDefault().getRawOffset();
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(time);
      return DateFormat.format("yyyy/MM/dd HH:mm:ss", calendar).toString();
    } catch (Exception ignored) {
      return "";
    }
  }

  public static long getTimeDiffStamp(String date) {
    long time = 0;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      time = sdf.parse(date).getTime() + TimeZone.getDefault().getRawOffset();
    } catch (Exception ex) {
      time = 0;
    }
    Calendar calendar = Calendar.getInstance();
    long now = calendar.getTimeInMillis();
    return (now - time) / 1000;
  }

  public static String getPhotoUrl(String imgUrl) {
    if (imgUrl == null) {
      return null;
    }
    if (!imgUrl.startsWith(Url.baseUrlWithPort)) {
      return Url.baseUrlWithPort + "/api/" + (imgUrl.startsWith("/") ? "" : "/") + imgUrl;
    }

    return getValidateUrl(imgUrl);
  }

  public static String getFormattedNumber(int number) {
    double m = Double.parseDouble(String.valueOf(number));
    if (String.valueOf(number).length() > 4) {
      return (number / 1000) + "K";
    }
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    return formatter.format(m);
  }

  public static String getFormattedNumber(String number) {
    double m = Double.parseDouble(number);
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    return formatter.format(m);
  }

  public static String getFormattedNumber(Double number) {
    DecimalFormat formatter = new DecimalFormat("###,###,###.##");
    return formatter.format(number);
  }

  public static ModelVideo getPopUpVideo() {
    Gson gson = new Gson();
    String json = Prefs.getString("serviceMedia", "");
    ModelVideo media = null;
    try {
      media = gson.fromJson(json, ModelVideo.class);
    } catch (Exception error) {
      Timber.e("ERROR: " + error.getMessage());
    }
    return media;
  }

  public static void setPopUpVideo(ModelVideo media) {
    Gson gson = new Gson();
    String json = gson.toJson(media);
    Prefs.putString("serviceMedia", json);
  }

  public static Bitmap setReducedImageSize(int targetWidth, int targetHeight,
      String mCurrentPhotoPath) {
    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
    bmOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
    int cameraWidth = bmOptions.outWidth;
    int cameraHeight = bmOptions.outHeight;

    int scaleFactor = Math.min(cameraWidth / targetWidth, cameraHeight / targetHeight);
    bmOptions.inSampleSize = scaleFactor;
    bmOptions.inJustDecodeBounds = false;

    return BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
  }

  public static Bitmap rotateImageIfRequired(Bitmap bitmap, String mCurrentPhotoPath) {
    ExifInterface exifInterface = null;
    try {
      exifInterface = new ExifInterface(mCurrentPhotoPath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED);
    Matrix matrix = new Matrix();
    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90: {
        matrix.setRotate(90);
        break;
      }

      case ExifInterface.ORIENTATION_ROTATE_180: {
        matrix.setRotate(180);
        break;
      }

      case ExifInterface.ORIENTATION_ROTATE_270: {
        matrix.setRotate(270);
        break;
      }
    }

    Bitmap rotatedBitmap =
        Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    return rotatedBitmap;
  }

  public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path)
      throws IOException {
    ExifInterface ei = new ExifInterface(image_absolute_path);
    String orientString = ei.getAttribute(ExifInterface.TAG_ORIENTATION);
    int orientation =
        orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_UNDEFINED;

    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90:
        return rotate(bitmap, 90);

      case ExifInterface.ORIENTATION_ROTATE_180:
        return rotate(bitmap, 180);

      case ExifInterface.ORIENTATION_ROTATE_270:
        return rotate(bitmap, 270);

      case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
        return flip(bitmap, true, false);

      case ExifInterface.ORIENTATION_FLIP_VERTICAL:
        return flip(bitmap, false, true);

      case ExifInterface.ORIENTATION_UNDEFINED: {
        return rotateUsingWidth(bitmap);
      }

      default:
        return bitmap;
    }
  }

  /**
   * Bitmap rotate in degrees
   *
   * @param bitmap Bitmap rotation
   * @param degrees Rotation degree
   * @return rotated bitmap
   */
  private static Bitmap rotate(Bitmap bitmap, float degrees) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degrees);
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  }

  private static Bitmap rotateUsingWidth(Bitmap bitmap) {
    Matrix matrix = new Matrix();
    int degrees = 0;
    if (bitmap.getWidth() > bitmap.getHeight()) {
      degrees = 90;
    }

    matrix.postRotate(degrees);
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  }

  /**
   * Flip bitmap degree
   *
   * @param bitmap Bitmap rotation
   * @param horizontal Bitmap horizontal flip
   * @param vertical Bitmap vertical flip
   * @return flipped bitmap
   */
  private static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
    Matrix matrix = new Matrix();
    matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  }

  /**
   * Save bitmap to file
   *
   * @param context - Activity context
   * @param b - Bitmap
   * @param name - Image file nam
   * @param extension - Image file extension
   * @return bitmap
   */
  public static Bitmap saveImage(Context context, Bitmap b, String name, String extension) {
    name = name + "." + extension;
    FileOutputStream out;
    try {
      out = context.openFileOutput(name, Context.MODE_PRIVATE);
      b = Bitmap.createScaledBitmap(b, 300, b.getHeight() * 300 / b.getWidth(), false);
      b.compress(Bitmap.CompressFormat.JPEG, 100, out);
      out.close();
      return b;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return b;
  }

  /**
   * Get file path with uri
   *
   * @param uri file uri
   * @return file string
   */
  public static String getPath(Activity activity, Uri uri) {
    String[] projection = {MediaStore.Images.Media.DATA};
    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
    if (cursor != null) {
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      return cursor.getString(column_index);
    } else {
      return null;
    }
  }

  public static boolean isNetworkConnected(Context context) {
    try {
      if (context == null) return false;
      ConnectivityManager cm =
          (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo ni = null;
      if (cm != null) {
        ni = cm.getActiveNetworkInfo();
      }

      return ni != null;
    } catch (Exception e) {
      return false;
    }
  }

  public static String getTimeAgo(long time) {
    if (time < 1000000000000L) {
      time *= 1000;
    }

    long now = System.currentTimeMillis();
    if (time > now || time <= 0) {
      return null;
    }

    final long diff = now - time;
    if (diff < MINUTE_MILLIS) {
      return "now";
    } else if (diff < 2 * MINUTE_MILLIS) {
      return "minute ago";
    } else if (diff < 50 * MINUTE_MILLIS) {
      return diff / MINUTE_MILLIS + " minute agp";
    } else if (diff < 90 * MINUTE_MILLIS) {
      return "hour ago";
    } else if (diff < 24 * HOUR_MILLIS) {
      return diff / HOUR_MILLIS + " " + "hour ago";
    } else if (diff < 48 * HOUR_MILLIS) {
      return "yesterday";
    } else {
      return diff / DAY_MILLIS + " days ago";
    }
  }

  public static String difference(String closeDate) {
    DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime createdDateTime = dateFormat.parseDateTime(getCurrentDate());
    DateTime closeDateTime = dateFormat.parseDateTime(closeDate);
    Interval interval = new Interval(createdDateTime.getMillis(), closeDateTime.getMillis());
    Period period = interval.toPeriod();
    return period.getDays() + "-" + period.getHours() + "-" + period.getMinutes();
  }

  private static String getCurrentDate() {
    DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    DateTime dateTime = new DateTime();
    return dateTime.toString(dateFormat);
  }

  public static String getValidateUrl(String url) {
    String returnUrl = url;
    if (returnUrl != null) {
      returnUrl = returnUrl.replaceAll("%5C", "/");
      returnUrl = returnUrl.replace("\\", "/");
      returnUrl = returnUrl.replaceAll("%20", "");
      returnUrl = returnUrl.replaceAll(" ", "");
      returnUrl = returnUrl.trim();
    }
    return returnUrl;
  }

  public static ModelAds getAds(String locateId) {
    if (DCCastApplication.adsList == null || DCCastApplication.adsList.size() == 0) {
      return null;
    }
    for (ModelAds modelAds : DCCastApplication.adsList) {
      if (modelAds.getLocate() != null && modelAds.getLocate()
          .getLocateId()
          .equalsIgnoreCase(locateId)) {
        return modelAds;
      }
    }
    return null;
  }

  public static ModelAds getAds(String locateId, int categoryId) {
    if (DCCastApplication.adsList == null || DCCastApplication.adsList.size() == 0) {
      return null;
    }
    for (ModelAds modelAds : DCCastApplication.adsList) {
      if (modelAds.getLocate() != null && modelAds.getLocate()
          .getLocateId()
          .equalsIgnoreCase(locateId)) {
        if (modelAds.getShowCategories() != null && categoryId > 0) {
          for (ModelShowCategory showCategory : modelAds.getShowCategories()) {
            if (showCategory.getId() == categoryId) {
              return modelAds;
            }
          }
        } else {
          return modelAds;
        }
      }
    }
    return null;
  }

  @SuppressLint("NewApi")
  public int getScreenWidth() {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = Objects.requireNonNull(wm).getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    return size.x;
  }

  public int dpFromPx(int px) {
    return (int) (px / context.getResources().getDisplayMetrics().density);
  }

  public int pxFromDp(int dp) {
    return (int) (dp * context.getResources().getDisplayMetrics().density);
  }

  public String milliSecondsToTimer(long milliseconds) {
    String finalTimerString = "";
    String secondsString = "";

    // Convert total duration into time
    int hours = (int) (milliseconds / (1000 * 60 * 60));
    int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
    int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
    // Add hours if there
    if (hours > 0) {
      finalTimerString = hours + ":";
    }

    // Prepending 0 to seconds if it is one digit
    if (seconds < 10) {
      secondsString = "0" + seconds;
    } else {
      secondsString = "" + seconds;
    }

    finalTimerString = finalTimerString + minutes + ":" + secondsString;

    // return timer string
    return finalTimerString;
  }

  public void openKeyboard(EditText editText, Activity activity) {
    InputMethodManager inputMethodManager =
        (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
    View view = activity.getCurrentFocus();
    if (view == null) {
      view = new View(activity);
    }

    inputMethodManager.showSoftInput(editText, 0);

    //inputMethodManager.toggleSoftInputFromWindow(
    //    view.getWindowToken(),
    //    InputMethodManager.SHOW_FORCED, 0);
  }

  public void hideKeyboard(Activity activity) {
    InputMethodManager imm =
        (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = activity.getCurrentFocus();
    if (view == null) {
      view = new View(activity);
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }
}
