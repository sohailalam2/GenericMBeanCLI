/*
 * Copyright 2013 The Generic MBean CLI Project
 *
 * The Generic MBean CLI Project licenses this file to you under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *              http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.sohail.alam.generic.mbean.cli.jmx;

import com.sohail.alam.generic.mbean.cli.CliProperties;
import com.sohail.alam.generic.mbean.cli.HelperMethods;
import com.sohail.alam.generic.mbean.cli.logger.Logger;
import com.sohail.alam.generic.mbean.cli.security.Authentication;
import com.sohail.alam.generic.mbean.cli.security.DefaultAuthentication;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * TODO: After JMX Connection Closed
 * Any Attribute/Operation commands tells the user to connect to a JMX server before executing it
 */


/**
 * <p/>
 * The Default implementation of {@link JMXInitializer} interface.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 19/5/13
 *          Time: 6:39 PM
 * @since 1.0.0
 */
public class DefaultJMXInitializer implements JMXInitializer {

    /**
     * The constant PING_TIMEOUT.
     */
    public static final int PING_TIMEOUT = 2000;
    /**
     * The PROPERTIES.
     */
    private final CliProperties PROPERTIES;
    /**
     * The AUTHENTICATION.
     */
    private final Authentication AUTHENTICATION;
    /**
     * The Filter object names.
     */
    private final ArrayList<String> filterObjectNames;
    /**
     * The BEAN OBJECT NAMES.
     */
    private final ArrayList<ObjectName> BEAN_OBJECT_NAMES;
    /**
     * The Storage.
     */
    private DataStorage storage;
    /**
     * The Jmxc.
     */
    private JMXConnector jmxc = null;
    /**
     * The Mbsc.
     */
    private MBeanServerConnection mbsc = null;

