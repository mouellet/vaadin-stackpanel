package org.vaadin.addons.stackpanel.client;

import com.vaadin.shared.communication.ServerRpc;

public interface StackPanelRpc extends ServerRpc {

    void setOpen(boolean open);

    void setToggleDownHtml(String toogleDownHtml);

    void setToggleUpHtml(String toogleUpHtml);

    void toggleClick();

    void toggleDisabledClick();
}
