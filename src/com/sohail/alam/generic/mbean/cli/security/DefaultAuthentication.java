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

package com.sohail.alam.generic.mbean.cli.security;

import com.sohail.alam.generic.mbean.cli.logger.Logger;

/**
 * <p/>
 * The {@link DefaultAuthentication} class is responsible for doing the user authentication, which allows
 * further use of the CLI.
 * The {@link DefaultAuthentication} is a default implementation of the {@link Authentication} interface.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 19/5/13
 *          Time: 5:59 PM
 * @since 1.0.0
 */
public class DefaultAuthentication implements Authentication {

    /*
    * Todo - JMX Authentication & Authorization
    * @author sohail.alam
    * @date 31/5/13
    * @time 5:03 PM
    */


    /**
     * The Logged in variable.
     */
    private boolean loggedIn = false;

    /**
     * Instantiates a new DefaultAuthentication.
     */
    private DefaultAuthentication() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Authentication getInstance() {
        Logger.logTrace("Inside constructor", Authentication.class, false);
        return SingletonHolder.instance;
    }

    /**
     * Do authentication.
     *
     * @param username the USERNAME
     * @param password the PASSWORD
     *
     * @return the boolean
     */
    private boolean doAuthentication(String username, String password) {
        Logger.logTrace("Inside doAuthentication() method", getClass(), false);

        if (USERNAME.equalsIgnoreCase(username) && PASSWORD.equalsIgnoreCase(password)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Is logged in.
     *
     * @return the boolean
     */
    @Override
    public boolean isLoggedIn() {
        Logger.logTrace("Inside isLoggedIn() method", getClass(), false);

        return loggedIn;
    }

    /**
     * Login boolean.
     *
     * @param username the username
     * @param password the password
     *
     * @return the boolean
     */
    @Override
    public boolean login(String username, String password) {
        Logger.logTrace("Inside login() method", getClass(), false);

        if (doAuthentication(username, password)) {
            loggedIn = true;
        } else {
            loggedIn = false;
        }

        return loggedIn;
    }

    /**
     * Logout boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean logout() {
        Logger.logTrace("Inside logout() method", getClass(), false);

        loggedIn = false;
        return loggedIn;
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
        static Authentication instance = new DefaultAuthentication();

    }
}
