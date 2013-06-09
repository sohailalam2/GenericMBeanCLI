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

package com.sohail.alam.generic.mbean.cli.utils;

import com.sohail.alam.generic.mbean.cli.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * This {@link Validate} class is responsible for validating a given IP to a\
 * IPv4 or IPv6 standard. Also, it validates a proper range for given Port.
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 22/5/13
 *          Time: 4:15 PM
 * @since 1.0.0
 */
public class Validate {

    /**
     * The constant ipv4Pattern.
     */
    private static final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    /**
     * The constant ipv6Pattern.
     */
    private static final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";
    /**
     * The constant VALID_IPV4_PATTERN.
     */
    private static Pattern VALID_IPV4_PATTERN = null;
    /**
     * The constant VALID_IPV6_PATTERN.
     */
    private static Pattern VALID_IPV6_PATTERN = null;

    /**
     * Private Constructor for Singleton Class
     */
    private Validate() {
        Logger.logTrace("Inside Constructor", Validate.class, false);
        try {
            VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
            VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
        } catch (PatternSyntaxException e) {
            Logger.logException(e, Validate.class);
        }
    }

    /**
     * Get the Singleton Instance of the Class Validate
     *
     * @return Singleton Object
     */
    public static Validate getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Determine if the given string is a valid IPv4 or IPv6 address. This
     * method uses pattern matching to see if the given string could be a valid
     * IP address.
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     *                  it could be a valid IP address.
     *
     * @return <code>true</code> if the string is a value that is a valid IP
     *         address, <code>false</code> otherwise.
     */
    public boolean isValidIpAddress(String ipAddress) {
        Logger.logTrace("Validating IP Address -> " + ipAddress, getClass(), false);
        if (isIPV6Address(ipAddress)) {
            return true;
        } else if (isIPV4Address(ipAddress)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether the given IP is of IPv4 or not
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     *                  it could be a valid IP address.
     *
     * @return <code>true</code> if the string is a value that is a valid IP
     *         address, <code>false</code> otherwise.
     */
    public boolean isIPV4Address(String ipAddress) {
        Logger.logTrace("Validating IPV4 Address -> " + ipAddress, getClass(), false);
        Matcher m1 = Validate.VALID_IPV4_PATTERN.matcher(ipAddress);
        return m1.matches();
    }

    /**
     * Check whether the given IP is of IPv6 or not
     *
     * @param ipAddress A string that is to be examined to verify whether or not
     *                  it could be a valid IP address.
     *
     * @return <code>true</code> if the string is a value that is a valid IP
     *         address, <code>false</code> otherwise.
     */
    public boolean isIPV6Address(String ipAddress) {
        Logger.logTrace("Validating IPV6 Address -> " + ipAddress, getClass(), false);
        Matcher m2 = Validate.VALID_IPV6_PATTERN.matcher(ipAddress);
        return m2.matches();
    }

    /**
     * Validate a given integer to be a port number in the proper range
     *
     * @param port the port
     *
     * @return true /false
     */
    public boolean isValidPort(int port) {
        Logger.logTrace("Validating Port Number -> " + port, getClass(), false);
        if (port <= 0 || port > 35565) {
            return false;
        }
        return true;
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
        static Validate instance = new Validate();
    }
}
