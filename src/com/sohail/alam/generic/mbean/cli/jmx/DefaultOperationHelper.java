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

import com.sohail.alam.generic.mbean.cli.logger.Logger;

import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * <p/>
 * The Implementation of {@link OperationHelper}. This is where most of the 'Magic' happens.
 * This class is solely responsible for anything and everything that has to do with MBean Operations
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 19/5/13
 *          Time: 7:21 PM
 * @since 1.0.0
 */
public class DefaultOperationHelper implements OperationHelper {

    /**
     * The JMX_INITIALIZER.
     */
    private final JMXInitializer JMX_INITIALIZER;
    /**
     * The Storage.
     */
    private DataStorage storage;

    /**
     * Instantiates a new Operation helper.
     */
    private DefaultOperationHelper() {
        JMX_INITIALIZER = DefaultJMXInitializer.getInstance();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OperationHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Gets operation description.
     *
     * @param operationName the operation name
     *
     * @return the operation description
     */
    @Override
    public StringBuffer getOperationInfo(String operationName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getOperationInfo(beanObjectName.getCanonicalName(), operationName));
        }
        return buffer;
    }

    /**
     * Gets operation description.
     *
     * @param mbeanName the mbean name
     * @param name      the name
     *
     * @return the operation description
     */
    @Override
    public StringBuffer getOperationInfo(String mbeanName, String name) {

        StringBuffer buffer = new StringBuffer();
        String userOperationName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userOperationName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().contains(userOperationName)) {
                            formatOperationInfo(buffer, beanObjectName, operationName);
                        }
                    }
                }
            } else if (startsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().endsWith(userOperationName)) {
                            formatOperationInfo(buffer, beanObjectName, operationName);
                        }
                    }
                }
            } else if (endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().startsWith(userOperationName)) {
                            formatOperationInfo(buffer, beanObjectName, operationName);
                        }
                    }
                }
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String operationName : storage.getBeanOperations().keySet()) {
                    if (operationName.equalsIgnoreCase(name.toLowerCase())) {
                        formatOperationInfo(buffer, beanObjectName, operationName);
                    }
                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such operation found, try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Format operation info.
     *
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param operationName  the operation name
     */
    private void formatOperationInfo(StringBuffer buffer, ObjectName beanObjectName, String operationName) {
        buffer
                .append("\n")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("                                 OPERATION INFORMATION                                 ")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("MBean Name   :  ").append(beanObjectName.getCanonicalName()).append("\n")
                .append("Name         :  ").append(operationName).append("\n")
                .append("Description  :  ").append(storage.getBeanOperations().get(operationName).get(storage.DESCRIPTION_POSITION)).append("\n")
                .append("---------------------------------------------------------------------------------------\n");
    }

    /**
     * Gets operation info.
     *
     * @param operationName the operation name
     *
     * @return the operation info
     */
    @Override
    public StringBuffer getOperationHelp(String operationName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getOperationHelp(beanObjectName.getCanonicalName(), operationName));
        }
        return buffer;
    }

    /**
     * Gets operation info.
     *
     * @param mbeanName the mbean name
     * @param name      the name
     *
     * @return the operation info
     */
    @Override
    public StringBuffer getOperationHelp(String mbeanName, String name) {

        StringBuffer buffer = new StringBuffer();
        String userOperationName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userOperationName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().contains(userOperationName)) {
                            formatOperationHelp(buffer, beanObjectName, operationName);
                        }
                    }
                }
            } else if (startsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().endsWith(userOperationName)) {
                            formatOperationHelp(buffer, beanObjectName, operationName);
                        }
                    }
                }
            } else if (endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().startsWith(userOperationName)) {
                            formatOperationHelp(buffer, beanObjectName, operationName);
                        }
                    }
                }
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String operationName : storage.getBeanOperations().keySet()) {
                    if (operationName.equalsIgnoreCase(name.toLowerCase())) {
                        formatOperationHelp(buffer, beanObjectName, operationName);
                    }
                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such operation found, try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Format operation help.
     *
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param operationName  the operation name
     */
    private void formatOperationHelp(StringBuffer buffer, ObjectName beanObjectName, String operationName) {
        buffer
                .append("\n")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("                                 OPERATION INFORMATION                                     ")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("MBean Name   :  ").append(beanObjectName.getCanonicalName()).append("\n")
                .append("Name         :  ").append(operationName).append("\n")
                .append("Return Type  :  ").append(storage.getBeanOperations().get(operationName).get(storage.RETURN_TYPE_POSITION)).append("\n")
                .append("Description  :  ").append(storage.getBeanOperations().get(operationName).get(storage.DESCRIPTION_POSITION)).append("\n");

        ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) storage.getBeanOperations().get(operationName).get(storage.PARAMETERS_POSITION);
        if (tempList != null && tempList.size() > 0) {
            int paramCount = 0;
            buffer.append("\nPARAMETERS INFO:\n");
            buffer.append("...............\n");
            for (HashMap map : tempList) {
                paramCount++;
                buffer.append("Parameter ").append(paramCount).append("\n");
                buffer.append("Parameter Name        : ").append(map.get(storage.NAME)).append("\n");
                buffer.append("Parameter Type        : ").append(map.get(storage.TYPE)).append("\n");
                buffer.append("Parameter Description : ").append(map.get(storage.DESCRIPTION)).append("\n\n");
            }
        }

        buffer.append("---------------------------------------------------------------------------------------\n");
    }

    /**
     * Gets all operations.
     *
     * @return the all operations
     */
    @Override
    public StringBuffer getAllOperations() {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getAllOperations(beanObjectName.getCanonicalName()));
        }
        return buffer;
    }

    /**
     * Gets all operations.
     *
     * @param mbeanName the mbean name
     *
     * @return the all operations
     */
    @Override
    public StringBuffer getAllOperations(String mbeanName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            Set<String> allOperationNames = storage.getBeanOperations().keySet();

            buffer.append("\n==============================================================================================================\n");
            buffer.append("ALL  OPERATIONS  INFORMATION  FOR  MBEAN  =>  ")
                    .append(String.format("%-57s", beanObjectName.getCanonicalName()))
                    .append("\n")
                    .append("==============================================================================================================\n")
                    .append(String.format("%-60s", "NAME"))
                    .append(String.format("%-35s", "RETURN TYPE"))
                    .append(String.format("%-15s", "PARAMETER COUNT"))
                    .append("\n--------------------------------------------------------------------------------------------------------------\n");

            for (String operationName : allOperationNames) {
                ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) storage.getBeanOperations().get(operationName).get(storage.PARAMETERS_POSITION);
                int paramCount = 0;
                if (tempList != null && tempList.size() > 0) {
                    for (HashMap map : tempList) {
                        paramCount++;
                    }
                }
                buffer.append(String.format("%-60s", operationName))
                        .append(String.format("%-35s", storage.getBeanOperations().get(operationName).get(storage.RETURN_TYPE_POSITION)))
                        .append(String.format("%-15s", paramCount))
                        .append("\n");
            }
            buffer.append("--------------------------------------------------------------------------------------------------------------\n");
        }
        return buffer;
    }

    /**
     * TODO: Invoke operation.
     *
     * @param operationName       the operation name
     * @param operationParameters the operation parameters
     *
     * @return the string buffer
     */
    @Override
    public StringBuffer invokeOperation(String operationName, ArrayList<String> operationParameters) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(invokeOperation(beanObjectName.getCanonicalName(), operationName, operationParameters));
        }
        return buffer;
    }

    /**
     * TODO: Invoke operation.
     *
     * @param mbeanName           the mbean name
     * @param name                the name
     * @param operationParameters the operation parameters
     *
     * @return the string buffer
     */
    @Override
    public StringBuffer invokeOperation(String mbeanName, String name, ArrayList<String> operationParameters) {

        Logger.logTrace("Inside invokeOperation()", getClass(), false);

        StringBuffer buffer = new StringBuffer();
        boolean found = false;
        String userOperationName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (operationParameters == null) {
            operationParameters = new ArrayList<String>(0);
        }

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userOperationName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().contains(userOperationName)) {
                            found = true;
                            buffer = tryToInvokeOperation(beanObjectName, operationName, operationParameters);
                        }
                    }
                }
            } else if (startsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().endsWith(userOperationName)) {
                            found = true;
                            buffer = tryToInvokeOperation(beanObjectName, operationName, operationParameters);
                        }
                    }
                }
            } else if (endsWithWildcard) {
                for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                    storage = DefaultDataStorage.getInstance(beanObjectName);
                    for (String operationName : storage.getBeanOperations().keySet()) {
                        if (operationName.toLowerCase().startsWith(userOperationName)) {
                            found = true;
                            buffer = tryToInvokeOperation(beanObjectName, operationName, operationParameters);
                        }
                    }
                }
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(mbeanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String operationName : storage.getBeanOperations().keySet()) {
                    if (operationName.equalsIgnoreCase(name.toLowerCase())) {
                        found = true;
                        buffer = tryToInvokeOperation(beanObjectName, operationName, operationParameters);
                        break;
                    }
                }
            }
        }

        if (!found) {
            buffer.append("No such operation found, try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * TODO: Try to invoke operation.
     *
     * @param mbeanObjectName     the mbean object name
     * @param operationName       the operation name
     * @param operationParameters the operation parameters
     *
     * @return the string buffer
     */
    private StringBuffer tryToInvokeOperation(ObjectName mbeanObjectName, String operationName, ArrayList<String> operationParameters) {

        Logger.logTrace("Inside tryToInvokeOperation()", getClass(), false);
        StringBuffer buffer = new StringBuffer();
        storage = DefaultDataStorage.getInstance(mbeanObjectName);
        ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) storage.getBeanOperations().get(operationName).get(storage.PARAMETERS_POSITION);
        int size = 0;
        if (tempList != null) {
            size = tempList.size();
        }

        if (operationParameters.size() >= size) {
            try {
                buffer.append("OPERATION NAME             :   ").append(operationName).append("\n");
                buffer.append("OPERATION RETURN VALUE     :   ").append(String.valueOf(
                        JMX_INITIALIZER.getMbsc().invoke(
                                mbeanObjectName,
                                operationName, // Operation Name
                                getParamValues(mbeanObjectName, operationName, operationParameters), // Operation Parameter Values in correct order
                                getParamTypes(mbeanObjectName, operationName))  // Operation Parameter type in correct order
                ));
                return buffer;

            } catch (Exception e) {
                Logger.logException(e, getClass());
            }
        } else {
            buffer.insert(0, "Number of parameters does not match method signature: " + operationName + "\n");
        }
        return buffer;
    }

    /**
     * TODO: Get param values.
     *
     * @param mbeanObjectName     the mbean object name
     * @param name                the name
     * @param operationParameters the operation parameters
     *
     * @return the object [ ]
     */
    private Object[] getParamValues(ObjectName mbeanObjectName, String name, ArrayList<String> operationParameters) {

        Logger.logTrace("Inside getParamValues()", getClass(), false);
        Object[] paramValues = new Object[0];
        storage = DefaultDataStorage.getInstance(mbeanObjectName);
        ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) storage.getBeanOperations().get(name).get(storage.PARAMETERS_POSITION);
        int size = 0;
        if (tempList != null) {
            size = tempList.size();
        }

        if (size > 0) {
            paramValues = new Object[size];
            HashMap<String, String> paramMap;
            int parameterClassTypeSwitch;
            for (int i = 0; i < size; i++) {
                parameterClassTypeSwitch = 0;
                paramMap = tempList.get(i);
                if (JavaClassTypes.CLASS_TYPE_MAP.containsKey(paramMap.get(storage.TYPE))) {
                    parameterClassTypeSwitch = JavaClassTypes.CLASS_TYPE_MAP.get(paramMap.get(storage.TYPE));
                } else {
                    parameterClassTypeSwitch = -1;
                }
                switch (parameterClassTypeSwitch) {
                    case JavaClassTypes.INT_INT:
                    case JavaClassTypes.INTEGER_WRAPPER_INT:
                        paramValues[i] = Integer.parseInt(operationParameters.get(i));
                        break;
                    case JavaClassTypes.FLOAT_INT:
                    case JavaClassTypes.FLOAT_WRAPPER_INT:
                        paramValues[i] = Float.parseFloat(operationParameters.get(i));
                        break;
                    case JavaClassTypes.DOUBLE_INT:
                    case JavaClassTypes.DOUBLE_WRAPPER_INT:
                        paramValues[i] = Double.parseDouble(operationParameters.get(i));
                        break;
                    case JavaClassTypes.BOOLEAN_INT:
                    case JavaClassTypes.BOOLEAN_WRAPPER_INT:
                        paramValues[i] = Boolean.parseBoolean(operationParameters.get(i));
                        break;
                    case JavaClassTypes.STRING_INT:
                        paramValues[i] = operationParameters.get(i);
                        break;
                    default:
                        break;
                }
            }
        }
        return paramValues;
    }

    /**
     * TODO: Get param types.
     *
     * @param mbeanObjectName the mbean object name
     * @param name            the name
     *
     * @return the string [ ]
     */
    private String[] getParamTypes(ObjectName mbeanObjectName, String name) {

        Logger.logTrace("Inside getParamTypes()", getClass(), false);
        String[] paramTypes = new String[0];
        storage = DefaultDataStorage.getInstance(mbeanObjectName);
        ArrayList<HashMap<String, String>> tempList = (ArrayList<HashMap<String, String>>) storage.getBeanOperations().get(name).get(DataStorage.PARAMETERS_POSITION);
        int size = 0;
        if (tempList != null) {
            size = tempList.size();
        }
        if (size > 0) {
            paramTypes = new String[size];
            int count = 0;
            // Organize the Parameter Types
            for (HashMap map : tempList) {
                paramTypes[count] = (String) map.get(DataStorage.TYPE);
                count++;
            }
        }
        return paramTypes;
    }

    /**
     * Check conflicts.
     *
     * @param name the name
     *
     * @return the string buffer
     */
    @Override
    public StringBuffer checkConflicts(String name) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(checkConflicts(beanObjectName.getCanonicalName(), name));
        }

        return buffer;
    }

    /**
     * Check conflicts.
     *
     * @param mbeanName the mbean name
     * @param name      the attribute name
     *
     * @return the string buffer
     */
    @Override
    public StringBuffer checkConflicts(String mbeanName, String name) {

        StringBuffer buffer = new StringBuffer();
        int conflictCounter = 0;
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            for (String operationName : storage.getBeanOperations().keySet()) {
                if (operationName.equalsIgnoreCase(name)) {
                    conflictCounter = 1;
                    buffer = new StringBuffer("\nMBean Name: ");
                    buffer.append(beanObjectName.getCanonicalName())
                            .append("\nOperation Name: ").append(operationName);
                    break;
                }
                if (operationName.toLowerCase().contains(name)) {
                    conflictCounter++;
                    buffer.append("\nMBean Name: ").append(beanObjectName.getCanonicalName())
                            .append("\nOperation Name: ").append(operationName).append("\n");
                }
            }
        }
        buffer.append("\n................................................................................");

        if (conflictCounter == 1) {
            // Get the Operation name only
            buffer = new StringBuffer(
                    buffer.toString().split("\n")[2].replaceAll("Operation Name: ", "")
            );
        } else {
            buffer.insert(0, "\nERROR:\nThere was a conflict resolving your command!\n");
        }

        return buffer;
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
        static OperationHelper instance = new DefaultOperationHelper();
    }
}
