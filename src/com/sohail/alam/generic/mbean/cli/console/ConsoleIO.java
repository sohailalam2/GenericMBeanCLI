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

/**
 * <p/>
 * The Interface responsible for managing the input/output from the Console.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 19/5/13
 *          Time: 7:57 PM
 * @since 1.0.0
 */
public interface ConsoleIO {
    /**
     * This method brings out the CLI user Interface to the user.
     */
    void run();
}
