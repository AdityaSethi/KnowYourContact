package org.kn.knowyourcontact.utils;

import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

import org.kn.knowyourcontact.KnowYourContactConstants;

/**
 * Persists the application specific settings in the device
 * 
 * @author Karan Nangru
 * 
 */
public class ApplicationContext {

    private static ApplicationContext applicationContext;
    private Hashtable applicationSettings;
    private PersistentObject persistentObject;

    /**
     * Returns the singleton instance of ApplicationContext
     * 
     * @return the ApplicationContext instance
     */
    public static synchronized ApplicationContext getApplicationContext() {
        if (null == applicationContext) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    /**
     * Returns the value stored for the input key
     * 
     * @param key
     * @return the key's value
     * @throws Exception
     */
    public String get(String key) throws Exception {
        Object valueObject = applicationSettings.get(key);
        if (null != valueObject) {
            return (String) valueObject;
        } else
            return null;
    }

    /**
     * Set the value for the provided key
     * 
     * @param key
     * @param value
     * @throws Exception
     */
    public synchronized void set(String key, String value) throws Exception {
        applicationSettings.put(key, value);
        commit();
    }

    private void commit() {
        synchronized (persistentObject) {
            persistentObject.commit();
        }
    }

    private ApplicationContext() {
        initialize();
    }

    private void initialize() {
        persistentObject = PersistentStore.getPersistentObject(KnowYourContactConstants.KYC_UNIQUE_HASHTAG);
        synchronized (persistentObject) {
            applicationSettings = (Hashtable) persistentObject.getContents();
            if (null == applicationSettings) {
                applicationSettings = new Hashtable();
                persistentObject.setContents(applicationSettings);
                persistentObject.commit();
            }
        }
    }
}
