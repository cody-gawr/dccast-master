/**
 * This is sample code provided by Wowza Media Systems, LLC.  All sample code is intended to be a
 * reference for the purpose of educating developers, and is not intended to be used in any
 * production environment.
 *
 * IN NO EVENT SHALL WOWZA MEDIA SYSTEMS, LLC BE LIABLE TO YOU OR ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF
 * THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF WOWZA MEDIA SYSTEMS, LLC HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * WOWZA MEDIA SYSTEMS, LLC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. ALL CODE PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". WOWZA MEDIA SYSTEMS, LLC HAS NO OBLIGATION TO PROVIDE MAINTENANCE,
 * SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *
 * © 2015 – 2019 Wowza Media Systems, LLC. All rights reserved.
 */

package notq.dccast.screens.navigation_menu.live;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.wowza.gocoder.sdk.api.logging.WOWZLog;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerView extends AppCompatTextView {

  final public static long DEFAULT_REFRESH_INTERVAL = 1000L;

  private long mTimerStart = 0L;

  private ScheduledExecutorService mTimerThread = null;
  private TimerProvider mDefaultTimerProvider;
  private TimerProvider mTimerProvider;

  private long durationTotalSeconds = 0L;

  public TimerView(Context context) {
    super(context);
    init();
  }

  public TimerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  @SuppressLint("SetTextI18n") private void init() {
    mTimerProvider = null;
    mDefaultTimerProvider = new TimerProvider() {
      @Override public long getTimecode() {
        return System.currentTimeMillis() - mTimerStart;
      }

      @Override public long getDuration() {
        return -1L;
      }
    };

    setText("00:00:00");
  }

  public void startTimer() {
    startTimer(DEFAULT_REFRESH_INTERVAL);
  }

  @SuppressLint("SetTextI18n") public synchronized void startTimer(long refreshInterval) {
    if (mTimerThread != null) {
      return;
    }

    if (mTimerProvider == null) mTimerProvider = mDefaultTimerProvider;

    setText("00:00:00");

    mTimerStart = System.currentTimeMillis();
    mTimerThread = Executors.newSingleThreadScheduledExecutor();
    mTimerThread.scheduleWithFixedDelay(
        () -> new Handler(Looper.getMainLooper()).post(() -> setText(genTimerDisplay())),
        refreshInterval, refreshInterval, TimeUnit.MILLISECONDS);

    setVisibility(VISIBLE);
  }

  @SuppressLint("DefaultLocale") private String genTimerDisplay() {
    String formatStr;

    long timecodeMs = mTimerProvider.getTimecode();
    long durationMs = mTimerProvider.getDuration();

    long timecodeTotalSeconds = timecodeMs / 1000L;
    durationTotalSeconds = timecodeTotalSeconds;
    long timecodeHours = timecodeTotalSeconds / 3600L, timecodeMinutes =
        (timecodeTotalSeconds / 60L) % 60L, timecodeSeconds = timecodeTotalSeconds % 60L;

    if (durationMs > 0L && durationMs >= timecodeMs) {
      durationTotalSeconds = durationMs / 1000L;
      long durationHours = durationTotalSeconds / 3600L, durationMinutes =
          (durationTotalSeconds / 60L) % 60L, durationSeconds = durationTotalSeconds % 60L;

      formatStr = String.format("%02d:%02d:%02d / %02d:%02d:%02d", timecodeHours, timecodeMinutes,
          timecodeSeconds, durationHours, durationMinutes, durationSeconds);
    } else {
      formatStr = String.format("%02d:%02d:%02d", timecodeHours, timecodeMinutes, timecodeSeconds);
    }

    return formatStr;
  }

  @SuppressLint("SetTextI18n") public synchronized void stopTimer() {
    if (mTimerThread == null) return;

    WOWZLog.debug("timer stopped");
    mTimerThread.shutdown();
    mTimerThread = null;

    setVisibility(INVISIBLE);
    setText("00:00:00");
  }

  public synchronized boolean isRunning() {
    return (mTimerThread != null);
  }

  public long getDuration() {
    return durationTotalSeconds;
  }

  public interface TimerProvider {
    long getTimecode();

    long getDuration();
  }
}
