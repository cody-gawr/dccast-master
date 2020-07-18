package notq.dccast.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import notq.dccast.util.Url;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
  public static Retrofit getClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(10, TimeUnit.MINUTES)
        .connectTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build();

    Gson gson = new GsonBuilder()
        .setLenient()
        .create();

    return new Retrofit.Builder().baseUrl(Url.baseUrlWithPort)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build();
  }

  public static Retrofit getDCIdClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(10, TimeUnit.MINUTES)
        .connectTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build();

    return new Retrofit.Builder().baseUrl(Url.dcIdUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }

  public static Retrofit getMDCIdClient() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(10, TimeUnit.MINUTES)
        .connectTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .addInterceptor(interceptor)
        .build();

    return new Retrofit.Builder().baseUrl(Url.mobileDcIdUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();
  }

  public static Retrofit getVodClient() {
    //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient client = new OkHttpClient.Builder().readTimeout(60, TimeUnit.MINUTES)
        .connectTimeout(60, TimeUnit.MINUTES)
        .writeTimeout(60, TimeUnit.MINUTES)
        .retryOnConnectionFailure(true)
        //.addInterceptor(interceptor)
        .build();

    Gson gson = new GsonBuilder()
        .setLenient()
        .create();

    return new Retrofit.Builder().baseUrl(Url.hostUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build();
  }
}