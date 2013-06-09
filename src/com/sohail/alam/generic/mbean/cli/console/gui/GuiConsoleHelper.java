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

import com.googlecode.lanterna.gui.Theme;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.TextArea;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * User: Sohail Alam
 * Version: 1.0.0
 * Date: 1/6/13
 * Time: 8:15 PM
 */
public class GuiConsoleHelper {

    /**
     * Instantiates a new Gui console helper.
     */
    private GuiConsoleHelper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GuiConsoleHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Display message.
     *
     * @param messageArea the message area
     * @param message     the message
     */
    public void displayMessage(TextArea messageArea, StringBuffer message) {

        displayMessage(messageArea, message.toString());
    }

    /**
     * Display message.
     *
     * @param messageArea the message area
     * @param message     the message
     */
    public void displayMessage(TextArea messageArea, String message) {

        String[] messageLine = message.split("\n");
        for (String line : messageLine) {
            messageArea.appendLine(line);
        }
    }

    /**
     * Display message.
     *
     * @param messageArea the message area
     * @param message     the message
     * @param color       the color
     * @param category    the category
     */
    public void displayMessage(Label messageArea, StringBuffer message, Terminal.Color color, Theme.Category category) {

        displayMessage(messageArea, message.toString(), color, category);
    }

    /**
     * Display message.
     *
     * @param messageArea the message area
     * @param message     the message
     * @param color       the color
     * @param category    the category
     */
    public void displayMessage(Label messageArea, String message, Terminal.Color color, Theme.Category category) {

        String[] messageLine = message.split("\n");
        for (String line : messageLine) {
            messageArea.setText(line);
        }

        messageArea.setTextColor(color);
        messageArea.setStyle(category);
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
        static GuiConsoleHelper instance = new GuiConsoleHelper();
    }
}
