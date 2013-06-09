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

package com.sohail.alam.generic.mbean.cli;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * This is the Implementation of {@link CliCommands}.
 * It contains a Map which links the String Version of the Command to its corresponding Integer Version.
 * It is primarily used to replace String with Integer which can be used in Switch-Case in Java 1.6
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 22/5/13
 *          Time: 12:35 PM
 * @since 1.0.0
 */
public class DefaultCliCommands implements CliCommands {

    /**
     * The Command map.
     */
    public final static Map<String, Integer> COMMAND_MAP = new HashMap<String, Integer>();

    static {
        COMMAND_MAP.put(HELP, HELP_INT);
        COMMAND_MAP.put(LOGIN, LOGIN_INT);
        COMMAND_MAP.put(LOGOUT, LOGOUT_INT);
        COMMAND_MAP.put(EXIT, EXIT_INT);
        COMMAND_MAP.put(INFO, INFO_INT);
        COMMAND_MAP.put(NEW_CONNECTION, NEW_CONNECTION_INT);
        COMMAND_MAP.put(ALL_ATTRIBUTES, ALL_ATTRIBUTES_INT);
        COMMAND_MAP.put(ALL_OPERATIONS, ALL_OPERATIONS_INT);
        COMMAND_MAP.put(ALL_MBEANS, ALL_MBEANS_INT);
        COMMAND_MAP.put(CLEAR, CLEAR_INT);
        COMMAND_MAP.put(CLOSE_CONNECTION, CLOSE_CONNECTION_INT);
        COMMAND_MAP.put(GET, GET_INT);
        COMMAND_MAP.put(SET, SET_INT);
        COMMAND_MAP.put(INVOKE, INVOKE_INT);
    }
}
