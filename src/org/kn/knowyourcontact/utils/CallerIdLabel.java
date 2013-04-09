package org.kn.knowyourcontact.utils;

import net.rim.device.api.ui.component.LabelField;

/**
 * All labels used in KYC must implement this label field
 * 
 */
public interface CallerIdLabel {

    /**
     * Create the label field
     * 
     * @return the created label field
     * 
     */
    public LabelField build();
}
