package org.kn.knowyourcontact;

import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.phonegui.PhoneScreen;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.RuntimeStore;
import net.rim.device.api.system.UnsupportedOperationException;

import org.kn.knowyourcontact.utils.ApplicationContext;
import org.kn.knowyourcontact.utils.PrimitiveUtils;

/**
 * Entry Point to the Know Your Contact (KYC) application. Delegates further
 * responsibility to Configuration Entry Point (when the KYC icon is clicked),
 * and the background Entry Point (Self), that handles listening to incoming
 * calls
 * 
 * @author Karan Nangru
 * 
 */
public class EntryPoint extends Application {

    private static final String LOAD_CONFIGURATIONS = "config";
    private static EntryPoint backGroundEntryPoint;
    private static EntryPoint instance;

    private boolean _initialized = false;
    private String initializationException = "";
    private String callListenerException = "";

    private CallListener callListener;

    /**
     * The application entry method
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (PhoneScreen.isSupported()) {
            if (showConfigurations(args)) {
                org.kn.knowyourcontact.configuration.EntryPoint.getInstance();
            }
            else {
                backGroundEntryPoint = EntryPoint.getInstance();
                backGroundEntryPoint.invokeLater(new Runnable() {
                    public void run() {
                        backGroundEntryPoint.bootloader();
                    }
                });
            }
        } else {
            throw new UnsupportedOperationException(
                    "Application not supported on your device");
        }

        backGroundEntryPoint.enterEventDispatcher();
    }

    /**
     * Appends the exceptionMessage to the already existing exceptionMessage
     * 
     * @param exceptionMessage
     */
    public void setInitializationException(String exceptionMessage) {
        initializationException = exceptionMessage;
    }

    /**
     * Sets the call listener exception
     * 
     * @param callListenerError
     */
    public void setCallListenerException(String callListenerError) {
        this.callListenerException = callListenerError;
    }

    /**
     * Sets the application status. If applicationStatus=true, then switched ON
     * Else, KYC is OFF
     * 
     * @param applicationStatus
     */
    public void setApplicationStatus(boolean applicationStatus) {
        setCallListener(applicationStatus);
    }

    /**
     * Returns the all errors recorded in the initialization of the kyc
     * background app. If none, then returns an empty string
     * 
     * @return the initialization exception
     */
    public String getInitializationException() {
        return initializationException;
    }

    /**
     * Returns the last recorded call listener exception
     * 
     * @return the last call listener exception
     */
    public String getCallListenerException() {
        return callListenerException;
    }

    private void bootloader() {
        ApplicationManager applicationManager = ApplicationManager.getApplicationManager();
        boolean inStartup = applicationManager.inStartup();

        while (inStartup) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // Do nothing
            }

            inStartup = applicationManager.inStartup();
        }

        backGroundEntryPoint.initialise();
    }

    private void initialise() {
        synchronized (this) {
            if (!_initialized) {
                _initialized = false;
                initializationException = "";
                try {
                    storeApplicationInRuntimeStore();
                    callListener = new CallListener(this);
                    if (activateCallerId()) {
                        Phone.addPhoneListener(callListener);
                    }
                    _initialized = true;
                } catch (Exception e) {
                    setInitializationException(e.getMessage());
                }
            }
        }
    }

    private boolean activateCallerId() throws Exception {
        return getKYCStatus();
    }

    private boolean getKYCStatus() throws Exception {
        String applicationStatus = org.kn.knowyourcontact.utils.ApplicationContext.getApplicationContext().get(
                KnowYourContactConstants.CONFIGURATION_APPLICATION_STATUS);
        if (org.kn.knowyourcontact.utils.PrimitiveUtils.isEmpty(applicationStatus)) {
            /**
             * Application installation case
             */
            ApplicationContext.getApplicationContext().set(KnowYourContactConstants.CONFIGURATION_APPLICATION_STATUS,
                    KnowYourContactConstants.CONFIGURATION_APPLICATION_STATUS_ON);
            return true;
        } else {
            return PrimitiveUtils.convertToBoolean(applicationStatus);
        }
    }

    private void storeApplicationInRuntimeStore() {
        RuntimeStore runtimeStore = RuntimeStore.getRuntimeStore();
        synchronized (runtimeStore) {
            runtimeStore.put(KnowYourContactConstants.KYC_UNIQUE_HASHTAG, Application.getApplication());
        }
    }

    private static synchronized EntryPoint getInstance() {
        if (instance == null) {
            instance = new EntryPoint();
        }
        return instance;
    }

    private static boolean showConfigurations(String[] args) {
        if (args != null) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase(LOAD_CONFIGURATIONS)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the call listener status. If callListenerStatus=true, then the
     * Caller listener is switched ON Else, it is switched OFF
     * 
     * @param callListenerStatus
     */
    private void setCallListener(boolean callListenerStatus) {
        if (callListenerStatus) {
            Phone.addPhoneListener(callListener);
        } else {
            Phone.removePhoneListener(callListener);
        }
    }

}
