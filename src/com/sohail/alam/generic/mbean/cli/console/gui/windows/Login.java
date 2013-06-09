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

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.component.*;
import com.googlecode.lanterna.gui.layout.BorderLayout;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Created with IntelliJ IDEA.
 * User: sohail.alam
 * Date: 1/6/13
 * Time: 12:42 PM
 */
public class Login {

    /**
     * Instantiates a new Login.
     */
    private Login() {

    }

    /**
     * Get instance.
     *
     * @return the login
     */
    public static Login getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Create login form.
     *
     * @return the panel
     */
    public Panel createLoginForm() {
        // Primary Panel
        Panel verticalPanel = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);

        // Login Form Components
        EmptySpace emptySpace = new EmptySpace();
        final Label info = new Label("Please Enter Your Login Credentials", Terminal.Color.BLACK, true);
        Label usernameLabel = new Label("USERNAME: ", Terminal.Color.RED, true);
        final TextBox usernameTextBox = new TextBox("", 100);
        Label passwordLabel = new Label("PASSWORD: ", Terminal.Color.RED, true);
        final TextBox passwordTextBox = new PasswordBox("", 100);
        Button loginButton = new Button("Login", new Action() {
            @Override
            public void doAction() {
                PrimaryGuiWindow.getInstance().doAuthentication(info, usernameTextBox, passwordTextBox);
            }
        });
        Button exitButton = new Button("Exit", new Action() {
            @Override
            public void doAction() {
                PrimaryGuiWindow.getInstance().doExit();
            }
        });
        Panel horizontalPanel = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);

        // Add the buttons horizontally
        horizontalPanel.addComponent(loginButton);
        horizontalPanel.addComponent(emptySpace);
        horizontalPanel.addComponent(exitButton);

        // Add the form components vertically
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(info);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(usernameLabel);
        verticalPanel.addComponent(usernameTextBox);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(passwordLabel);
        verticalPanel.addComponent(passwordTextBox);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(horizontalPanel, BorderLayout.CENTER);

        // Return the primary panel
        return verticalPanel;
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
        static Login instance = new Login();
    }
}
