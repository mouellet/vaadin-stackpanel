package org.vaadin.addons.stackpanel;

import org.junit.Test;

import com.vaadin.ui.Panel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StackPanelTest {

    @Test
    public void whenClosed_thenIsNotOpen() {
        Panel panel = new Panel();
        StackPanel stackPanel = StackPanel.extend(panel);
        assertTrue(stackPanel.isOpen());

        stackPanel.close();
        assertFalse(stackPanel.isOpen());
    }

}
