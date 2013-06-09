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

package com.sohail.alam.generic.mbean.cli.jmx;

import java.util.ArrayList;

/**
 * <p/>
 * xxxxxxxxxx
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 19/5/13
 *          Time: 7:35 PM
 * @since 1.0.0
 */
public interface OperationHelper {
    /**
     * Gets operation info.
     *
     * @param operationName the operation name
     *
     * @return the operation info
     */
    StringBuffer getOperationHelp(String operationName);

    /**
     * Gets operation info.
     *
     * @param mbeanName     the mbean name
     * @param operationName the operation name
     *
     * @return the operation info
     */
    StringBuffer getOperationHelp(String mbeanName, String operationName);

    /**
     * Gets operation description.
     *
     * @param operationName the operation name
     *
     * @return the operation description
     */
    StringBuffer getOperationInfo(String operationName);

    /**
     * Gets operation description.
     *
     * @param mbeanName     the mbean name
     * @param operationName the operation name
     *
     * @return the operation description
     */
    StringBuffer getOperationInfo(String mbeanName, String operationName);

    /**
     * Invoke operation.
     *
     * @param operationName       the operation name
     * @param operationParameters the operation parameters
     *
     * @return the string buffer
     */
    StringBuffer invokeOperation(String operationName, ArrayList<String> operationParameters);

    /**
     * Invoke operation.
     *
     * @param mbeanName           the mbean name
     * @param operationName       the operation name
     * @param operationParameters the operation parameters
     *
     * @return the string buffer
     */
    StringBuffer invokeOperation(String mbeanName, String operationName, ArrayList<String> operationParameters);

    /**
     * Gets all operations.
     *
     * @return the all operations
     */
    StringBuffer getAllOperations();

    /**
     * Gets all operations.
     *
     * @param mbeanName the mbean name
     *
     * @return the all operations
     */
    StringBuffer getAllOperations(String mbeanName);

    /**
     * Check conflicts.
     *
     * @param operationName the operation name
     *
     * @return the string buffer
     */
    StringBuffer checkConflicts(String operationName);

    /**
     * Check conflicts.
     *
     * @param mbeanName the mbean name
     * @param name      the name
     *
     * @return the string buffer
     */
    StringBuffer checkConflicts(String mbeanName, String name);
}
