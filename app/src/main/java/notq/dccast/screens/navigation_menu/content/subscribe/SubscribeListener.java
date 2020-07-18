package notq.dccast.screens.navigation_menu.content.subscribe;

import notq.dccast.model.user.ModelUser;
import notq.dccast.model.user.subscribe.ModelSubscribeUser;

public interface SubscribeListener {
    void onUserSelected(ModelSubscribeUser user);
}
