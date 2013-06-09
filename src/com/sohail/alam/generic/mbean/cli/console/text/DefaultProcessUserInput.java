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

import com.sohail.alam.generic.mbean.cli.CliCommands;
import com.sohail.alam.generic.mbean.cli.DefaultCliCommands;
import com.sohail.alam.generic.mbean.cli.HelperMethods;
import com.sohail.alam.generic.mbean.cli.console.ConsoleIO;
import com.sohail.alam.generic.mbean.cli.console.ProcessUserInput;
import com.sohail.alam.generic.mbean.cli.jmx.*;
import com.sohail.alam.generic.mbean.cli.logger.Logger;
import com.sohail.alam.generic.mbean.cli.security.Authentication;
import com.sohail.alam.generic.mbean.cli.security.DefaultAuthentication;

import javax.management.ObjectName;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * <p/>
 * The Implementation of {@link com.sohail.alam.generic.mbean.cli.console.ProcessUserInput} and {@link CliCommands}
 * defining the methods to process the un-escaped User Inputs,
 * and to return a properly formatted String Buffer which can then be displayed on the Console.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 20/5/13
 *          Time: 7:20 AM
 * @since 1.0.0
 */
public class DefaultProcessUserInput implements ProcessUserInput, CliCommands {

    private final Authentication AUTHENTICATION;
    private final ConsoleIO CONSOLE;
    private final HelperMethods HELPER;
    private final JMXInitializer JMX_INITIALIZER;
    private final AttributeHelper ATTRIBUTE;
    private final OperationHelper OPERATION;
    private String userInput = null;
    private StringBuffer outputBuffer = null;
    private String beanName = null;


