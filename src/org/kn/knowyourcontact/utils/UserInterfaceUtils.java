package org.kn.knowyourcontact.utils;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

/**
 * Contains user interface specific utility methods
 * 
 */
public class UserInterfaceUtils {

    /**
     * Displays a popup before exiting the app
     * 
     * @param message
     *            - The message to display in the popup
     */
    public static void diplayExitPopup(final String message) {
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run() {
                Dialog.alert(message);
                System.exit(0);
            }
        });
    }

}
