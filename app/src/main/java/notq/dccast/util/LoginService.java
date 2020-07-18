package notq.dccast.util;

import io.realm.Realm;
import notq.dccast.model.user.ModelUser;

public class LoginService {

  public static ModelUser getLoginUser() {
    Realm realm = Realm.getDefaultInstance();
    return realm.where(ModelUser.class).findFirst();
  }
}
