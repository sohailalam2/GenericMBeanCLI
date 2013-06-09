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
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.*;
import com.googlecode.lanterna.gui.layout.BorderLayout;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: sohail.alam
 * Date: 1/6/13
 * Time: 1:06 PM
 */
public class ChooseMBeans extends Window {
    /**
     * The constant instance.
     */
    private static volatile ChooseMBeans instance = null;

    /**
     * Instantiates a new Choose m beans.
     *
     * @param mbeanNames the mbean names
     */
    private ChooseMBeans(ArrayList<String> mbeanNames) {
        super("Select a MBean");
        createChoiceWindow(mbeanNames);
    }

    /**
     * Gets instance.
     * <p/>
     * The infamous Double-Checked Locking (DCL) idiom.
     * To implement DCL, you check a volatile field in the common path and
     * only synchronize when necessary
     * <p/>
     * {@code SOURCE: http://blog.crazybob.org/2007/01/lazy-loading-singletons.html}
     *
     * @param mbeanNames the mbean names
     *
     * @return the instance
     */
    public static ChooseMBeans getInstance(ArrayList<String> mbeanNames) {

        if (instance == null) {
            synchronized (ChooseMBeans.class) {
                if (instance == null)
                    instance = new ChooseMBeans(mbeanNames);
            }
        }
        return instance;
    }

    /**
     * Create choice window.
     *
     * @param mbeanNames the mbean names
     */
    private void createChoiceWindow(ArrayList<String> mbeanNames) {

        // Primary Panel
        Panel verticalPanel = new Panel(new Border.Invisible(), Panel.Orientation.VERTICAL);

        // Login Form Components
        EmptySpace emptySpace = new EmptySpace(1, 1);
        Label mbeanNamesLabel = new Label("MBean Names: ", Terminal.Color.RED, true);
        final CheckBoxList mbeanNamesCheckBoxList = new CheckBoxList();
        addMBeanNames(mbeanNamesCheckBoxList, mbeanNames);

        Button loginButton = new Button("Done Selection", new Action() {
            @Override
            public void doAction() {
                System.out.println(returnSelectedNames(mbeanNamesCheckBoxList));
            }
        });
        Button exitButton = new Button("Exit Without Selecting", new Action() {
            @Override
            public void doAction() {
                // Take appropriate actions
            }
        });
        Panel horizontalPanel = new Panel(new Border.Bevel(true), Panel.Orientation.HORISONTAL);

        // Add the buttons horizontally
        horizontalPanel.addComponent(loginButton);
        horizontalPanel.addComponent(emptySpace);
        horizontalPanel.addComponent(exitButton);

        // Add the form components vertically
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(mbeanNamesLabel);
        verticalPanel.addComponent(mbeanNamesCheckBoxList);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(emptySpace);
        verticalPanel.addComponent(horizontalPanel, BorderLayout.CENTER);

        // Finally add the form to the window
        addComponent(verticalPanel);
    }

    /**
     * Return selected names.
     *
     * @param mbeanNamesCheckBoxList the mbean names check box list
     *
     * @return the array list
     */
    private ArrayList<String> returnSelectedNames(CheckBoxList mbeanNamesCheckBoxList) {

        ArrayList<String> selectedNames = new ArrayList<String>();

        for (int i = 0; i < mbeanNamesCheckBoxList.getSize(); i++) {
            if (mbeanNamesCheckBoxList.isChecked(i)) {
                selectedNames.add(mbeanNamesCheckBoxList.getItemAt(i).toString());
            }
        }

        return selectedNames;
    }

    /**
     * Add mbean names.
     *
     * @param mbeanNamesCheckBoxList the mbean names check box list
     * @param mbeanNames             the mbean names
     */
    private void addMBeanNames(CheckBoxList mbeanNamesCheckBoxList, ArrayList<String> mbeanNames) {
        for (String mbeanName : mbeanNames) {
            mbeanNamesCheckBoxList.addItem(new String(mbeanName));
        }
    }
}
