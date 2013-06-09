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

package com.sohail.alam.generic.mbean.cli.console.gui;

import com.sohail.alam.generic.mbean.cli.console.ConsoleIO;

/**
 * Created with IntelliJ IDEA.
 * User: sohail.alam
 * Date: 1/6/13
 * Time: 3:47 PM
 */
public class GuiConsoleIO implements ConsoleIO {


    /**
     * Instantiates a new Primary window.
     */
    private GuiConsoleIO() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConsoleIO getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * This method brings out the CLI user Interface to the user.
     */
    @Override
    public void run() {
        // Simply instantiate the GuiConsoleType
        GuiConsoleType.getInstance();
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
        static ConsoleIO instance = new GuiConsoleIO();
    }
}
