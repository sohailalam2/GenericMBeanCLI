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

package com.sohail.alam.generic.mbean.cli.console;

import com.sohail.alam.generic.mbean.cli.CliCommands;

/**
 * <p/>
 * The Interface extending the {@link CliCommands} and declaring a method to process the Un-escaped User Input
 * and return a valid output which is to be displayed on the Console.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 20/5/13
 *          Time: 7:28 AM
 * @since 1.0.0
 */
public interface ProcessUserInput extends CliCommands {

    /**
     * Process the Un-escaped User Input
     * and return a valid output which is to be displayed on the Console.
     *
     * @param input
     *
     * @return the string buffer which is to be displayed on the Console.
     */
    StringBuffer process(String input);
}
