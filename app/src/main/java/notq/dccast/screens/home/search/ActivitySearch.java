package notq.dccast.screens.home.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import notq.dccast.R;
import notq.dccast.databinding.ActivitySearchBinding;
import notq.dccast.model.ModelListHeader;
import notq.dccast.model.ModelSearchHistory;
import notq.dccast.model.ModelVideo;
import notq.dccast.model.user.ModelUser;
import notq.dccast.screens.BaseActivity;
import notq.dccast.screens.home.adapter.AdapterSearchHistory;
import notq.dccast.screens.home.adapter.AdapterSearchPager;
import notq.dccast.screens.home.interfaces.HomeChildFragmentListener;
import notq.dccast.screens.home.interfaces.SearchAdapterListener;
import notq.dccast.util.LoginService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivitySearch extends BaseActivity
    implements View.OnClickListener, HomeChildFragmentListener, SearchAdapterListener {
  private ActivitySearchBinding binding;
  private AdapterSearchPager pagerAdapter;
  private AdapterSearchHistory historyAdapter;
  private Realm realm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    realm = Realm.getDefaultInstance();

    initToolbar();
    initViewPager();
    initSearchHistoryRecyclerView();
  }

  public void moveToBackground() {
    boolean sentAppToBackground = moveTaskToBack(true);

    if (!sentAppToBackground) {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      startActivity(intent);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }

  private void initToolbar() {
    binding.toolbarContainer.searchField.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
        if (Objects.requireNonNull(binding.toolbarContainer.searchField.getText()).length() > 0) {
          binding.toolbarContainer.btnClear.setVisibility(View.VISIBLE);
        } else {
          binding.toolbarContainer.btnClear.setVisibility(View.GONE);
        }

        if (!binding.searchHistoryRecyclerView.isShown()) {
          binding.searchHistoryRecyclerView.setVisibility(View.VISIBLE);
          binding.searchPager.setVisibility(View.GONE);
        }
      }

      @Override public void afterTextChanged(Editable editable) {

      }
    });

    binding.toolbarContainer.searchField.setOnEditorActionListener(
        (textView, actionId, keyEvent) -> {
          if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (isValid()) {
              sendSearchRequest(false,
                  Objects.requireNonNull(binding.toolbarContainer.searchField.getText())
                      .toString());
            }

            return true;
          }
          return false;
        });

    binding.toolbarContainer.btnClear.setOnClickListener(this);
    binding.toolbarContainer.btnBack.setOnClickListener(this);
    binding.toolbarContainer.searchedField.setOnClickListener(this);
  }

  private void initViewPager() {
    pagerAdapter = new AdapterSearchPager(getSupportFragmentManager());
    binding.searchPager.setOffscreenPageLimit(2);
    binding.searchPager.setAdapter(pagerAdapter);
    binding.tabLayout.setupWithViewPager(binding.searchPager);
  }

  private void initSearchHistoryRecyclerView() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    linearLayoutManager.setReverseLayout(false);
    binding.searchHistoryRecyclerView.setLayoutManager(linearLayoutManager);

    historyAdapter = new AdapterSearchHistory(getApplicationContext(), this);
    binding.searchHistoryRecyclerView.setAdapter(historyAdapter);
    setHistoryItemsToRecyclerView(true);
  }

  private void sendSearchRequest(boolean keywordFromHistory, String keyword) {
    if (!keywordFromHistory) {
      ModelUser loginUser = LoginService.getLoginUser();
      if (loginUser != null) {
        if (!loginUser.getStopRecentSearch()) {
          saveKeyword(keyword);
        }
      } else {
        saveKeyword(keyword);
      }
    }

    ((SearchResultPage) pagerAdapter.getItem(0)).searchRequest("VOD", keyword, true);
    ((SearchResultPage) pagerAdapter.getItem(1)).searchRequest("LIVE", keyword, true);

    hideKeyboard();

    binding.toolbarContainer.searchedField.setText(keyword);
    binding.toolbarContainer.searchField.setVisibility(View.GONE);
    binding.toolbarContainer.btnClear.setVisibility(View.GONE);
    binding.toolbarContainer.searchedField.setVisibility(View.VISIBLE);
    binding.searchHistoryRecyclerView.setVisibility(View.GONE);
    binding.searchPager.setVisibility(View.VISIBLE);
    binding.tabLayout.setVisibility(View.VISIBLE);
  }

  private void setHistoryItemsToRecyclerView(boolean needNotify) {
    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }

    List<ModelSearchHistory> modelSearchHistories =
        realm.where(ModelSearchHistory.class).findAll().sort("id", Sort.DESCENDING);
    historyAdapter.setModelSearchHistories(modelSearchHistories);
    if (needNotify) {
      historyAdapter.notifyDataSetChanged();
    }

    realm.commitTransaction();
  }

  private boolean isValid() {
    if (Objects.requireNonNull(binding.toolbarContainer.searchField.getText()).length() == 0) {
      Toast.makeText(getApplicationContext(), getString(R.string.validate_search_value),
          Toast.LENGTH_SHORT).show();
      return false;
    } else {
      return true;
    }
  }

  private void saveKeyword(String keyword) {
    if (!keyword.isEmpty()) {
      if (!realm.isInTransaction()) {
        realm.beginTransaction();
      }

      List<ModelSearchHistory> historyItemList = realm.where(ModelSearchHistory.class).findAll();

      boolean canSave = false;
      int nextId = 0;

      if (historyItemList.isEmpty()) {
        canSave = true;
      } else {
        List<ModelSearchHistory> conflictedHistoryItemList =
            realm.where(ModelSearchHistory.class).equalTo("keyword", keyword).findAll();

        if (conflictedHistoryItemList.isEmpty()) {
          canSave = true;
        }

        Number number = realm.where(ModelSearchHistory.class).max("id");
        nextId = Objects.requireNonNull(number).intValue() + 1;
      }

      if (canSave) {
        ModelSearchHistory modelSearchHistory = new ModelSearchHistory();
        modelSearchHistory.setId(nextId);
        modelSearchHistory.setKeyword(keyword);
        realm.copyToRealm(modelSearchHistory);
      }

      realm.commitTransaction();
    }
  }

  private void hideKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
  }

  private void showKeyboard() {
    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_clear: {
        Objects.requireNonNull(binding.toolbarContainer.searchField.getText()).clear();
        break;
      }

      case R.id.btn_back: {
        hideKeyboard();
        onBackPressed();
        break;
      }

      case R.id.searched_field: {
        binding.toolbarContainer.searchedField.setVisibility(View.GONE);
        binding.toolbarContainer.searchField.setVisibility(View.VISIBLE);
        binding.toolbarContainer.btnClear.setVisibility(View.VISIBLE);
        binding.toolbarContainer.searchField.requestFocus();
        binding.searchPager.setVisibility(View.GONE);
        binding.tabLayout.setVisibility(View.GONE);
        binding.searchHistoryRecyclerView.setVisibility(View.VISIBLE);

        setHistoryItemsToRecyclerView(true);
        showKeyboard();
        break;
      }
    }
  }

  @Override public void onBackPressed() {
    if (binding.searchHistoryRecyclerView.isShown()) {
      binding.searchPager.setVisibility(View.VISIBLE);
      binding.searchHistoryRecyclerView.setVisibility(View.GONE);
      hideKeyboard();
    } else {
      finish();
    }
  }

  @Override public void fragmentCollapsed() {

  }

  @Override public void fragmentExpanded() {

  }

  @Override public void fragmentClosed() {

  }

  @Override public void onLikeDislikeUpdated(ModelVideo video) {
    SearchResultPage searchResultPage =
        pagerAdapter.searchResultPages.get(binding.searchPager.getCurrentItem());

    if (searchResultPage.videoAdapter.getVideos().size() > 0) {
      ArrayList<ModelVideo> videos = searchResultPage.videoAdapter.getVideos();

      for (int i = 0; i < videos.size(); i++) {
        ModelVideo video_ = videos.get(i);

        if (video_.getId() == video.getId()) {
          searchResultPage.videoAdapter.getVideos().set(i, video);
          searchResultPage.videoAdapter.notifyItemChanged(i);
        }
      }
    }
  }

  @Override public void onLikeDislikeUpdated(ModelListHeader header) {

  }

  @Override public void onLeftMenuTabChanged(int tabPosition) {

  }

  @Override public void onMediaDeleted(ModelVideo modelVideo) {
    for (SearchResultPage searchResultPage : pagerAdapter.getSearchResultPages()) {
      searchResultPage.videoAdapter.removeVideo(modelVideo);
    }
  }

  @Override public void onMediaRemovedFromFavorite(ModelVideo modelVideo) {

  }

  @Override public void onKeywordSelected(String keyword) {
    sendSearchRequest(true, keyword);
  }

  @Override public void onKeywordRemoveClicked(ModelSearchHistory historyItem) {
    if (!realm.isInTransaction()) {
      realm.beginTransaction();
    }

    historyItem.deleteFromRealm();
    setHistoryItemsToRecyclerView(false);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
