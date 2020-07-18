package notq.dccast.screens.home.video_service;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.model.ModelVideo;

import static com.google.android.exoplayer2.C.VIDEO_SCALING_MODE_SCALE_TO_FIT;

public class ExoPlayer implements Player.EventListener {

  View mContent;
  ModelVideo videoItem;
  String videoPath;
  private Context mContext;
  private ProgressBar progressBar;
  private SimpleExoPlayerView exoPlayerView;
  private SimpleExoPlayer exoPlayer;

  private DataSource.Factory mediaDataSourceFactory;
  private DefaultTrackSelector trackSelector;
  private BandwidthMeter bandwidthMeter;
  private boolean isNeedUpdateProgress = false;
  private Runnable videoUpdateProgress = new Runnable() {
    @Override
    public void run() {
      notq.dccast.util.Util.getHandler().postDelayed(videoUpdateProgress, 100);

      float progress = exoPlayer.getCurrentPosition() / (float) exoPlayer.getDuration();
      updateProgress(progress);
    }
  };

  void setVideoItem(ModelVideo modelVideo) {
    this.videoItem = modelVideo;
    this.videoPath = this.videoItem.getVideoUrl("");
  }

  ModelVideo getVideoItem() {
    return this.videoItem;
  }

  void initVideoPlayer(Context context) {
    mContext = context;

    bandwidthMeter = new DefaultBandwidthMeter();
    mediaDataSourceFactory = new DefaultDataSourceFactory(mContext,
        Util.getUserAgent(mContext, "mediaPlayerSample"),
        (TransferListener) bandwidthMeter);

    LayoutInflater inflater =
        (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mContent = inflater.inflate(R.layout.layout_popup_player, null);
    TextView txtLiveTitle = mContent.findViewById(R.id.txt_live_title);
    if (videoItem != null) {
      txtLiveTitle.setText(videoItem.getTitle());
    }
    mContent.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        View toolbar = mContent.findViewById(R.id.toolbar);
        if (toolbar.getVisibility() == View.GONE) {
          toolbar.setVisibility(View.VISIBLE);
        } else {
          toolbar.setVisibility(View.GONE);
        }
      }
    });

    progressBar = mContent.findViewById(R.id.progress_bar);
    exoPlayerView = mContent.findViewById(R.id.video_player);
    exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
    exoPlayerView.requestFocus();

    initializePlayer();

        /*// Create an AdsLoader.
        mSdkFactory = ImaSdkFactory.getInstance();
        mAdsLoader = mSdkFactory.createAdsLoader(this);
        // Add listeners for when ads are loaded and for errors.
        mAdsLoader.addAdErrorListener(this);
        mAdsLoader.addAdsLoadedListener(new AdsLoader.AdsLoadedListener() {
            @Override
            public void onAdsManagerLoaded(AdsManagerLoadedEvent adsManagerLoadedEvent) {
                // Ads were successfully loaded, so get the AdsManager instance. AdsManager has
                // events for ad playback and errors.
                mAdsManager = adsManagerLoadedEvent.getAdsManager();

                // Attach event and error event listeners.
                mAdsManager.addAdErrorListener(DCPlayerActivity.this);
                mAdsManager.addAdEventListener(DCPlayerActivity.this);
                mAdsManager.init();

                hideVideoLoading();
            }
        });*/
  }

  private void initializePlayer() {

    TrackSelection.Factory videoTrackSelectionFactory =
        new AdaptiveTrackSelection.Factory(bandwidthMeter);
    trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
    exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
    exoPlayer.setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT);
    exoPlayer.addListener(this);
    exoPlayerView.setPlayer(exoPlayer);

    DataSource.Factory dataSourceFactory =
        new DefaultDataSourceFactory(Objects.requireNonNull(mContext),
            Util.getUserAgent(mContext, "DCCast"));

    MediaSource videoSource =
        new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            Uri.parse(videoPath));

    exoPlayer.prepare(videoSource);
    exoPlayerView.setVisibility(View.GONE);
  }

  void releasePlayer() {
    if (exoPlayer != null) {
      stopUpdateProgress();

      exoPlayer.release();
      exoPlayer = null;
      trackSelector = null;
    }
  }

  void hideCustomControl() {
  }

  private void showCustomControl() {
  }

  private void startUpdateProgress() {
    if (isNeedUpdateProgress) {
      notq.dccast.util.Util.getHandler().postDelayed(videoUpdateProgress, 100);
    }
  }

  private void stopUpdateProgress() {
    if (isNeedUpdateProgress) {
      notq.dccast.util.Util.getHandler().removeCallbacks(videoUpdateProgress);
    }
  }

  private void updateProgress(float progress) {
  }

  void seekToProgress(float position) {
    long positionToSet = (long) (exoPlayer.getDuration() * position);
    seekTo(positionToSet);
  }

  private long videoTime() {
    return exoPlayer.getCurrentPosition();
  }

  String timeString() {
    long videoTime = videoTime();
    int secs = (int) (videoTime / 1000);
    int mins = secs / 60;
    secs = secs % 60;

    String timeString = "";
    timeString += (mins < 10) ? ("0" + mins) : mins;
    timeString += ":";
    timeString += (secs < 10) ? ("0" + secs) : secs;

    return timeString;
  }

  private void seekTo(long position) {
    exoPlayer.seekTo(position);
  }

  private void showVideoLoading(boolean isForce) {
        /*if (isForce || !isAds)
            progressBar.setVisibility(View.VISIBLE);*/
  }

  private void hideVideoLoading() {
    progressBar.setVisibility(View.GONE);
  }

  void reloadVideo() {
    resetVideoPlayer();

    MediaSource mediaSource = new HlsMediaSource(Uri.parse(videoPath),
        mediaDataSourceFactory, null, null);
    exoPlayer.prepare(mediaSource);

    resumeVideo();
  }

  public void resumeVideo() {
    exoPlayerView.setVisibility(View.VISIBLE);
    exoPlayer.setPlayWhenReady(true);

    showCustomControl();
    startUpdateProgress();
  }

  public void pauseVideo() {
    exoPlayer.setPlayWhenReady(false);

    stopUpdateProgress();
  }

  private void resetVideoPlayer() {
    stopUpdateProgress();
    // pause video
    seekTo(0);
  }

  void setVolume(float volume) {
    if (volume < 0) {
      volume = 0;
    } else if (volume > 1) {
      volume = 1;
    }
    exoPlayer.setVolume(volume);
  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
  }

  @Override
  public void onLoadingChanged(boolean isLoading) {
    if (isLoading) {
      showVideoLoading(false);
    } else {
      hideVideoLoading();
    }
  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    switch (playbackState) {
      case Player.STATE_BUFFERING:
        showVideoLoading(false);
        break;
      case Player.STATE_ENDED:
        resetVideoPlayer();
        break;
      case Player.STATE_IDLE:
        break;
      case Player.STATE_READY:
        hideVideoLoading();
        break;
    }
  }

  @Override
  public void onRepeatModeChanged(int repeatMode) {
  }

  @Override
  public void onPlayerError(ExoPlaybackException error) {
  }

  @Override
  public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
  }
}
