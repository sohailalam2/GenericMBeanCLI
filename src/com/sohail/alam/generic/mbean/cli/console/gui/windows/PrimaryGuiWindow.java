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

package com.sohail.alam.generic.mbean.cli.console.gui.windows;

import com.googlecode.lanterna.gui.Theme;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.TextArea;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.terminal.Terminal;
import com.sohail.alam.generic.mbean.cli.CliCommands;
import com.sohail.alam.generic.mbean.cli.HelperMethods;
import com.sohail.alam.generic.mbean.cli.console.gui.GuiConsoleHelper;
import com.sohail.alam.generic.mbean.cli.console.gui.GuiProcessUserInput;
import com.sohail.alam.generic.mbean.cli.security.Authentication;
import com.sohail.alam.generic.mbean.cli.security.DefaultAuthentication;

/**
 * User: Sohail Alam
 * Version: 1.0.0
 * Date: 1/6/13
 * Time: 9:15 PM
 */
public class PrimaryGuiWindow extends Window {

    /**
     * The AUTHENTICATION.
     */
    private final Authentication AUTHENTICATION = DefaultAuthentication.getInstance();
    /**
     * The GUI_HELPER.
     */
    private final GuiConsoleHelper GUI_HELPER = GuiConsoleHelper.getInstance();
    /**
     * The User input.
     */
    private String userInput = new String("");
    /**
     * The Enter key pressed counter. It maintains the state of the number of times the user presses
     * the Enter Key consecutively. It can be useful in many cases, such as to display the HELP after
     * say 5 consecutive Enter Key presses.
     */
    private int enterKeyPressed = 0;

    /**
     * Instantiates a new Primary mbean window.
     */
    private PrimaryGuiWindow() {
        super("Welcome to Generic CLI");
        addComponent(Login.getInstance().createLoginForm());
        setSoloWindow(true);

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PrimaryGuiWindow getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Do exit.
     */
    public void doExit() {

        String message = "Thank you for using this Generic MBean CLI. Hope you enjoyed it.";
        MessageBox.showMessageBox(getInstance().getOwner(), "Goodbye User", message);
        close();
        HelperMethods.getInstance().shutdownGracefully();
    }

    /**
     * Do authentication.
     *
     * @param info            the info
     * @param usernameTextBox the username text box
     * @param passwordTextBox the password text box
     */
    public void doAuthentication(Label info, TextBox usernameTextBox, TextBox passwordTextBox) {

        AUTHENTICATION.login(usernameTextBox.getText(), passwordTextBox.getText());

        // If Authentication is successful then show the CLI Interface
        if (AUTHENTICATION.isLoggedIn()) {
            getInstance().removeAllComponents();
            getInstance().addComponent(CliGui.getInstance().drawGui());
        }
        // Otherwise show an error message
        else {
            final String message = "Wrong Username/Password combination!! Please try again";
            GUI_HELPER.displayMessage(info, message, Terminal.Color.RED, Theme.Category.SHADOW);
        }
    }

    /**
     * CliGui command button action.
     *
     * @param results the results
     * @param command the command
     */
    public void cliCommandButtonAction(TextArea results, String command) {
        userInput = command;
        if (userInput != null) {
            if (userInput.length() == 0) {
                enterKeyPressed++;
                if (enterKeyPressed == 5) {
                    GUI_HELPER.displayMessage(results, GuiProcessUserInput.getInstance().process(CliCommands.HELP).toString());
                    enterKeyPressed = 0;
                }
            } else {
                GUI_HELPER.displayMessage(results, GuiProcessUserInput.getInstance().process(userInput));
            }
        }
    }

    /**
     * Gets user input.
     *
     * @return the user input
     */
    public String getUserInput() {
        return userInput;
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
        static PrimaryGuiWindow instance = new PrimaryGuiWindow();
    }
}
