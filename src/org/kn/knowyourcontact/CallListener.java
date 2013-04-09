package org.kn.knowyourcontact;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.pim.Contact;

import net.rim.blackberry.api.pdap.BlackBerryContact;
import net.rim.blackberry.api.phone.AbstractPhoneListener;
import net.rim.blackberry.api.phone.Phone;
import net.rim.blackberry.api.phone.PhoneCall;
import net.rim.blackberry.api.phone.phonegui.PhoneScreen;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * Listens to incoming, outgoing, waiting calls
 */
public final class CallListener extends AbstractPhoneListener {

    private EntryPoint entryPoint;

    /**
     * Initialize the phone call listener
     * 
     * @param entryPoint
     */
    public CallListener(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    /**
     * Called when there is a call waiting
     * 
     * @see AbstractPhoneListener#callWaiting(int)
     */
    public void callWaiting(int callid) {
        loadContactInfo(callid);
    }

    /**
     * Called when there is a call initiated
     * 
     * @see AbstractPhoneListener#callInitiated(int)
     */
    public void callInitiated(int callid) {
        // Do nothing : Don't show contact note on outgoing calls
    }

    /**
     * Called when there is an incoming call
     * 
     * @see AbstractPhoneListener#callIncoming(int)
     */
    public void callIncoming(int callid) {
        loadContactInfo(callid);
    }

    /**
     * Loads the contact info for the call and displays it on the call screen
     * 
     * @param callid
     *            Id of an incoming call
     */
    private void loadContactInfo(int callid) {
        /**
         * Reset call listener exception since a new call is started
         */
        entryPoint.setCallListenerException("");

        try {
            PhoneCall phoneCall = Phone.getCall(callid);
            if (phoneCall != null) {
                PhoneScreen phoneScreen = new PhoneScreen(callid, entryPoint);
                phoneScreen.setScreenForeground(Color.POWDERBLUE);

                String phoneNumber = phoneCall.getPhoneNumber();
                Vector contactsVector = Phone.getContactsByPhoneNumber(phoneNumber);
                manageContactResult(callid, phoneScreen, contactsVector);
                phoneScreen.sendDataToScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
            entryPoint.setCallListenerException(e.getMessage());
        }
    }

    private void manageContactResult(int callid, PhoneScreen phoneScreen, Vector contactsVector) {
        if (null != contactsVector) {
            Enumeration elements = contactsVector.elements();
            while (elements.hasMoreElements())
            {
                Object contactObject = elements.nextElement();
                if (contactObject instanceof BlackBerryContact)
                {
                    BlackBerryContact blackBerryContact = (BlackBerryContact) contactObject;
                    updateCallScreen(callid, phoneScreen, blackBerryContact);
                    break;
                }
            }
        }
    }

    private void updateCallScreen(int callid, PhoneScreen phoneScreen, BlackBerryContact blackBerryContact) {
        if (null != blackBerryContact) {
            String note = blackBerryContact.getString(Contact.NOTE, 0);
            Manager screenManager = decorateIncomingCallScreen(note);
            phoneScreen.add(screenManager);
        }
    }

    private Manager decorateIncomingCallScreen(String note) {
        Manager phoneScreenVerticalManager = new VerticalFieldManager(VerticalFieldManager.USE_ALL_WIDTH) {
            public int getPreferredHeight() {
                return 400;
            }
        };

        phoneScreenVerticalManager = addContactNote(note, phoneScreenVerticalManager);
        HorizontalFieldManager phoneScreenHorizontalManager = new HorizontalFieldManager(HorizontalFieldManager.USE_ALL_HEIGHT);
        phoneScreenHorizontalManager.add(phoneScreenVerticalManager);
        return phoneScreenHorizontalManager;
    }

    private Manager addContactNote(String note, Manager phoneScreenVerticalManager) {
        LabelField contactDetails = new LabelField(note, DrawStyle.HCENTER);
        phoneScreenVerticalManager.add(contactDetails);
        return phoneScreenVerticalManager;
    }

}