    /**
     * Instantiates a new Process user input implementation.
     */
    private DefaultProcessUserInput() {
        AUTHENTICATION = DefaultAuthentication.getInstance();
        CONSOLE = DefaultConsoleIO.getInstance();
        HELPER = HelperMethods.getInstance();
        JMX_INITIALIZER = DefaultJMXInitializer.getInstance();
        ATTRIBUTE = DefaultAttributeHelper.getInstance();
        OPERATION = DefaultOperationHelper.getInstance();
        outputBuffer = new StringBuffer();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ProcessUserInput getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Pre process the input by escaping any unwanted sequence of character(s), trimming the input
     * of unnecessary white-spaces. Also, extracts commands from the user input by delimiting white spaces
     * between commands.
     * Additionally, any information between quotes is preserved as it is (even with white spaces in between)
     *
     * @param input       the input
     * @param commandList the command list
     */
    private void preProcessInput(String input, ArrayList<String> commandList) {
        userInput = input.trim();
        userInput = userInput.replaceAll("\\s+", " ");

        StringTokenizer quoteTokenizer = new StringTokenizer(input, "\"", true);
        StringTokenizer spaceTokenizer;
        String token;

        while (quoteTokenizer.hasMoreElements()) {
            token = quoteTokenizer.nextToken();
            if (token.equals("\"")) {
                token = quoteTokenizer.nextToken();
                commandList.add(token);
                quoteTokenizer.nextToken();
            } else {
                spaceTokenizer = new StringTokenizer(token, " ");
                while (spaceTokenizer.hasMoreElements())
                    commandList.add(spaceTokenizer.nextToken());
            }
        }
    }

    /**
     * Post process is typically done to remove any unwanted
     * {@link com.sohail.alam.generic.mbean.cli.jmx.States} information from the Output Buffer.
     */
    private void postProcess() {
        outputBuffer.trimToSize();
    }

    /**
     * Process string buffer. This is the landing point for the un-escaped, un-processed user input.
     *
     * @param input the input
     *
     * @return the string buffer
     */
    @Override
    public StringBuffer process(String input) {
        outputBuffer = new StringBuffer();
        ArrayList<String> commandList = new ArrayList<String>();
        preProcessInput(input, commandList);

        StringBuffer attributeBuffer = new StringBuffer();
        StringBuffer operationBuffer = new StringBuffer();

        // Convert the User's Command to appropriate Integer Value for Switch Case
        int commandSwitch;
        if (DefaultCliCommands.COMMAND_MAP.containsKey(commandList.get(0).toUpperCase())) {
            commandSwitch = DefaultCliCommands.COMMAND_MAP.get(commandList.get(0).toUpperCase());
        } else {
            commandSwitch = -1;
        }

        // TODO: improve the user command:
        // Use -a to execute command specific to Attributes
        // Use -o to execute command specific to Operations
        // Otherwise do an intelligent search and execute the appropriate command

        /*
         * THIS IS WHERE THE ACTIONS ARE TAKEN ACCORDING TO THE USER'S COMMANDS
         */
        switch (commandSwitch) {

            case ALL_MBEANS_INT:
                showAllBeans();
                break;

            case ALL_ATTRIBUTES_INT:
                showAllAttributes(commandList);
                break;

            case ALL_OPERATIONS_INT:
                showAllOperations(commandList);
                break;

            case CLEAR_INT:
                HELPER.clearScreen();
                break;

            //TODO: improve the close_connection command
            case CLOSE_CONNECTION_INT:
                JMX_INITIALIZER.disconnect();
                break;

            // TODO: Fix Exit - Possibly because of Executors running in background
            case EXIT_INT:
                HELPER.shutdownGracefully();
                break;

            // TODO: improve the GET command : GET ATTRIBUTE_NAME
            // Do not show "No such attribute found, try using wildcard (*) search" more than once
            case GET_INT:
                getAttributeValue(commandList);
                break;

            case HELP_INT:
                showHelp(commandList);
                break;

            case INFO_INT:
                showInfo(commandList);
                break;

            case INVOKE_INT:
                invokeOperation(commandList);
                break;

            case LOGIN_INT:
                doLogin();
                break;

            case LOGOUT_INT:
                doLogout();
                break;

            case NEW_CONNECTION_INT:
                startNewJMXConnection(commandList);
                break;

            // TODO: Add a SEARCH case -> it will search for the existence of a parameter/operation
            case SEARCH_INT:
                break;

            // TODO: improve the SET command
            // Accept wildcard without throwing
            // "There was a conflict resolving your command!" more than once
            case SET_INT:
                setAttributeValue(commandList);
                break;

            // TODO: Complete the default action
            default:
                break;
        }

        postProcess();
        return outputBuffer;
    }

    /**
     * Invoke operation.
     *
     * @param commandList the command list
     */
    private void invokeOperation(ArrayList<String> commandList) {

        String mbeanName = null;
        if (commandList.size() >= 3) {
            int beanPosition;
            try {
                beanPosition = Integer.parseInt(commandList.get(1));
                if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                    mbeanName = JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName();
                } else {
                    outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                }
            } catch (NumberFormatException ex) {
                mbeanName = commandList.get(1);
            }
            String operationName = commandList.get(2);
            ArrayList<String> operationParameters = new ArrayList<String>();
            operationParameters.addAll(commandList);
            operationParameters.remove(0); // Remove the "INVOKE" command
            operationParameters.remove(0); // Remove the MBean Name
            operationParameters.remove(0); // Remove the Operation Name
            outputBuffer = OPERATION.invokeOperation(mbeanName, operationName, operationParameters);
        } else {
            // TODO: improve this section of the INVOKE command
        }
    }

