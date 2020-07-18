package notq.dccast.model;

import com.google.gson.annotations.SerializedName;

public class ModelBlockDivisionResult {
  @SerializedName("division")
  public String division;
  @SerializedName("is_block")
  public boolean isBlock;
  @SerializedName("block_msg")
  public String blockMessage;
  @SerializedName("block_end_date")
  public String blockEndDate;

  public String getDivision() {
    return division;
  }

  public void setDivision(String division) {
    this.division = division;
  }

  public boolean isBlock() {
    return isBlock;
  }

  public void setBlock(boolean block) {
    isBlock = block;
  }

  public String getBlockMessage() {
    return blockMessage;
  }

  public void setBlockMessage(String blockMessage) {
    this.blockMessage = blockMessage;
  }

  public String getBlockEndDate() {
    return blockEndDate;
  }

  public void setBlockEndDate(String blockEndDate) {
    this.blockEndDate = blockEndDate;
  }
}
