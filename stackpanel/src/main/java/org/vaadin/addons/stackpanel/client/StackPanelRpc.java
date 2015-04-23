package org.vaadin.addons.stackpanel.client;

import com.vaadin.shared.communication.ServerRpc;

public interface StackPanelRpc extends ServerRpc {

    void setOpen(boolean open);
}
