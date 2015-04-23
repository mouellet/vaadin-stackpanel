package org.vaadin.addons.stackpanel;

import org.vaadin.addons.stackpanel.client.StackPanelRpc;
import org.vaadin.addons.stackpanel.client.StackPanelState;

import com.vaadin.server.AbstractExtension;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class StackPanel extends AbstractExtension {

    private ServerRpc rpc = new StackPanelRpc() {

        @Override
        public void setOpen(boolean open) {
            if (getState().isOpen() != open) {
                getState().setOpen(open);
            }
        }
    };

    protected StackPanel(Panel panel) {
        super();
        registerRpc(rpc);
        getState().setOpen(true);
        super.extend(panel);
    }

    public static StackPanel extend(Panel panel) {
        return new StackPanel(panel);
    }

    public void open() {
        getState().setOpen(true);
    }

    public void close() {
        getState().setOpen(false);
    }

    public boolean isOpen() {
        return getState().isOpen();
    }

    @Override
    protected StackPanelState getState() {
        return (StackPanelState) super.getState();
    }

    @Override
    public Class<? extends SharedState> getStateType() {
        return StackPanelState.class;
    }

}