    /**
     * Instantiates a new JMX initializer implementation.
     */
    private DefaultJMXInitializer() {

        PROPERTIES = CliProperties.getInstance();
        AUTHENTICATION = DefaultAuthentication.getInstance();
        BEAN_OBJECT_NAMES = new ArrayList<ObjectName>();

        // All Beans containing these as the domain name will be rejected
        filterObjectNames = new ArrayList<String>();
        // JAVA DEFAULTS
        filterObjectNames.add("com.sun.management");
        filterObjectNames.add("java.lang");
        filterObjectNames.add("java.util.logging");
        filterObjectNames.add("JMImplementation");
        // EXTRAS
        filterObjectNames.add("org.eclipse.jetty.jmx");
        filterObjectNames.add("org.eclipse.jetty.util.log");
        filterObjectNames.add("org.eclipse.jetty.server");
        filterObjectNames.add("org.eclipse.jetty.server.handler");

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JMXInitializer getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Connect boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean connect() {
        String jmxIP = PROPERTIES.getJmxIP();
        int jmxPort = PROPERTIES.getJmxPort();
        try {
            InetAddress inetAddress = InetAddress.getByName(jmxIP);
            if (inetAddress.isReachable(PING_TIMEOUT)) {
                JMXServiceURL url = new JMXServiceURL(
                        "service:jmx:rmi:///jndi/rmi://" +
                                jmxIP + ":" +
                                jmxPort + "/jmxrmi");
                jmxc = JMXConnectorFactory.connect(url);
                jmxc.addConnectionNotificationListener(new NotificationListener() {
                    @Override
                    public void handleNotification(Notification notification, Object handback) {
                        String notificationMessage = notification.getMessage();
                        String notificationType = notification.getType();
                        long notificationSequenceNumber = notification.getSequenceNumber();
                        Logger.logMessage("## JMX NOTIFICATION ##");
                        Logger.logMessage("Notification Sequence Number : " + notificationSequenceNumber);
                        Logger.logMessage("Notification Type            : " + notificationType);
                        Logger.logMessage("Notification Message         : " + notificationMessage);
                        if (notificationType.contains("jmx.remote.connection.failed")) {
                            System.err.println("## THE JMX SERVER CONNECTION HAS FAILED ##");
                            HelperMethods.getInstance().exit(-1);
                        }
                    }
                }, null, null);

                mbsc = jmxc.getMBeanServerConnection();
                Logger.logMessage("You have successfully logged in to JMX Server: " +
                        jmxIP + ":" + jmxPort);

                // Initialize Data Storage
                initializeDataStorage();
            } else {
                Logger.logMessage("The JMX Server Address -> " +
                        jmxIP + ":" + jmxPort +
                        " is not reachable!! Please Try Again");
            }


            return true;
        } catch (Exception e) {
            jmxc = null;
            mbsc = null;
            Logger.logMessage("Can not login to JMX Server: " +
                    jmxIP + ":" + jmxPort);
            Logger.logException(e, getClass());
            return false;
        }
    }

    /**
     * Initialize data storage.
     *
     * @throws IOException the iO exception
     * @throws IOException the iO exception
     * @throws IOException the iO exception
     * @throws IOException the iO exception
     */
    private void initializeDataStorage() throws IOException, InstanceNotFoundException, IntrospectionException, ReflectionException {
        // Get all the MBean Object Names
        Set<ObjectName> beanObjectNames = mbsc.queryNames(null, null);
        // Iterate all these
        for (ObjectName beanObjectName : beanObjectNames) {
            String domainName = beanObjectName.getDomain();
            // Filtering out the unnecessary beans
            if (!filterObjectNames.contains(domainName)) {

                // Store the MBean Object Name
                BEAN_OBJECT_NAMES.add(beanObjectName);

                // Create an instance of DataStorage
                storage = DefaultDataStorage.getInstance(beanObjectName);

                // gets the information from MBean server
                MBeanInfo mBeanInfo = getMbsc().getMBeanInfo(beanObjectName);

                // Get the attributes info and put them in the map
                MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
                for (MBeanAttributeInfo mBeanAttributeInfo : attributes) {
                    String name = mBeanAttributeInfo.getName();
                    String className = mBeanAttributeInfo.getType();
                    String readable = String.valueOf(mBeanAttributeInfo.isReadable());
                    String writeable = String.valueOf(mBeanAttributeInfo.isWritable());
                    String isGetter = String.valueOf(mBeanAttributeInfo.isIs());
                    String description = mBeanAttributeInfo.getDescription();
                    storage.addToBeanAttribute(name, className, readable, writeable, isGetter, description);
                }

                // Get the operations info and put them in the map
                MBeanOperationInfo[] operations = mBeanInfo.getOperations();
                for (MBeanOperationInfo mBeanOperationInfo : operations) {
                    String name = mBeanOperationInfo.getName();
                    String description = mBeanOperationInfo.getDescription();
                    String returnType = mBeanOperationInfo.getReturnType();
                    MBeanParameterInfo[] mBeanParameterInfo = mBeanOperationInfo.getSignature();
                    ArrayList<Map<String, String>> parameters = new ArrayList<Map<String, String>>();
                    Map<String, String> parameterInfo;
                    for (MBeanParameterInfo info : mBeanParameterInfo) {
                        // TODO: change HashMap to something which can guaranty ordering of the parameters
                        parameterInfo = new HashMap<String, String>();
                        parameterInfo.put(DataStorage.NAME, info.getName());
                        parameterInfo.put(DataStorage.TYPE, info.getType());
                        parameterInfo.put(DataStorage.DESCRIPTION, info.getDescription());
                        parameters.add(parameterInfo);
                    }
                    storage.addToMBeanOperations(name, description, returnType, parameters);
                }
            }
        }
    }

    /**
     * Disconnect boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean disconnect() {

        if (jmxc != null) {
            try {
                jmxc.close();
                Logger.logMessage("You have successfully disconnected from JMX Server: " +
                        PROPERTIES.getJmxIP() + ":" + PROPERTIES.getJmxPort());
//                AUTHENTICATION.logout();
                storage.purgeDataStorage();
                getMBeanObjectNames().clear();
            } catch (IOException e) {
                Logger.logException(e, getClass());
                return false;
            }
        }
        return true;
    }

    /**
     * Gets jmxc.
     *
     * @return the jmxc
     */
    @Override
    public JMXConnector getJmxc() {
        return jmxc;
    }

    /**
     * Gets mbsc.
     *
     * @return the mbsc
     */
    @Override
    public MBeanServerConnection getMbsc() {
        return mbsc;
    }

    /**
     * Reload the Data Structure containing the MBean Information
     */
    @Override
    public void reload() {
        try {
            initializeDataStorage();
        } catch (Exception ex) {
            Logger.logException(ex, getClass());
        }
    }

    /**
     * Gets bean object names.
     *
     * @return the bean object names
     */
    @Override
    public ArrayList<ObjectName> getMBeanObjectNames() {
        return BEAN_OBJECT_NAMES;
    }

    /**
     * Gets MBean object name.
     *
     * @param mbeanName the mbean name
     *
     * @return the m bean object name
     */
    @Override
    public ObjectName getMBeanObjectName(String mbeanName) {

        for (ObjectName beanObjectName : getMBeanObjectNames()) {
            if (beanObjectName.getCanonicalName().equalsIgnoreCase(mbeanName)) {
                return beanObjectName;
            }
        }
        return null;
    }

    /**
     * Gets m bean object names.
     *
     * @param mbeanName the mbean name
     *
     * @return the m bean object names
     */
    @Override
    public ArrayList<ObjectName> getMBeanObjectNames(String mbeanName) {

        ArrayList<ObjectName> objectNameArrayList = new ArrayList<ObjectName>();
        String userMBeanName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (mbeanName.contains("*")) {
            startsWithWildcard = mbeanName.startsWith("*");
            endsWithWildcard = mbeanName.endsWith("*");
            userMBeanName = mbeanName.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                for (ObjectName beanObjectName : getMBeanObjectNames()) {
                    if (beanObjectName.getCanonicalName().toLowerCase().contains(userMBeanName)) {
                        objectNameArrayList.add(beanObjectName);
                    }
                }
            } else if (startsWithWildcard) {
                for (ObjectName beanObjectName : getMBeanObjectNames()) {
                    if (beanObjectName.getCanonicalName().toLowerCase().endsWith(userMBeanName)) {
                        objectNameArrayList.add(beanObjectName);
                    }
                }
            } else if (endsWithWildcard) {
                for (ObjectName beanObjectName : getMBeanObjectNames()) {
                    if (beanObjectName.getCanonicalName().toLowerCase().startsWith(userMBeanName)) {
                        objectNameArrayList.add(beanObjectName);
                    }
                }
            }
        } else {
            for (ObjectName beanObjectName : getMBeanObjectNames()) {
                if (beanObjectName.getCanonicalName().equalsIgnoreCase(mbeanName)) {
                    objectNameArrayList.add(beanObjectName);
                }
            }
        }
        return objectNameArrayList;
    }

