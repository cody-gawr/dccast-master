package notq.dccast.model.live;

public class ModelOptions {
  private String title;
  private boolean selected;
  private int id;

  public ModelOptions(String title, boolean selected) {
    this.title = title;
    this.selected = selected;
  }

  public ModelOptions(int id, String title, boolean selected) {
    this.id = id;
    this.title = title;
    this.selected = selected;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }
}
