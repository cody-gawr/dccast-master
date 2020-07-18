package notq.dccast.screens.home.interfaces;

import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;

public interface LikeDislikeChangeListener {
  void onLikeDislikeUpdated(ModelVideo video);

  void onLikeDislikeUpdated(ModelListHeader header);
}
