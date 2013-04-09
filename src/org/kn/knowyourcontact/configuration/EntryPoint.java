package org.kn.knowyourcontact.configuration;

import net.rim.device.api.system.RuntimeStore;
import net.rim.device.api.ui.UiApplication;

import org.kn.knowyourcontact.KnowYourContactConstants;
import org.kn.knowyourcontact.utils.UserInterfaceUtils;

/**
 * Entry point to the configuration screen that is displayed on clicking the KYC
 * icon in phone
 * 
 * @author Karan Nangru
 * 
 */
public class EntryPoint extends UiApplication {

    private static EntryPoint instance;
    private org.kn.knowyourcontact.EntryPoint backgroundEntryPoint;
    private ConfigurationScreen configurationScreen;

    /**
     * Fetch the current instance of Entry Point
     * 
     * @return Configuration Entry Point singleton instance
     */
    public static synchronized EntryPoint getInstance() {
        if (instance == null) {
            instance = new EntryPoint();
            instance.enterEventDispatcher();
        } else {
            if (!instance.isForeground()) {
                instance.requestForeground();
            }
        }
        return instance;
    }

    /**
     * This method is called on Activation of this EntryPoint
     * 
     * @see net.rim.device.api.ui.UiApplication#activate()
     * 
     */
    public synchronized void activate() {
        /**
         * DO nothing as for now. But this is an important handle, so overriding
         * the same
         * 
         */
    }

    private EntryPoint() {
        loadAndDisplayConfigurations();
    }

    private void loadBackgroundApplication() throws Exception {
        long applicationId = KnowYourContactConstants.KYC_UNIQUE_HASHTAG;
        RuntimeStore appReg = RuntimeStore.getRuntimeStore();
        synchronized (appReg) {
            if (appReg.get(applicationId) != null) {
                backgroundEntryPoint = (org.kn.knowyourcontact.EntryPoint) appReg
                        .waitFor(applicationId);
            }
        }
    }

    private void loadAndDisplayConfigurations() {
        try {
            loadBackgroundApplication();
            configurationScreen = new ConfigurationScreen(backgroundEntryPoint);
            this.pushScreen(configurationScreen);
        } catch (Exception e) {
            e.printStackTrace();
            UserInterfaceUtils
                    .diplayExitPopup("Exception occurred while loading Configuration!");
        }
    }
}
