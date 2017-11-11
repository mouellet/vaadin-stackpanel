package org.vaadin.addons.stackpanel.client;

import com.vaadin.shared.communication.SharedState;

@SuppressWarnings("serial")
public class StackPanelState extends SharedState {

    private boolean open;
    private boolean toggleEnabled = true;
    private boolean toggleIconEnabled;
    private String toggleDownHtml;
    private String toggleUpHtml;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setToggleDownHtml(String toggleDownHtml) {
        this.toggleDownHtml = toggleDownHtml;
    }

    public String getToggleDownHtml() {
        return toggleDownHtml;
    }

    public void setToggleUpHtml(String toggleDownHtml) {
        this.toggleUpHtml = toggleDownHtml;
    }

    public String getToggleUpHtml() {
        return toggleUpHtml;
    }

    public boolean isToggleIconEnabled() {
        return toggleIconEnabled;
    }

    public void setToggleIconEnabled(boolean toggleIconEnabled) {
        this.toggleIconEnabled = toggleIconEnabled;
    }

    public boolean isToggleEnabled() {
        return toggleEnabled;
    }

    public void setToggleEnabled(boolean toggleEnabled) {
        this.toggleEnabled = toggleEnabled;
        setToggleIconEnabled(toggleEnabled);
    }
}
