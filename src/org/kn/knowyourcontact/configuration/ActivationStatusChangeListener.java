package org.kn.knowyourcontact.configuration;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.CheckboxField;

import org.kn.knowyourcontact.KnowYourContactConstants;
import org.kn.knowyourcontact.utils.ApplicationContext;
import org.kn.knowyourcontact.utils.UserInterfaceUtils;

/**
 * Listens to change is KYC's activation status
 * 
 * @author Karan Nangru
 * 
 */
public class ActivationStatusChangeListener implements FieldChangeListener {

    private final org.kn.knowyourcontact.EntryPoint backgroundEntryPoint;

    ActivationStatusChangeListener(org.kn.knowyourcontact.EntryPoint backgroundEntryPoint) {
        this.backgroundEntryPoint = backgroundEntryPoint;
    }

    /**
     * Called when activation status field is changed
     * 
     * @see net.rim.device.api.ui.FieldChangeListener#fieldChanged(net.rim.device.api.ui.Field,
     *      int)
     */
    public void fieldChanged(Field field, int context) {
        if (field instanceof CheckboxField) {
            CheckboxField enableCallerIdCheckbox = (CheckboxField) field;
            boolean enableCallerId = enableCallerIdCheckbox.getChecked();
            setCallerIdActivationStatus(enableCallerId);
        }
    }

    private void setCallerIdActivationStatus(boolean enableCallerId) {
        try {
            ApplicationContext.getApplicationContext().set(KnowYourContactConstants.CONFIGURATION_APPLICATION_STATUS,
                    String.valueOf(enableCallerId));
        } catch (Exception e) {
            UserInterfaceUtils.diplayExitPopup("Error : Could not change activation status");
        }
        backgroundEntryPoint.setApplicationStatus(enableCallerId);
    }

}
