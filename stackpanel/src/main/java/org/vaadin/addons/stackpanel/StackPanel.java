package org.vaadin.addons.stackpanel;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.stackpanel.client.StackPanelRpc;
import org.vaadin.addons.stackpanel.client.StackPanelState;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.AbstractExtension;
import com.vaadin.server.FontIcon;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.Panel;

@SuppressWarnings("serial")
public class StackPanel extends AbstractExtension {

    public interface ToggleListener {
        void toggleClick(StackPanel source);
    }

    public interface ToggleDisabledClickListener {
        void toggleDisabledClick(StackPanel source);
    }

    public interface FocusListener {
        void focus(StackPanel source);
    }

    public interface BlurListener {
        void blur(StackPanel source);
    }

    private List<ToggleListener> listeners = new ArrayList<>();
    private List<ToggleDisabledClickListener> toggleDisabledClickListeners = new ArrayList<>();
    private List<FocusListener> focusListeners = new ArrayList<>();
    private List<BlurListener> blurListeners = new ArrayList<>();

    private ServerRpc rpc = new StackPanelRpc() {

        @Override
        public void setOpen(boolean open) {
            if (getState().isOpen() != open) {
                getState().setOpen(open);
            }
        }

        @Override
        public void toggleClick() {
            for (ToggleListener listener : listeners) {
                listener.toggleClick(StackPanel.this);
            }
        }

        @Override
        public void toggleDisabledClick() {
            for (ToggleDisabledClickListener listener : toggleDisabledClickListeners) {
                listener.toggleDisabledClick(StackPanel.this);
            }
        }

        @Override
        public void focus() {
            for (FocusListener focusListener : focusListeners) {
                focusListener.focus(StackPanel.this);
            }
        }

        @Override
        public void blur() {
            for (BlurListener blurListener : blurListeners) {
                blurListener.blur(StackPanel.this);
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

    };

    protected StackPanel(Panel panel) {
        super();
        registerRpc(rpc);
        getState().setOpen(true);
        getState().setToggleIconEnabled(true);
        // set standard toggle icons
        setToggleDownIcon(VaadinIcons.PLUS);
        setToggleUpIcon(VaadinIcons.MINUS);
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

    public void setToggleDownIcon(FontIcon toggleDownIcon) {
        getState().setToggleDownHtml(toggleDownIcon.getHtml());
    }

    public void setToggleUpIcon(FontIcon toggleUpIcon) {
        getState().setToggleUpHtml(toggleUpIcon.getHtml());
    }

    public void setToggleEnabled(boolean enabled) {
        getState().setToggleEnabled(enabled);
    }

    public boolean isToggleEnabled() {
        return getState().isToggleEnabled();
    }
    
    @Override
    protected StackPanelState getState() {
        return (StackPanelState) super.getState();
    }

    public void addToggleListener(ToggleListener listener) {
        listeners.add(listener);
    }

    public void removeToggleListener(ToggleListener listener) {
        listeners.remove(listener);
    }

    public void addToggleDisabledClickListener(ToggleDisabledClickListener listener) {
        toggleDisabledClickListeners.add(listener);
    }

    public void removeToggleDisabledClickListener(ToggleDisabledClickListener listener) {
        toggleDisabledClickListeners.remove(listener);
    }

    public int getTabIndex() {
        return getState().getTabIndex();
    }

    public void setTabIndex(int tabIndex) {
        getState().setTabIndex(tabIndex);
    }

    public void addFocusListener(FocusListener focusListener) {
        focusListeners.add(focusListener);
    }

    public void removeFocusListener(FocusListener focusListener) {
        focusListeners.remove(focusListener);
    }

    public void addBlurListener(BlurListener blurListener) {
        blurListeners.add(blurListener);
    }

    public void removeBlurListener(BlurListener blurListener) {
        blurListeners.remove(blurListener);
    }

    @Override
    public Class<? extends SharedState> getStateType() {
        return StackPanelState.class;
    }

}
