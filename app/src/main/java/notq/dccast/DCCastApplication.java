package notq.dccast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.pixplicity.easyprefs.library.Prefs;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;
import notq.dccast.model.ads.ModelAds;
import notq.dccast.model.category.CategoryItem;
import notq.dccast.util.Util;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class DCCastApplication extends MultiDexApplication {
  @SuppressLint("StaticFieldLeak")
  public static Util utils;
  public static List<CategoryItem> listCategoryItems = new RealmList<>();
  public static List<ModelAds> adsList = new ArrayList<>();
  public static String appId = "";
  public static String userId = "";
  public static String userNo = "";
  public static String videoUrl = "";
  public static int videoId = -1;
  public static boolean isLive = false;

  @Override
  public void onCreate() {
    super.onCreate();
    utils = new Util(getApplicationContext());
    Realm.init(this);
    Realm.setDefaultConfiguration(
        new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build());

    Timber.plant(new Timber.DebugTree());

    new Prefs.Builder().setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(getPackageName())
        .setUseDefaultSharedPreference(true)
        .build();

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/NotoSans-Regular.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build()
    );
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(newBase);
    MultiDex.install(this);
  }
}
