package org.vaadin.addons.stackpanel.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.VPanel;
import com.vaadin.shared.ui.Connect;

import org.vaadin.addons.stackpanel.StackPanel;

@SuppressWarnings("serial")
@Connect(StackPanel.class)
public class StackPanelConnector extends AbstractExtensionConnector {

    public static final String CLASSNAME = "v-stackpanel";

    private static final String STYLENAME_SUFFIX_OPEN = CLASSNAME + "-open";
    private static final String STYLENAME_SUFFIX_CLOSED = CLASSNAME + "-closed";

    private StackPanelRpc rpc = RpcProxy.create(StackPanelRpc.class, this);

    private VPanel panel;

    private final Element captionToggle = DOM.createSpan();

    @Override
    protected void extend(ServerConnector target) {
        panel = (VPanel) ((ComponentConnector) target).getWidget();
        panel.addStyleName(CLASSNAME);

        captionToggle.addClassName(CLASSNAME + "-toggle");
        
        
        DOM.insertChild(panel.captionNode, captionToggle, 2);

        updateStyleName(panel);
       

        panel.addDomHandler(new ClickHandler() {
        	
        	//toggle click event

            @Override
            public void onClick(ClickEvent event) {
                EventTarget eventTarget = event.getNativeEvent().getEventTarget();
           
                if (eventTarget.cast() == panel.captionNode || eventTarget.cast() == panel.captionNode.getFirstChildElement() || 
                		eventTarget.cast() == captionToggle.getFirstChildElement())  { 
                	
                	
                	//refresh state of panel
                    getState().setOpen(!getState().isOpen());
                    refresh();
                    rpc.setOpen(getState().isOpen());
                    
                    //fire toggle listener
                    rpc.toggleClick();
                    
                    
                }
            }

        }, ClickEvent.getType());
    }
    

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        if (stateChangeEvent.hasPropertyChanged("open")) {
            refresh();
        }
    }

    public void refresh() {
        UIObject.setVisible(panel.contentNode, getState().isOpen());
        updateStyleName(panel);
        
        if (getState().isToggleIconEnabled()) {
        	updateToggleIcon(getState().isOpen());
        }   
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
