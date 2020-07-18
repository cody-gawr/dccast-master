package notq.dccast.util;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import notq.dccast.api.APIClient;
import notq.dccast.model.vod.VodResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import timber.log.Timber;

public class FileUploader {

  public FileUploaderCallback fileUploaderCallback;
  public int uploadIndex = -1;
  private File[] files;
  private String uploadURL = "";
  private long totalFileLength = 0;
  private long totalFileUploaded = 0;
  private String filekey = "";
  private UploadInterface uploadInterface;
  private VodResponse[] responses;

  private Call<VodResponse> currentCall;

  private Handler handler;
  private Runnable runnable;

  public FileUploader() {
    uploadInterface = APIClient.getVodClient().create(UploadInterface.class);
  }

  public void uploadFiles(String url, String filekey, File[] files,
      FileUploaderCallback fileUploaderCallback) {
    this.fileUploaderCallback = fileUploaderCallback;
    this.files = files;
    this.uploadIndex = -1;
    this.uploadURL = url;
    this.filekey = filekey;
    totalFileUploaded = 0;
    totalFileLength = 0;
    uploadIndex = -1;
    responses = new VodResponse[files.length];
    for (int i = 0; i < files.length; i++) {
      totalFileLength = totalFileLength + files[i].length();
    }
    uploadNext();
  }

  private void uploadNext() {
    if (files.length > 0) {
      if (uploadIndex != -1) {
        totalFileUploaded = totalFileUploaded + files[uploadIndex].length();
      }
      uploadIndex++;
      if (uploadIndex < files.length) {
        uploadSingleFile(uploadIndex);
      } else {
        fileUploaderCallback.onFinish(responses);
      }
    } else {
      fileUploaderCallback.onFinish(responses);
    }
  }

  private void uploadSingleFile(final int index) {
    PRRequestBody fileBody = new PRRequestBody(files[index]);
    MultipartBody.Part filePart =
        MultipartBody.Part.createFormData(filekey, files[index].getName(), fileBody);
    currentCall = uploadInterface.uploadFile(uploadURL, filePart);

    currentCall.enqueue(new Callback<VodResponse>() {
      @Override
      public void onResponse(Call<VodResponse> call, retrofit2.Response<VodResponse> response) {
        if (response.isSuccessful()) {
          VodResponse jsonElement = response.body();
          responses[index] = jsonElement;
        } else {
          responses[index] = null;
        }
        uploadNext();
      }

      @Override
      public void onFailure(Call<VodResponse> call, Throwable t) {
        Timber.e("FILE UPLOAD ERROR: " + t.getMessage());
        fileUploaderCallback.onError();
        cancelRequest();
        call.cancel();
      }
    });
  }

  public void cancelRequest() {
    if (currentCall != null) {
      currentCall.cancel();
    }

    if (handler != null && runnable != null) {
      handler.removeCallbacks(runnable);
    }
  }

  private interface UploadInterface {
    @Multipart
    @POST
    Call<VodResponse> uploadFile(@Url String url, @Part MultipartBody.Part file);
  }

  public interface FileUploaderCallback {
    void onError();

    void onFinish(VodResponse[] responses);

    void onProgressUpdate(long currentpercent, long totalpercent, long filenumber);
  }

  public class PRRequestBody extends RequestBody {
    private static final int DEFAULT_BUFFER_SIZE = 2048;
    private File mFile;

    public PRRequestBody(final File file) {
      mFile = file;
    }

    @Override
    public MediaType contentType() {
      // i want to upload only images
      return MediaType.parse("video/*");
    }

    @Override
    public long contentLength() throws IOException {
      return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
      long fileLength = mFile.length();
      byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
      FileInputStream in = new FileInputStream(mFile);
      long uploaded = 0;

      try {
        int read;
        handler = new Handler(Looper.getMainLooper());
        Timber.e("LOADING BYTE LENGTH: " + buffer.length);
        while ((read = in.read(buffer)) != -1) {

          // update progress on UI thread
          //if (runnable != null && handler != null) {
          //  handler.removeCallbacks(runnable);
          //}
          runnable = new ProgressUpdater(uploaded, fileLength);
          handler.post(runnable);
          uploaded += read;

          //Source source = Okio.source(mFile);
          //sink.writeAll(source);

          sink.write(buffer, 0, read);
        }
      } catch (NumberFormatException ex) {
        Timber.e("FILE UPLOAD ERROR: NUMBER FORMAT- " + ex.getMessage());
      } catch (Exception ex) {
        Timber.e("FILE UPLOAD ERROR: " + ex.getMessage());
      } finally {
        in.close();
      }
    }
  }

  private class ProgressUpdater implements Runnable {
    private long mUploaded;
    private long mTotal;

    public ProgressUpdater(long uploaded, long total) {
      mUploaded = uploaded;
      mTotal = total;
    }

    @Override
    public void run() {
      try {
        long current_percent = (long) (100 * mUploaded / (float) mTotal);
        long total_percent =
            (long) (100 * (totalFileUploaded + mUploaded) / (float) totalFileLength);
        if (currentCall == null || currentCall.isCanceled()) {
          return;
        }
        fileUploaderCallback.onProgressUpdate(current_percent, total_percent, uploadIndex + 1);
      } catch (Exception ex) {
        Timber.e("FileUploader ProgressUpdater failed: " + ex.getMessage());
      }
    }
  }
}