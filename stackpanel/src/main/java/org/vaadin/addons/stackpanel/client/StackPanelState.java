package org.vaadin.addons.stackpanel.client;

import com.vaadin.shared.communication.SharedState;

@SuppressWarnings("serial")
public class StackPanelState extends SharedState {

    private boolean open;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

}
