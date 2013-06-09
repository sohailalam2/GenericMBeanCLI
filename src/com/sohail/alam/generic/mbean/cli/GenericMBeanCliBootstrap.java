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

import com.sohail.alam.generic.mbean.cli.console.gui.GuiConsoleIO;
import com.sohail.alam.generic.mbean.cli.console.text.DefaultConsoleIO;

/**
 * <p/>
 * This {@link GenericMBeanCliBootstrap} is the {@code main} class of the application.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 19/5/13
 *          Time: 5:44 PM
 * @since 1.0.0
 */
public class GenericMBeanCliBootstrap {

    /**
     * The entry point of application.
     *
     * @param arguments the input arguments
     */
    public static void main(String[] arguments) {

        CliProperties.getInstance().initialize();

        /*
         * TODO: Start the Scheduler
         * DESCRIPTION: Background task for refreshing the MBean Maps
         * DATE: 24/5/13
         * TIME: 12:55 AM
         */

//        // Update Task
//        BackgroundTask.getINSTANCE().runJMXUpdate();

        if (CliProperties.getInstance().isUseConsoleGui()) {
            GuiConsoleIO.getInstance().run();
        } else {
            System.err.println(showWelcomeScreen());
            DefaultConsoleIO.getInstance().run();
        }
    }

    /**
     * Show welcome screen.
     *
     * @return the string buffer
     */
    public static StringBuffer showWelcomeScreen() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("\n**********************************************************************");
        buffer.append("\n**********                                                  **********");
        buffer.append("\n**********         WELCOME  TO  GENERIC  MBEAN  CLI         **********");
        buffer.append("\n**********                                                  **********");
        buffer.append("\n**********************************************************************");

        return buffer;
    }
}
