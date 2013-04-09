package org.kn.knowyourcontact.utils;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.component.LabelField;

/**
 * Used for building additional information labels
 * 
 * @author Karan Nangru
 * 
 */
public class AdditionalInfoLabel implements CallerIdLabel {

    private final String text;
    private LabelField labelField;

    /**
     * Initialise the label
     * 
     * @param text
     */
    public AdditionalInfoLabel(String text) {
        this.text = text;
        init();
    }

    /**
     * Return the created label
     * 
     * @see com.mck.callerid.utils.CallerIdLabel#build()
     */
    public LabelField build() {
        return labelField;
    }

    private void init() {
        decorateLabel();
    }

    private void decorateLabel() {
        labelField = new LabelField(text) {
            protected void paintBackground(net.rim.device.api.ui.Graphics g)
            {
                g.clear();
                g.getColor();
                g.setColor(Color.GRAY);
            }
        };
        FontFamily fontFamily[] = FontFamily.getFontFamilies();
        Font font = fontFamily[1].getFont(Font.ITALIC, 16);
        labelField.setFont(font);
    }
}
