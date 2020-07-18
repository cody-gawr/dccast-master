package notq.dccast.screens.home.interfaces;

import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelVideo;

public interface HomeChildFragmentListener {
  void fragmentCollapsed();

  void fragmentExpanded();

  void fragmentClosed();

  void onLikeDislikeUpdated(ModelVideo video);

  void onLikeDislikeUpdated(ModelListHeader header);

  void onLeftMenuTabChanged(int tabPosition);

  void onMediaDeleted(ModelVideo modelVideo);

  void onMediaRemovedFromFavorite(ModelVideo modelVideo);
}