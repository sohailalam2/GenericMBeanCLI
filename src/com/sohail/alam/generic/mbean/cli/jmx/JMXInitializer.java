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

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import java.util.ArrayList;

/**
 * <p/>
 * xxxxxxxxxx
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 19/5/13
 *          Time: 7:16 PM
 * @since 1.0.0
 */
public interface JMXInitializer {
    /**
     * Connect boolean.
     *
     * @return the boolean
     */
    boolean connect();

    /**
     * Disconnect boolean.
     *
     * @return the boolean
     */
    boolean disconnect();

    /**
     * Gets jmxc.
     *
     * @return the jmxc
     */
    JMXConnector getJmxc();

    /**
     * Gets mbsc.
     *
     * @return the mbsc
     */
    MBeanServerConnection getMbsc();

    /**
     * Sets iP.
     *
     * @param ip the jmx_ip
     */
    void setIP(String ip);

    /**
     * Sets jmx_port.
     *
     * @param port the jmx_port
     */
    void setPort(int port);

    /**
     * Reload the Data Structure containing the Bean Information
     */
    void reload();

    /**
     * Gets bean object names.
     *
     * @return the bean object names
     */
    ArrayList<ObjectName> getMBeanObjectNames();

    /**
     * Gets m bean object name.
     *
     * @param mbeanName the mbean name
     *
     * @return the m bean object name
     */
    ObjectName getMBeanObjectName(String mbeanName);

    /**
     * Gets m bean object names.
     *
     * @param mbeanName the mbean name
     *
     * @return the m bean object names
     */
    ArrayList<ObjectName> getMBeanObjectNames(String mbeanName);
}
