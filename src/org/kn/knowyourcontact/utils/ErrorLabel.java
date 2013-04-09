package org.kn.knowyourcontact.utils;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.component.LabelField;

/**
 * Label for showing errors. Has a red color oriented styling
 * 
 */
public class ErrorLabel implements CallerIdLabel {

    private final String text;
    private LabelField labelField;

    /**
     * Initialize the label
     * 
     * @param text
     */
    public ErrorLabel(String text) {
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
                g.setColor(Color.RED);
            }
        };
        FontFamily fontFamily[] = FontFamily.getFontFamilies();
        Font font = fontFamily[1].getFont(Font.PLAIN, 16);
        labelField.setFont(font);
    }

}
