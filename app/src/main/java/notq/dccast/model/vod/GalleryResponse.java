package notq.dccast.model.vod;

import java.io.Serializable;

public class GalleryResponse implements Serializable {
  private String title;
  private String id;

  public GalleryResponse(String title, String id) {
    this.title = title;
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public String getId() {
    return id;
  }
}
