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

/**
 * User: Sohail Alam
 * Version: 1.0.0
 * Date: 2/6/13
 * Time: 12:15 PM
 */
public class GenericMBeanCliGuiTheme extends Theme {

    private GenericMBeanCliGuiTheme() {
    }

    @Override
    public Definition getDefinition(Category category) {
        return super.getDefinition(category);
    }

    @Override
    protected void setDefinition(Category category, Definition def) {
        super.setDefinition(category, def);
    }

    /**
     * Gets the default style to use when no Category-specific style is set.
     */
    @Override
    protected Definition getDefaultStyle() {
        return super.getDefaultStyle();
    }

    public static GenericMBeanCliGuiTheme getInstance() {
        return SingletonHolder.instance;
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
        static GenericMBeanCliGuiTheme instance = new GenericMBeanCliGuiTheme();
    }
}
