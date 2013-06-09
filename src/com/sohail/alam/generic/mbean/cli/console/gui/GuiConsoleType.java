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

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.sohail.alam.generic.mbean.cli.GenericMBeanCliBootstrap;
import com.sohail.alam.generic.mbean.cli.console.ConsoleType;
import com.sohail.alam.generic.mbean.cli.console.gui.windows.GenericMBeanCliGuiTheme;
import com.sohail.alam.generic.mbean.cli.console.gui.windows.PrimaryGuiWindow;
import com.sohail.alam.generic.mbean.cli.console.text.DefaultConsoleIO;
import com.sohail.alam.generic.mbean.cli.jmx.DefaultJMXInitializer;
import com.sohail.alam.generic.mbean.cli.jmx.JMXInitializer;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: sohail.alam
 * Date: 1/6/13
 * Time: 2:27 PM
 */
public class GuiConsoleType implements ConsoleType {

    /**
     * Is this the Windows platform?
     */
    private final boolean WINDOWS;
    /**
     * Is this the Unix/Linux platform?
     */
    private final boolean UNIX;
    /**
     * The TERMINAL.
     */
    private final Terminal TERMINAL;
    /**
     * The SCREEN.
     */
    private final Screen SCREEN;
    /**
     * The GUI_SCREEN.
     */
    private final GUIScreen GUI_SCREEN;

    /**
     * Instantiates a new Gui console.
     */
    private GuiConsoleType() {

        // Connect to MBean Server
        JMXInitializer initializer = DefaultJMXInitializer.getInstance();
        initializer.connect();

        String os = System.getProperty("os.name").toLowerCase();
        // linux or unix
        UNIX = (os.contains("nix") || os.contains("nux"));
        // windows
        WINDOWS = os.contains("win");

        if (UNIX) {
            TERMINAL = TerminalFacade.createUnixTerminal(Charset.forName("UTF8"));
        } else {
            TERMINAL = TerminalFacade.createTerminal(Charset.forName("UTF8"));
        }
//        TERMINAL.enterPrivateMode();
        SCREEN = TerminalFacade.createScreen(TERMINAL);
        GUI_SCREEN = TerminalFacade.createGUIScreen(SCREEN);

        if (getGUI_SCREEN() == null) {
            System.err.println(GenericMBeanCliBootstrap.showWelcomeScreen());
            DefaultConsoleIO.getInstance().run();
            return;
        }
        GUI_SCREEN.setTheme(GenericMBeanCliGuiTheme.getInstance());
        SCREEN.startScreen();

        GUI_SCREEN.showWindow(PrimaryGuiWindow.getInstance(), GUIScreen.Position.FULL_SCREEN);

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
     * Gets GUI_SCREEN.
     *
     * @return the GUI_SCREEN
     */
    public GUIScreen getGUI_SCREEN() {
        return GUI_SCREEN;
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
        static ConsoleType instance = new GuiConsoleType();
    }
}
