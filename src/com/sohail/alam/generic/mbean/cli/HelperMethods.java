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

import com.sohail.alam.generic.mbean.cli.console.gui.windows.CliGui;

import java.util.StringTokenizer;

/**
 * <p/>
 * This class contains the primary helper methods for displaying the HELP information,
 * EXITing the application and CLEARing the screen.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 20/5/13
 *          Time: 7:12 AM
 * @since 1.0.0
 */
public class HelperMethods {

    /**
     * Instantiates a new Helper methods.
     */
    private HelperMethods() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static HelperMethods getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Gets help.
     *
     * @return the help
     */
    public StringBuffer getHelp() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("\nDISPLAYING HELP!!");

        buffer
                .append("\nALL POSSIBLE COMMANDS\n\n")
                .append("\n---------------------------------------------------------------------------------------------------\n")
                .append("\n")

                        // ALL_ATTRIBUTES
                .append(helpFormatter("ALL_ATTRIBUTES [MBEAN_NAME/MBEAN_POSITION]",
                        "ALL_ATTRIBUTES myMBean",
                        "Brings out a table describing all the available attributes\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean\n" +
                                "otherwise searches the all MBeans\n"))

                        //ALL_MBEANS
                .append(helpFormatter("ALL_MBEANS",
                        null,
                        "Displays a list of all the available user MBeans for the existing JMX Connection\n"))

                        // ALL_OPERATIONS [MBEAN_NAME/MBEAN_POSITION]
                .append(helpFormatter("ALL_OPERATIONS [MBEAN_NAME/MBEAN_POSITION]",
                        "ALL_OPERATIONS myMBean",
                        "Brings out a table describing all the available operations\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean\n" +
                                "otherwise searches the all MBeans\n"))

                        //CLEAR
                .append(helpFormatter("CLEAR",
                        null,
                        "Clears the Scree\n"))

                        //CLOSE_CONNECTION
                .append(helpFormatter("CLOSE_CONNECTION",
                        null,
                        "Closes the existing JMX Connection\n"))

                        //EXIT
                .append(helpFormatter("EXIT",
                        null,
                        "Gracefully closes all existing connections and exits the application\n"))

                        //GET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME
                .append(helpFormatter("GET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME",
                        "GET myMBean myAttribute",
                        "Gets and displays the value set for the given attribute\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean,\n" +
                                "otherwise searches the all MBeans\n"))

                        //HELP
                .append(helpFormatter("HELP",
                        null,
                        "Brings out the general help page\n"))

                        //HELP [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME
                .append(helpFormatter("HELP [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME",
                        "HELP myMBean myAttribute",
                        "Brings out the detailed information about the Attribute/Operation\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean,\n" +
                                "otherwise searches the all MBeans\n"))

                        //INFO [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME
                .append(helpFormatter("INFO [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME",
                        "INFO myMBean myAttribute",
                        "Brings out the description or short information about the Attribute/Operation\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean,\n" +
                                "otherwise searches the all MBeans\n"))

                        //INVOKE [MBEAN_NAME/MBEAN_POSITION] OPERATION_NAME [PARAMETER_VALUES...]
                .append(helpFormatter("INVOKE [MBEAN_NAME/MBEAN_POSITION] OPERATION_NAME [PARAMETER_VALUES...]",
                        "INVOKE myMBean invokeMyOperation Sohail",
                        "Invokes an MBean operation.\n" +
                                "Also optionally one can pass parameter values if that operation takes any\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean,\n" +
                                "otherwise searches the all MBeans\n"))

                        //LOGIN
                .append(helpFormatter("LOGIN",
                        null,
                        "Logs out the currently logged in user and tries to login the default user\n"))

                        //LOGOUT
                .append(helpFormatter("LOGOUT",
                        null,
                        "Logs out the currently logged in user and brings out the Authentication Screen\n"))

                        //NEW_CONNECTION [IP PORT]
                .append(helpFormatter("NEW_CONNECTION [IP PORT]",
                        "NEW_CONNECTION 127.0.0.1 5566",
                        "Closes the existing JMX Connection and brings out the screen to enter\n" +
                                "JMX IP and JMX PORT information\n" +
                                "Optionally one may enter the IP and Port information alongside the command\n"))

                        //SET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME VALUE
                .append(helpFormatter("SET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME VALUE",
                        "SET myMBean setMyName Sohail",
                        "Sets the value for the given attribute\n" +
                                "[MBEAN_NAME/MBEAN_POSITION] is optional, to search within the given MBean,\n" +
                                "otherwise searches the all MBeans\n"))

                .append("\n");
        return buffer;
    }

    /**
     * Help formatter.
     *
     * @param command the command
     * @param example the example
     * @param details the details
     *
     * @return the string buffer
     */
    private StringBuffer helpFormatter(String command, String example, String details) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("COMMAND: ").append(String.format("%-5s", "")).append(command.trim()).append("\n\n");
        if (example != null) {
            buffer.append("EXAMPLE: ").append(String.format("%-5s", "")).append(example.trim()).append("\n\n");
        }
        buffer.append("DETAILS: ");
        int i = 0;
        for (String line : details.split("\n")) {
            if (i == 0) {
                buffer.append(String.format("%-5s", "")).append(line.trim()).append("\n");
            } else {
                buffer.append(String.format("%-14s", "")).append(line.trim()).append("\n");
            }
            i++;
        }
        buffer.append("\n...................................................................................................\n")
                .append("\n");
        return buffer;
    }

    /**
     * Help formatter.
     *
     * @param command the command
     * @param details the details
     *
     * @return the string buffer
     */
    private StringBuffer helpFormatter(String command, String details) {

        StringBuffer buffer = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(details, "\n");

        buffer.append(String.format("%-75s", command.trim()));

        while (tokenizer.hasMoreTokens()) {
            buffer.append(String.format("%-100s", " | " + tokenizer.nextToken().trim()))
                    .append(String.format("%-75s", "\n"));
        }

        buffer.append("\n---------------------------------------------------------------------------------------------------------------------------------\n");

        return buffer;
    }

    /**
     * Exit the Application.
     *
     * @param exitState the exit state, defaults to zero(0)
     */
    public void exit(int exitState) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("\nForcefully Shutting down CLI");
        if (CliProperties.getInstance().isUseConsoleGui()) {
            System.exit(exitState);
        } else {
            System.err.println(buffer.toString());
            System.exit(exitState);
        }
    }

    /**
     * Clear screen.
     */
    public void clearScreen() {
        if (CliProperties.getInstance().isUseConsoleGui()) {
            CliGui.getInstance().getResults().clear();
        } else {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Shutdown gracefully.
     */
    public void shutdownGracefully() {
        exit(0);
    }

    /*
     * TODO: Try shutting down gracefully
     * DESCRIPTION: Disconnect from the JMX Server and try to shutdown gracefully
     * DATE: 21/5/13
     * TIME: 11:57 PM
     */

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
        static HelperMethods instance = new HelperMethods();
    }
}
