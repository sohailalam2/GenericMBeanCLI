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

/**
 * <p/>
 * This {@link CliCommands} Interface lists the primary commands that this CLI can serve.
 * <p/>
 * <h1><u>Command List</u>:</h1>
 * <p/>
 * <h3>ALL_ATTRIBUTES</h3>
 * <ul>
 * <li>
 * <code>ALL_ATTRIBUTES [MBEAN_NAME/MBEAN_POSITION]</code><br />
 * Brings out a table describing all the available attributes<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>ALL_MBEANS</h3>
 * <ul>
 * <li>
 * <code>ALL_MBEANS</code><br />
 * Displays a list of all the available user MBeans for the existing JMX Connection
 * </li>
 * </ul>
 * <p/>
 * <h3>ALL_OPERATIONS</h3>
 * <ul>
 * <li>
 * <code>ALL_OPERATIONS [MBEAN_NAME/MBEAN_POSITION]</code><br />
 * Brings out a table describing all the available operations<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>CLEAR</h3>
 * <ul>
 * <li>
 * <code>CLEAR</code><br/>
 * Clears the screen
 * </li>
 * </ul>
 * <p/>
 * <h3>CLOSE_CONNECTION</h3>
 * <ul>
 * <li>
 * <code>CLOSE_CONNECTION</code><br/>
 * Closes the existing JMX Connection
 * </li>
 * </ul>
 * <p/>
 * <h3>EXIT</h3>
 * <ul>
 * <li>
 * <code>EXIT</code><br/>
 * Gracefully closes all existing connections and exits the application
 * </li>
 * </ul>
 * <p/>
 * <h3>GET</h3>
 * <ul>
 * <li>
 * <code>GET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME</code><br />
 * Gets and displays the value set for the given attribute<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>HELP</h3>
 * <ul>
 * <li>
 * <code>HELP</code><br/>
 * Brings out the general help page
 * </li>
 * <p/>
 * <li>
 * <code>HELP [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME</code><br />
 * Brings out the detailed information about the Attribute/Operation<br/>
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>INFO</h3>
 * <ul>
 * <li>
 * <code>INFO [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE/OPERATION_NAME</code><br />
 * Brings out the description or short information about the Attribute/Operation<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>INVOKE</h3>
 * <ul>
 * <li>
 * <code>INVOKE [MBEAN_NAME/MBEAN_POSITION] OPERATION_NAME [PARAMETER_VALUES...]</code><br />
 * Invokes an MBean operation. Also optionally one can pass parameter values if that operation takes any.<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 * <p/>
 * <h3>LOGIN</h3>
 * <ul>
 * <li>
 * <code>LOGIN</code><br/>
 * Logs out the currently logged in user and tries to login the default user
 * </li>
 * </ul>
 * <p/>
 * <h3>LOGOUT</h3>
 * <ul>
 * <li>
 * <code>LOGOUT</code><br/>
 * Logs out the currently logged in user and brings out the Authentication Screen
 * </li>
 * </ul>
 * <p/>
 * <h3>NEW_CONNECTION</h3>
 * <ul>
 * <li>
 * <code>NEW_CONNECTION [IP PORT]</code><br/>
 * Closes the existing JMX Connection and brings out the screen to enter JMX IP and JMX PORT information.<br/>
 * Optionally one may enter the IP and Port information alongside the command.
 * </li>
 * </ul>
 * <p/>
 * <h3>SET</h3>
 * <ul>
 * <li>
 * <code>SET [MBEAN_NAME/MBEAN_POSITION] ATTRIBUTE_NAME VALUE</code><br />
 * Sets the value for the given attribute<br />
 * <code>[MBEAN_NAME/MBEAN_POSITION]</code> is optional, to search within the given MBean, otherwise searches the all MBeans
 * </li>
 * </ul>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 20/5/13
 *          Time: 8:18 AM
 * @since 1.0.0
 */
public interface CliCommands {


