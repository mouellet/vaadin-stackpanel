package org.vaadin.addons.stackpanel;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.stackpanel.client.StackPanelRpc;
import org.vaadin.addons.stackpanel.client.StackPanelState;
import org.w3c.dom.ls.LSInput;

import com.vaadin.server.AbstractExtension;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class StackPanel extends AbstractExtension {
	
	public interface ToggleListener {
		public void toggleClick(StackPanel source);
	}
	
	private List<ToggleListener> listeners = new ArrayList<ToggleListener>();

    private ServerRpc rpc = new StackPanelRpc() {

        @Override
        public void setOpen(boolean open) {
            if (getState().isOpen() != open) {
                getState().setOpen(open);
            }
        }

		@Override
		public void setToggleDownHtml(String toogleDownHtml) {
			getState().setToggleDownHtml(toogleDownHtml);
			
		}

		@Override
		public void setToggleUpHtml(String toggleUpHtml) {
			getState().setToggleUpHtml(toggleUpHtml);
			
		}

		@Override
		public void toggleClick() {
			for (ToggleListener listener : listeners) {
				listener.toggleClick(StackPanel.this);
			}
		}
    };

    protected StackPanel(Panel panel) {
        super();
        registerRpc(rpc);
        getState().setOpen(true);
        getState().setToggleIconEnabled(true);
        //set standard toggle icons
        setToggleDownIcon(FontAwesome.PLUS);
        setToggleUpIcon(FontAwesome.MINUS);
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
    
    public void setToggleIconsEnabled(boolean areEnabled) {
		getState().setToggleIconEnabled(areEnabled);
	}

	public void setToggleDownIcon(FontAwesome toggleDownIcon) {
    	getState().setToggleDownHtml(toggleDownIcon.getHtml());
    }
    
    public void setToggleUpIcon(FontAwesome toggleUpIcon) {
    	getState().setToggleUpHtml(toggleUpIcon.getHtml());
    }
    
    public void addToggleListener(ToggleListener listener) {
    	listeners.add(listener);
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
