package org.vaadin.addons.stackpanel.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.stackpanel.StackPanel;
import org.vaadin.addons.stackpanel.StackPanel.ToggleListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("Vaadin StackPanel Extension Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        // panel with standard toggle icons and icon
        Panel section0 = new SectionPanel("StackPanel with icon");
        section0.setIcon(VaadinIcons.ADJUST);
        StackPanel.extend(section0);

        // panel with standard toggle icons
        Panel section1 = new SectionPanel("StackPanel");
        StackPanel.extend(section1);

        // panel with individual icons
        Panel section2 = new SectionPanel("StackPanel with individual toggle icons");
        StackPanel panel2 = StackPanel.extend(section2);
        panel2.setToggleDownIcon(VaadinIcons.CARET_SQUARE_DOWN_O);
        panel2.setToggleUpIcon(VaadinIcons.CARET_SQUARE_UP_O);

        // panel without toggle icons
        Panel section3 = new SectionPanel("StackPanel without icons");
        StackPanel panel3 = StackPanel.extend(section3);
        panel3.setToggleIconsEnabled(false);

        // panel without toggle icons
        Panel section4 = new SectionPanel("StackPanel with toggle listener");
        StackPanel panel4 = StackPanel.extend(section4);
        panel4.addToggleListener(new ToggleListener() {

            @Override
            public void toggleClick(StackPanel source) {
                Notification.show("Toggle Listener fired!");
            }
        });

        // panel with caption as html
        Panel section5 = new SectionPanel("StackPanel <b>with caption as html</b>");
        section5.setCaptionAsHtml(true);
        section5.setIcon(VaadinIcons.CART);
        StackPanel panel5 = StackPanel.extend(section5);
        CheckBox enableDisable = new CheckBox("Section 5 enabled");
        enableDisable.setValue(true);
        enableDisable.addValueChangeListener((v) -> {
            panel5.setToggleEnabled(v.getValue());
        });
        panel5.addToggleDisabledClickListener(s -> Notification.show("Header Clicked!"));
        panel5.addToggleListener(s -> Notification.show("Toggle Clicked!"));
        setContent(new VerticalLayout(section0, section1, section2, section3, section4, section5, enableDisable));
    }

    public static class SectionPanel extends Panel {

        public SectionPanel(String caption) {
            setCaption(caption);
            setContent(new HorizontalLayout() {

                {
                    setSizeFull();
                    setMargin(true);
                    setSpacing(true);
                    setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
                    addComponents(
                            new TextField("First Name"),
                            new TextField("Last Name"),
                            new TextField("Phone"),
                            new Button("Save"));
                }
            });
        }
    }

}
