package org.vaadin.addons.stackpanel.client;

import java.util.logging.Logger;

import org.vaadin.addons.stackpanel.StackPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.UIObject;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VPanel;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(StackPanel.class)
public class StackPanelConnector extends AbstractExtensionConnector {

    public static final String CLASSNAME = "v-stackpanel";
    private static final String STYLENAME_SUFFIX_OPEN = CLASSNAME + "-open";
    private static final String STYLENAME_SUFFIX_CLOSED = CLASSNAME + "-closed";
    private static final String STYLENAME_SUFFIX_DISABLED= CLASSNAME + "-disabled";

    private StackPanelRpc rpc = RpcProxy.create(StackPanelRpc.class, this);

    private VPanel panel;

    private final Element captionToggle = DOM.createSpan();

    private final ClickHandler toggleClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if (DOM.asOld(panel.captionNode).isOrHasChild(Element.as(event.getNativeEvent().getEventTarget()))) {
                getState().setOpen(!getState().isOpen());
                refresh();
                rpc.setOpen(getState().isOpen());

                //fire toggle listener
                rpc.toggleClick();
            }
        }
    };

    private final ClickHandler toggleDisabledClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if (DOM.asOld(panel.captionNode).isOrHasChild(Element.as(event.getNativeEvent().getEventTarget()))) {
                rpc.toggleDisabledClick();
            }
        }
    };

    private HandlerRegistration clickHandlerRegistration;

    @Override
    protected void extend(ServerConnector target) {
        panel = (VPanel) ((ComponentConnector) target).getWidget();
        panel.addStyleName(CLASSNAME);
        captionToggle.addClassName(CLASSNAME + "-toggle");

        DOM.insertChild(panel.captionNode, captionToggle, 2);

        updateStyleName(panel);

        clickHandlerRegistration = panel.addDomHandler(toggleClickHandler, ClickEvent.getType());

        panel.captionNode.setTabIndex(getState().getTabIndex());
        Event.sinkEvents(panel.captionNode, Event.FOCUSEVENTS);
        Event.setEventListener(panel.captionNode, new EventListener() {
            @Override
            public void onBrowserEvent(Event event) {
                if (event.getTypeInt() == Event.ONFOCUS) {
                    rpc.focus();
                }
                if (event.getTypeInt() == Event.ONBLUR) {
                    rpc.blur();
                }
            }
        });
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        if (stateChangeEvent.hasPropertyChanged("open") || stateChangeEvent.hasPropertyChanged("toggleEnabled")) {
            refresh();
        }
    }

    public void refresh() {
        UIObject.setVisible(panel.contentNode, getState().isOpen());
        updateStyleName(panel);

        if (getState().isToggleIconEnabled()) {
            updateToggleIcon(getState().isOpen());
        }

        if (getState().isToggleEnabled()) {
            if (clickHandlerRegistration != null) {
                clickHandlerRegistration.removeHandler();
            }
            clickHandlerRegistration = panel.addDomHandler(toggleClickHandler, ClickEvent.getType());
        } else {
            if (clickHandlerRegistration != null) {
                clickHandlerRegistration.removeHandler();
            }
            clickHandlerRegistration = panel.addDomHandler(toggleDisabledClickHandler, ClickEvent.getType());
        }
        panel.captionNode.setTabIndex(getState().getTabIndex());
    }

    private void updateToggleIcon(Boolean isOpen) {
        if (isOpen) {
            captionToggle.setInnerHTML(getState().getToggleUpHtml());
        } else {
            captionToggle.setInnerHTML(getState().getToggleDownHtml());
        }
    }

    private void updateStyleName(VPanel panel) {
        if (getState().isOpen()) {
            panel.removeStyleName(STYLENAME_SUFFIX_CLOSED);
            panel.addStyleName(STYLENAME_SUFFIX_OPEN);
        } else {
            panel.removeStyleName(STYLENAME_SUFFIX_OPEN);
            panel.addStyleName(STYLENAME_SUFFIX_CLOSED);
        }

        if (getState().isToggleEnabled()) {
            panel.removeStyleName(STYLENAME_SUFFIX_DISABLED);
        } else {
            panel.addStyleName(STYLENAME_SUFFIX_DISABLED);
        }
    }

    @Override
    public StackPanelState getState() {
        return (StackPanelState) super.getState();
    }

    @SuppressWarnings("unused")
    private static Logger getLogger() {
        return Logger.getLogger(StackPanelConnector.class.getName());
    }

}