    /**
     * Sets iP.
     *
     * @param ip the jmx_ip
     */
    @Override
    public void setIP(String ip) {
        //TODO: Validate the IP Address
        if (true) {
            Logger.logTrace("Setting JMX IP -> " + ip, getClass(), false);
            PROPERTIES.setJmxIP(ip);
        } else {
            Logger.logMessage("The given IP is not in the valid IP Address format");
        }
    }

    /**
     * Sets jmx_port.
     *
     * @param port the jmx_port
     */
    @Override
    public void setPort(int port) {
        Logger.logTrace("Setting JMX PORT -> " + port, getClass(), false);

        //TODO: Validate the Port Number
        if (true) {
            PROPERTIES.setJmxPort(port);
        } else {
            Logger.logMessage("Port Number is out of range (1-65535)");
        }
    }

    /**
     * The type Singleton holder.
     * <p/>
     * Initialization on Demand Holder (IODH) idiom which requires very little code and
     * has zero synchronization overhead. Zero, as in even faster than volatile.
     * IODH requires the same number of lines of code as plain old synchronization, and it's faster than DCL!
     * <p/>
     * {@code SOURCE: http://blog.crazybob.org/2007/01/lazy-loading-singletons.html}
     */
    static class SingletonHolder {
        /**
         * The Instance.
         */
        static JMXInitializer instance = new DefaultJMXInitializer();
    }
}
