package notq.dccast.model.vod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VodResponse {
  @SerializedName("fieldname")
  @Expose
  private String fieldname;
  @SerializedName("originalname")
  @Expose
  private String originalname;
  @SerializedName("encoding")
  @Expose
  private String encoding;
  @SerializedName("mimetype")
  @Expose
  private String mimetype;
  @SerializedName("destination")
  @Expose
  private String destination;
  @SerializedName("filename")
  @Expose
  private String filename;
  @SerializedName("path")
  @Expose
  private String path;
  @SerializedName("size")
  @Expose
  private Long size;

  public String getFieldname() {
    return fieldname;
  }

  public void setFieldname(String fieldname) {
    this.fieldname = fieldname;
  }

  public String getOriginalname() {
    return originalname;
  }

  public void setOriginalname(String originalname) {
    this.originalname = originalname;
  }

  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getMimetype() {
    return mimetype;
  }

  public void setMimetype(String mimetype) {
    this.mimetype = mimetype;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }
}