    /**
     * Sets attribute value.
     *
     * @param commandList the command list
     */
    private void setAttributeValue(ArrayList<String> commandList) {
        String mbeanName;
        int beanPosition;
        String attributeName;
        String attributeValue;
        if (commandList.size() >= 4) {
            mbeanName = commandList.get(1);
            attributeName = commandList.get(2);
            attributeValue = commandList.get(3);
            outputBuffer = ATTRIBUTE.checkConflicts(attributeName);
            if (!outputBuffer.toString().contains("\nERROR:\n")) {
                try {
                    beanPosition = Integer.parseInt(mbeanName);
                    if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                        outputBuffer = ATTRIBUTE.setAttributeValue(
                                JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName(),
                                attributeName, attributeValue);
                    } else {
                        outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                    }
                } catch (NumberFormatException ex) {
                    outputBuffer = ATTRIBUTE.setAttributeValue(mbeanName, attributeName, attributeValue);
                }
            }
        } else {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                Logger.logMessage("\nSet new value to a parameter:");
                Logger.logMessage("Enter the MBean Name/Number: ");
                mbeanName = bufferedReader.readLine();
                Logger.logMessage("Enter the Attribute Name: ");
                attributeName = bufferedReader.readLine();
                Logger.logMessage("Enter the Attribute Value: ");
                attributeValue = bufferedReader.readLine();
                outputBuffer = ATTRIBUTE.checkConflicts(attributeName);
                if (!outputBuffer.toString().contains("\nERROR:\n")) {
                    try {
                        beanPosition = Integer.parseInt(mbeanName);
                        if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                            outputBuffer = ATTRIBUTE.setAttributeValue(
                                    JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName(),
                                    attributeName, attributeValue);
                        } else {
                            outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                        }
                    } catch (NumberFormatException ex) {
                        outputBuffer = ATTRIBUTE.setAttributeValue(mbeanName, attributeName, attributeValue);
                    }
                }
            } catch (Exception e) {
                Logger.logException(e, getClass());
            }
        }
    }

    /**
     * Show all operations.
     *
     * @param commandList the command list
     */
    private void showAllOperations(ArrayList<String> commandList) {
        Logger.logTrace("Inside Case: ALL_OPERATIONS_INT", getClass(), false);
        if (commandList.size() > 1) {
            // Display all attributes of the given Bean
            int beanPosition;
            try {
                beanPosition = Integer.parseInt(commandList.get(1));
                if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                    outputBuffer = OPERATION.getAllOperations(JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName());
                } else {
                    outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                }
            } catch (NumberFormatException ex) {
                outputBuffer = OPERATION.getAllOperations(commandList.get(1));
            }
        } else {
            // Display all attributes of all the beans
            outputBuffer = OPERATION.getAllOperations();
        }
    }

    /**
     * Show help.
     *
     * @param commandList the command list
     */
    private void showHelp(ArrayList<String> commandList) {
        // If only HELP was typed then bring the Help Page
        if (commandList.size() == 1) {
            outputBuffer = HELPER.getHelp();
        } else {
            if (commandList.size() > 2) {
                int beanPosition;
                try {
                    beanPosition = Integer.parseInt(commandList.get(1));
                    if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                        beanName = JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName();
                        outputBuffer = ATTRIBUTE.getAttributeHelp(beanName, commandList.get(2));
                        outputBuffer.append(OPERATION.getOperationHelp(beanName, commandList.get(2)));
                    } else {
                        outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                    }
                } catch (Exception ex) {
                    beanName = commandList.get(1);
                    outputBuffer = ATTRIBUTE.getAttributeHelp(beanName, commandList.get(2));
                    outputBuffer.append(OPERATION.getOperationHelp(beanName, commandList.get(2)));
                }
            } else if (commandList.size() == 2) {
                outputBuffer = ATTRIBUTE.getAttributeHelp(commandList.get(1));
                outputBuffer.append(OPERATION.getOperationHelp(commandList.get(1)));
            }
        }
    }

    /**
     * Show info.
     *
     * @param commandList the command list
     */
    private void showInfo(ArrayList<String> commandList) {

        if (commandList.size() > 2) {
            int beanPosition;
            try {
                beanPosition = Integer.parseInt(commandList.get(1));
                if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                    beanName = JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName();
                    outputBuffer = ATTRIBUTE.getAttributeInfo(beanName, commandList.get(2));
                    outputBuffer.append(OPERATION.getOperationInfo(beanName, commandList.get(2)));
                } else {
                    outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                }
            } catch (Exception ex) {
                beanName = commandList.get(1);
                outputBuffer = ATTRIBUTE.getAttributeInfo(beanName, commandList.get(2));
                outputBuffer.append(OPERATION.getOperationInfo(beanName, commandList.get(2)));
            }
        } else if (commandList.size() == 2) {
            outputBuffer = ATTRIBUTE.getAttributeInfo(commandList.get(1));
            outputBuffer.append(OPERATION.getOperationInfo(commandList.get(1)));
        }
    }

    /**
     * Gets attribute value.
     *
     * @param commandList the command list
     */
    private void getAttributeValue(ArrayList<String> commandList) {

        if (commandList.size() > 2) {
            int beanPosition;
            try {
                beanPosition = Integer.parseInt(commandList.get(1));
                if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                    outputBuffer = ATTRIBUTE.getAttributeValue(JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName(), commandList.get(2));
                } else {
                    outputBuffer.append("\nNo such Bean Position Exists => ").append(beanPosition).append("\n");
                }
            } catch (NumberFormatException ex) {
                outputBuffer = ATTRIBUTE.getAttributeValue(commandList.get(1), commandList.get(2));
            }
        } else if (commandList.size() == 2) {
            outputBuffer = ATTRIBUTE.getAttributeValue(commandList.get(1));
        }
    }

    /**
     * Do login.
     */
    private void doLogin() {
        AUTHENTICATION.logout();
        CONSOLE.run();
    }

    /**
     * Do logout.
     */
    private void doLogout() {
        outputBuffer.append("You have successfully logged out!");
        AUTHENTICATION.logout();
        //CONSOLE.run();
    }

    /**
     * Start new jMX connection.
     *
     * @param commandList the command list
     */
    private void startNewJMXConnection(ArrayList<String> commandList) {
        if (commandList.size() == 2) {
            outputBuffer.append("You have not entered the JMX Port! Please try again");
        } else if (commandList.size() >= 3) {
            String ip = commandList.get(1);
            int port = 0;
            try {
                port = Integer.parseInt(commandList.get(2));
            } catch (NumberFormatException ex) {
                outputBuffer.append("Please enter a valid Port Number");
            }
            JMX_INITIALIZER.disconnect();
            JMX_INITIALIZER.setIP(ip);
            JMX_INITIALIZER.setPort(port);
            JMX_INITIALIZER.connect();
        } else if (commandList.size() == 1) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                int port = 0;
                String ip;
                //                    AUTHENTICATION.logout();
                //                    Logger.logMessage("You have successfully logged out!");
                JMX_INITIALIZER.disconnect();
                Logger.logMessage("\nSet up a new JMX Connection:");
                Logger.logMessage("Enter the JMX IP: ");
                ip = bufferedReader.readLine();
                JMX_INITIALIZER.setIP(ip);
                Logger.logMessage("Enter the JMX Port: ");
                String tempPort = bufferedReader.readLine();
                port = Integer.parseInt(tempPort);
                JMX_INITIALIZER.setPort(port);
                JMX_INITIALIZER.connect();
            } catch (Exception e) {
                Logger.logException(e, getClass());
            }
        }
    }

    /**
     * Show all attributes.
     *
     * @param commandList the command list
     */
    private void showAllAttributes(ArrayList<String> commandList) {
        Logger.logTrace("Inside Case: ALL_ATTRIBUTES_INT", getClass(), false);
        if (commandList.size() > 1) {
            // Display all attributes of the given Bean
            int beanPosition;
            try {
                beanPosition = Integer.parseInt(commandList.get(1));
                if (JMX_INITIALIZER.getMBeanObjectNames().size() > beanPosition) {
                    outputBuffer = ATTRIBUTE.getAllAttributes(JMX_INITIALIZER.getMBeanObjectNames().get(beanPosition).getCanonicalName());
                } else {
                    outputBuffer.append("\n No such Bean Position Exists => ").append(beanPosition);
                }
            } catch (NumberFormatException ex) {
                outputBuffer = ATTRIBUTE.getAllAttributes(commandList.get(1));
            }
        } else {
            // Display all attributes of all the beans
            outputBuffer = ATTRIBUTE.getAllAttributes();
        }
    }

    /**
     * Show all beans.
     */
    private void showAllBeans() {
        ArrayList<ObjectName> allBeans = JMX_INITIALIZER.getMBeanObjectNames();
        outputBuffer
                .append("\n")
                .append("==================================================================================================\n")
                .append("All Available MBeans :")
                .append("\n")
                .append("--------------------------------------------------------------------------------------------------\n")
                .append(String.format("%-10s", "Position"))
                .append(" | ")
                .append(String.format("%-40s", "Bean Name"))
                .append("\n")
                .append("==================================================================================================\n");
        for (int i = 0; i < allBeans.size(); i++) {
            outputBuffer
                    .append(String.format("%-10s", i))
                    .append(" | ")
                    .append(String.format("%-36s", allBeans.get(i)))
                    .append("\n");
        }
        outputBuffer.append("--------------------------------------------------------------------------------------------------\n");
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
        static ProcessUserInput instance = new DefaultProcessUserInput();
    }
}
