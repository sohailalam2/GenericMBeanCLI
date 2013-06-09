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

package com.sohail.alam.generic.mbean.cli.console.text;

import com.sohail.alam.generic.mbean.cli.ShutdownHook;
import com.sohail.alam.generic.mbean.cli.console.ConsoleType;
import com.sohail.alam.generic.mbean.cli.logger.Logger;

import java.io.IOException;

/**
 * <p/>
 * The Implementation of {@link com.sohail.alam.generic.mbean.cli.console.ConsoleType}.
 * It defines the platform this application is running on.
 * Also, provides a means to get the system Runtime, which can be useful to run System Level Commands.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 19/5/13
 *          Time: 5:46 PM
 * @since 1.0.0
 */
public class DefaultConsoleType implements ConsoleType {

    /**
     * The System RUNTIME.
     */
    final Runtime RUNTIME = Runtime.getRuntime();
    /**
     * Is this the Windows platform?
     */
    private final boolean WINDOWS;
    /**
     * Is this the Unix/Linux platform?
     */
    private final boolean UNIX;

    /**
     * Instantiates a new Console type implementation.
     */
    private DefaultConsoleType() {
        RUNTIME.addShutdownHook(new ShutdownHook());
        String os = System.getProperty("os.name").toLowerCase();
        // linux or unix
        UNIX = (os.contains("nix") || os.contains("nux"));
        // windows
        WINDOWS = os.contains("win");
        try {
            Process process = null;
            if (isWindows()) {
                process = RUNTIME.exec("cmd.exe");
            } else if (isUnix()) {
                process = RUNTIME.exec("bash");
            }
        } catch (IOException ex) {
            Logger.logException(ex, getClass());
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConsoleType getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Is this the Windows platform?
     *
     * @return the boolean
     */
    @Override
    public boolean isWindows() {
        return WINDOWS;
    }

    /**
     * Is this the Unix/Linux platform?
     *
     * @return the boolean
     */
    @Override
    public boolean isUnix() {
        return UNIX;
    }

    /**
     * Gets the System RUNTIME.
     *
     * @return the RUNTIME
     */
    public Runtime getRUNTIME() {
        return RUNTIME;
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
        static ConsoleType instance = new DefaultConsoleType();
    }
}