    /**
     * The HELP Command
     * <h2>Examples:</h2>
     * <h3>HELP</h3>
     * <p>Brings out an extended help information.</p>
     * <h3>HELP attribute/operation_name</h3>
     * <p>Tries to bring detailed information about the given MBean Attribute/Operation.</p>
     */
    final String HELP = "HELP";
    final int HELP_INT = 1;
    /**
     * The LOGIN Command
     * <p>
     * Brings out the login user interface.
     * Useful if user logs out and wants to login without restarting the cli.
     * </p>
     */
    final String LOGIN = "LOGIN";
    final int LOGIN_INT = 2;
    /**
     * The LOGOUT Command
     * <p>
     * Logs out the currently logged in user, without exiting the cli.
     * </p>
     */
    final String LOGOUT = "LOGOUT";
    final int LOGOUT_INT = 3;
    /**
     * The EXIT Command
     * <p>
     * Logs off the currently logged in user and gracefully shuts down the cli.
     * </p>
     */
    final String EXIT = "EXIT";
    final int EXIT_INT = 4;
    /**
     * The INFO Command
     * <p>
     * This is similar to help but only works with a given MBean Attribute/Operation.
     * The difference is that, it only brings the description of the given Attribute/Operation from the JMX Server
     * </p>
     */
    final String INFO = "INFO";
    final int INFO_INT = 5;
    /**
     * The NEW_CONNECTION Command
     * <p>
     * To start a new JMX Connection, fire this command. It should then log the current user off,
     * bring in the UI to enter IP/PORT of the JMX Server to connect to, and finally try to connect to it.
     * </p>
     */
    final String NEW_CONNECTION = "NEW_CONNECTION";
    final int NEW_CONNECTION_INT = 6;
    /**
     * The ALL_ATTRIBUTES Command
     * <p>
     * This displays all the MBean Attributes present in all the MBeans.
     * Also, if a particular MBean is specified, then only shows its Attributes.
     * <h2>Examples:</h2>
     * <h3>ALL_ATTRIBUTES mbean_name</h3>
     * <p>Shows only the Attributes from mbean_name MBean.</p>
     * </p>
     */
    final String ALL_ATTRIBUTES = "ALL_ATTRIBUTES";
    final int ALL_ATTRIBUTES_INT = 7;
    /**
     * The ALL_OPERATIONS Command
     * <p>
     * This displays all the MBean Operations present in all the MBeans.
     * Also, if a particular MBean is specified, then only shows its Operations.
     * <h2>Examples:</h2>
     * <h3>ALL_OPERATIONS mbean_name</h3>
     * <p>Shows only the Operations from mbean_name MBean.</p>
     * </p>
     */
    final String ALL_OPERATIONS = "ALL_OPERATIONS";
    final int ALL_OPERATIONS_INT = 8;
    /**
     * The CLEAR Command
     * <p>
     * Clears the screen
     * </p>
     */
    final String CLEAR = "CLEAR";
    final int CLEAR_INT = 9;
    /**
     * The CLOSE_CONNECTION Command
     * <p>
     * To close an existing JMX Connection, fire this command.
     * </p>
     */
    final String CLOSE_CONNECTION = "CLOSE_CONNECTION";
    final int CLOSE_CONNECTION_INT = 10;
    /**
     * The GET Command
     * <p>
     * This is used to GET a MBean Attribute directly, clearly showing the user's intention
     * </p>
     */
    final String GET = "GET";
    final int GET_INT = 11;
    /**
     * The SET Command
     * <p>
     * This is used to SET a MBean Attribute directly, clearly showing the user's intention
     * </p>
     */
    final String SET = "SET";
    final int SET_INT = 12;
    /**
     * The INVOKE Command
     * <p>
     * This is used to INVOKE a MBean Operation directly, clearly showing the user's intention
     * </p>
     */
    final String INVOKE = "INVOKE";
    final int INVOKE_INT = 13;
    /**
     * The ALL_MBEANS Command
     * <p>
     * This is used to INVOKE a MBean Operation directly, clearly showing the user's intention
     * </p>
     */
    final String ALL_MBEANS = "ALL_MBEANS";
    final int ALL_MBEANS_INT = 14;
    /**
     * The SEARCH Command
     * <p>
     * This is used for searching with wildcard the entire JMX MBeans for a particular Attribute/Operation
     * </p>
     */
    final String SEARCH = "SEARCH";
    final int SEARCH_INT = 15;
}
