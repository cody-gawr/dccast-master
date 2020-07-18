package notq.dccast.screens.home.interfaces;

import notq.dccast.model.ModelSearchHistory;

public interface SearchAdapterListener {
  void onKeywordSelected(String keyword);

  void onKeywordRemoveClicked(ModelSearchHistory historyItem);
}
