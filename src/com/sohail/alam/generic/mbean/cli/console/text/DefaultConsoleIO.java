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
import com.sohail.alam.generic.mbean.cli.console.ConsoleIO;
import com.sohail.alam.generic.mbean.cli.jmx.DefaultJMXInitializer;
import com.sohail.alam.generic.mbean.cli.jmx.JMXInitializer;
import com.sohail.alam.generic.mbean.cli.logger.Logger;
import com.sohail.alam.generic.mbean.cli.security.Authentication;
import com.sohail.alam.generic.mbean.cli.security.DefaultAuthentication;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;

/**
 * <p/>
 * This is the Implementation of {@link com.sohail.alam.generic.mbean.cli.console.ConsoleIO}.
 * This class is responsible for the Input/Output from the Console.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 19/5/13
 *          Time: 5:55 PM
 * @since 1.0.0
 */
public class DefaultConsoleIO implements ConsoleIO {

    /**
     * The AUTHENTICATION.
     */
    private final Authentication AUTHENTICATION = DefaultAuthentication.getInstance();
    /**
     * The Input contains the un-escaped User's input.
     */
    private String input = null;
    /**
     * The Buffered reader contains the raw input from the User.
     */
    private BufferedReader bufferedReader = null;
    /**
     * The Enter key pressed counter. It maintains the state of the number of times the user presses
     * the Enter Key consecutively. It can be useful in many cases, such as to display the HELP after
     * say 5 consecutive Enter Key presses.
     */
    private int enterKeyPressed = 0;

    /**
     * Instantiates a new Console iO implementation.
     */
    private DefaultConsoleIO() {
        DefaultConsoleType.getInstance();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
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
     * Run forever.
     * An infinite loop that listens to User's Input and displays the results.
     */
    private void runForever() {
        Logger.logTrace("Inside runForever() method", getClass(), false);

        Logger.logMessage("Please Wait while we connect to JMX Server\n");

        // Connect to MBean Server
        JMXInitializer initializer = DefaultJMXInitializer.getInstance();
        initializer.connect();

        while (true) {
            if (AUTHENTICATION.isLoggedIn()) {
                Logger.logTrace("Successfully Logged In", getClass(), false);
                bringUserInterface();
                if (input == null) {
                    break;
                }
            } else {
                if (input == null) {
                    break;
                }
                System.err.println("\nYou are not authorized!! Please try again...\n");
                doAuthentication();
            }
        }
    }

    /**
     * This method internally calls the
     * {@link DefaultConsoleIO#doAuthentication()} method to
     * authenticate the user and on success calls the
     * {@link DefaultConsoleIO#runForever()} method, otherwise
     * keeps prompting the user to enter the correct username/password combination.
     */
    @Override
    public void run() {
        Logger.logTrace("Inside run() method", getClass(), false);

        AUTHENTICATION.login(AUTHENTICATION.USERNAME, AUTHENTICATION.PASSWORD);
        /*
         * TODO: Authentication Bypassed
         * DESCRIPTION: Do not bypass authentication once ready for production
         * DATE: 23/5/13
         * TIME: 10:18 PM
         */

        //        doAuthentication();
        runForever();
    }

    /**
     * This method authenticates the User.
     */
    private void doAuthentication() {
        Logger.logTrace("Inside doAuthentication() method", getClass(), false);
        try {
            Console console = System.console();
            System.err.println();
            System.err.print("Please Type in your Username: ");
            input = bufferedReader.readLine();
            String username = input;
            System.err.println();
            String password;
            if (console != null) {
                password = new String(console.readPassword("Please Type in your Password: "));
            } else {
                System.err.print("Please Type in your Password: ");
                input = bufferedReader.readLine();
                password = input;
            }
            AUTHENTICATION.login(username, password);
        } catch (Exception ex) {
            Logger.logException(ex, getClass());
        }
    }

    /**
     * Bring user interface.
     */
    private void bringUserInterface() {
        Logger.logTrace("Inside bringUserInterface() method", getClass(), false);

        try {
            System.out.println();
            System.out.print("CLI>> ");
            input = bufferedReader.readLine();
            if (input != null) {
                if (input.length() == 0) {
                    enterKeyPressed++;
                    if (enterKeyPressed == 5) {
                        System.out.println(DefaultProcessUserInput.getInstance().process(CliCommands.HELP));
                        enterKeyPressed = 0;
                    }
                } else {
                    System.out.println(DefaultProcessUserInput.getInstance().process(input));
                }
            }
        } catch (Exception ex) {
            Logger.logException(ex, getClass());
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
        static ConsoleIO instance = new DefaultConsoleIO();
    }
}
