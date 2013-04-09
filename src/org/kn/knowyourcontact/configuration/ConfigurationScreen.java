package org.kn.knowyourcontact.configuration;

import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.container.MainScreen;

import org.kn.knowyourcontact.KnowYourContactConstants;
import org.kn.knowyourcontact.utils.AdditionalInfoLabel;
import org.kn.knowyourcontact.utils.ApplicationContext;
import org.kn.knowyourcontact.utils.ErrorLabel;
import org.kn.knowyourcontact.utils.PrimitiveUtils;
import org.kn.knowyourcontact.utils.UserInterfaceUtils;

/**
 * The UI screen used to configure the KYC
 * 
 * @author Karan Nangru
 * 
 */
public final class ConfigurationScreen extends MainScreen {

    private CheckboxField enableCallerIdCheckbox;
    private boolean applicationEnabled;
    private final org.kn.knowyourcontact.EntryPoint backgroundEntryPoint;

    /**
     * Initializes the config screen
     * 
     * @param backgroundEntryPoint
     */
    public ConfigurationScreen(org.kn.knowyourcontact.EntryPoint backgroundEntryPoint) {
        super();
        this.backgroundEntryPoint = backgroundEntryPoint;
        setTitle(KnowYourContactConstants.CONFIG_SCREEN_TITLE);
        decorateConfigurationScreen();
    }

    /**
     * This method is called on closing the Configuration screen
     * 
     * @see net.rim.device.api.ui.Screen#onClose()
     * 
     */
    public boolean onClose()
    {
        System.exit(0);
        return true;
    }

    private void decorateConfigurationScreen() {
        addEnableCallerIdCheckbox();
        addBackgroundEntryPointExceptions();
    }

    private void addBackgroundEntryPointExceptions() {
        String errorLabelText = "";
        if (!PrimitiveUtils.isEmpty(backgroundEntryPoint.getInitializationException())) {
            errorLabelText = errorLabelText + backgroundEntryPoint.getInitializationException();
        }

        if (!PrimitiveUtils.isEmpty(backgroundEntryPoint.getCallListenerException())) {
            errorLabelText = errorLabelText + "\n" + backgroundEntryPoint.getCallListenerException();
        }

        if (!PrimitiveUtils.isEmpty(errorLabelText)) {
            ErrorLabel backgroundEntryPointErrorTraceLabel = new ErrorLabel(errorLabelText);
            add(backgroundEntryPointErrorTraceLabel.build());
        }

    }

    private void addEnableCallerIdCheckbox() {
        try {
            String applicationStatus = ApplicationContext.getApplicationContext().get(KnowYourContactConstants.CONFIGURATION_APPLICATION_STATUS);
            applicationEnabled = PrimitiveUtils.convertToBoolean(applicationStatus);
            enableCallerIdCheckbox = new CheckboxField(" Display Contact Notes", applicationEnabled);
            enableCallerIdCheckbox.setChangeListener(new ActivationStatusChangeListener(backgroundEntryPoint));
        } catch (Exception e) {
            UserInterfaceUtils.diplayExitPopup("Exception : Could not read current status \n " + e.getMessage());
        }

        AdditionalInfoLabel enableCallerIdInfoLabel = new AdditionalInfoLabel("Turn off to stop displaying Contact notes");
        add(enableCallerIdCheckbox);
        add(enableCallerIdInfoLabel.build());
    }
}
