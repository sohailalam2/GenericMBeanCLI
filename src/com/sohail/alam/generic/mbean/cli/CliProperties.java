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

package com.sohail.alam.generic.mbean.cli;

import com.sohail.alam.generic.mbean.cli.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * <p/>
 * This {@link CliProperties} loads the configuration file
 * and the value of {@code #jmxIP} and {@code jmxPort}
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 21/5/13
 *          Time: 11:24 PM
 * @since 1.0.0
 */
public class CliProperties {

    /**
     * The Log level.
     */
    private String logLevel = "DEBUG";
    /**
     * The Jmx iP.
     */
    private String jmxIP;
    /**
     * The Jmx jmx_port.
     */
    private int jmxPort;
    /**
     * The Use console mbean.
     */
    private boolean useConsoleGui = true;

    /**
     * Instantiates a new CliGui properties.
     */
    private CliProperties() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CliProperties getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Initialize Properties.
     */
    public void initialize() {
        Properties proxyProperties = new Properties();
        File config = new File("configuration/config.properties");
        try {
            proxyProperties.load(new FileInputStream(config));
            if ("INFO".equalsIgnoreCase(proxyProperties.getProperty("log_level").trim())) {
                setLogLevel("INFO");
            } else {
                setLogLevel("DEBUG");
            }
            setJmxIP(proxyProperties.getProperty("jmx_ip").trim());
            setJmxPort(Integer.parseInt(proxyProperties.getProperty("jmx_port").trim()));
            setUseConsoleGui(Boolean.parseBoolean(proxyProperties.getProperty("use_console_gui").trim()));
        } catch (FileNotFoundException ex) {
            Logger.logException(ex, getClass());
            // Exit the system
            System.exit(-1);
        } catch (NumberFormatException ex) {
            Logger.logException(ex, getClass());
        } catch (Exception ex) {
            Logger.logException(ex, getClass());
        }

        // Uncomment this if you need to trace internal working...
        // REMEMBER to turn on all the trace logs that you need by enabling individual trace logs
        logLevel = "TRACE";
    }

    /**
     * Gets JMX IP.
     *
     * @return the JMX IP
     */
    public String getJmxIP() {
        return jmxIP;
    }

    /**
     * Set the JMX IP.
     *
     * @param jmxIP the JMX IP
     */
    public void setJmxIP(String jmxIP) {
        Logger.logTrace("Setting up JMX IP -> " + jmxIP, getClass(), false);
        this.jmxIP = jmxIP;
    }

    /**
     * Gets JMX PORT.
     *
     * @return the JMX PORT
     */
    public int getJmxPort() {
        return jmxPort;
    }

    /**
     * Sets JMX PORT.
     *
     * @param jmxPort the JMX PORT
     */
    public void setJmxPort(int jmxPort) {
        this.jmxPort = jmxPort;
    }

    /**
     * Gets log level.
     *
     * @return the log level
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * Sets log level.
     *
     * @param logLevel the log level
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * Is use console mbean.
     *
     * @return the boolean
     */
    public boolean isUseConsoleGui() {
        return useConsoleGui;
    }

    /**
     * Sets use console mbean.
     *
     * @param useConsoleGui the use console mbean
     */
    public void setUseConsoleGui(boolean useConsoleGui) {
        this.useConsoleGui = useConsoleGui;
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
        static CliProperties instance = new CliProperties();
    }
}
