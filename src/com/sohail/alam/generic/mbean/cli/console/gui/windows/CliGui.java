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
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.sohail.alam.generic.mbean.cli.GenericMBeanCliBootstrap;

/**
 * Created with IntelliJ IDEA.
 * User: sohail.alam
 * Date: 1/6/13
 * Time: 2:57 PM
 */
public class CliGui {

    /**
     * The Cli command text box.
     */
    final TextBox cliCommandTextBox;
    /**
     * The PRIMARY_WINDOW.
     */
    private final PrimaryGuiWindow PRIMARY_WINDOW = PrimaryGuiWindow.getInstance();
    /**
     * The Results.
     */
    private final TextArea results;

    /**
     * Instantiates a new Cli mbean.
     */
    private CliGui() {

        TerminalSize size = new TerminalSize(100, 100);
        results = new TextArea(size, GenericMBeanCliBootstrap.showWelcomeScreen().toString());
        getResults().getHotspot();
        cliCommandTextBox = new TextBox("", 100);

    }

    /**
     * Get instance.
     *
     * @return the cli mbean
     */
    public static CliGui getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Draw mbean.
     *
     * @return the panel
     */
    public Panel drawGui() {

        // Primary Panel
        Panel verticalPanel = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);

        // Login Form Components
        EmptySpace emptySpace;
        Label resultsLabel;
        Label cliCommandLabel;
        Button loginButton;
        Button exitButton;
        Panel horizontalCliCommandPanel;
        Panel horizontalButtonsPanel;

        emptySpace = new EmptySpace();
        resultsLabel = new Label("RESULTS: ", 100, Terminal.Color.RED, true);
        cliCommandLabel = new Label("CLI COMMAND>>", Terminal.Color.RED, true);
        loginButton = new Button("Run", new Action() {
            @Override
            public void doAction() {
                PRIMARY_WINDOW.cliCommandButtonAction(getResults(), cliCommandTextBox.getText());
            }
        });
        exitButton = new Button("Exit", new Action() {
            @Override
            public void doAction() {
                PRIMARY_WINDOW.doExit();
            }
        });
        horizontalCliCommandPanel = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);
        horizontalButtonsPanel = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);

        // Add the Label and TextBox
        horizontalCliCommandPanel.addComponent(cliCommandLabel);
        horizontalCliCommandPanel.addComponent(emptySpace);
        horizontalCliCommandPanel.addComponent(cliCommandTextBox);

        // Add the buttons horizontally
        horizontalButtonsPanel.addComponent(loginButton);
        horizontalButtonsPanel.addComponent(emptySpace);
        horizontalButtonsPanel.addComponent(exitButton);

        // Add the form components vertically
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(resultsLabel);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(getResults(), LinearLayout.GROWS_VERTICALLY);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(horizontalCliCommandPanel, LinearLayout.GROWS_HORIZONTALLY);
        verticalPanel.addComponent(horizontalButtonsPanel);

        // Return the Primary Panel
        return verticalPanel;
    }

    /**
     * Gets results.
     *
     * @return the results
     */
    public TextArea getResults() {
        return results;
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
        static CliGui instance = new CliGui();
    }
}
