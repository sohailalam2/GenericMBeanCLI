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

import javax.management.ObjectName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * This {@link DefaultDataStorage} class is the default implementation of {@link DataStorage}
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 20/5/13
 *          Time: 6:51 AM
 * @since 1.0.0
 */
public class DefaultDataStorage implements DataStorage {
    /**
     * The Instances.
     */
    private static Map<ObjectName, DataStorage> instances = new HashMap<ObjectName, DataStorage>();
    /**
     * The MBean attributes.
     */
    private Map<String, HashMap<String, String>> beanAttributes = null;
    /**
     * The MBean operations.
     */
    private Map<String, ArrayList<Object>> beanOperations = null;

    /**
     * Instantiates a new Data storage implementation.
     */
    private DefaultDataStorage() {
        beanAttributes = new HashMap<String, HashMap<String, String>>();
        beanOperations = new HashMap<String, ArrayList<Object>>();
    }

    /**
     * Gets instance.
     *
     * @param beanObjectName the bean name
     *
     * @return the instance
     */
    public static DataStorage getInstance(ObjectName beanObjectName) {
        if (instances.containsKey(beanObjectName)) {
            return instances.get(beanObjectName);
        } else {
            DataStorage newInstance = new DefaultDataStorage();
            instances.put(beanObjectName, newInstance);
            return newInstance;
        }
    }

    /**
     * Gets bean attributes.
     *
     * @return the bean attributes
     */
    @Override
    public Map<String, HashMap<String, String>> getBeanAttributes() {
        return beanAttributes;
    }

    /**
     * Sets bean attributes.
     *
     * @param beanAttributes the bean attributes
     */
    @Override
    public void setBeanAttributes(Map<String, HashMap<String, String>> beanAttributes) {
        this.beanAttributes = beanAttributes;
    }

    /**
     * Add to bean attribute.
     *
     * @param name        the name
     * @param className   the class name
     * @param readable    the readable
     * @param writeable   the writeable
     * @param isGetter    the is getter
     * @param description the description
     */
    public void addToBeanAttribute(String name, String className, String readable, String writeable, String isGetter, String description) {

        if (!getBeanAttributes().containsKey(name)) {
            HashMap<String, String> tempMap = new HashMap<String, String>();
            tempMap.put(CLASS_NAME, className);
            tempMap.put(READABLE, readable);
            tempMap.put(WRITEABLE, writeable);
            tempMap.put(IS_GETTER, isGetter);
            tempMap.put(DESCRIPTION, description);
            getBeanAttributes().put(name, tempMap);
        }
    }

    /**
     * Gets bean operations.
     *
     * @return the bean operations
     */
    @Override
    public Map<String, ArrayList<Object>> getBeanOperations() {
        return beanOperations;
    }

    /**
     * Sets bean operations.
     *
     * @param beanOperations the bean operations
     */
    @Override
    public void setBeanOperations(Map<String, ArrayList<Object>> beanOperations) {
        this.beanOperations = beanOperations;
    }

    /**
     * Add to m bean operations.
     *
     * @param name        the name
     * @param description the description
     * @param returnType  the return type
     * @param parameters  the parameters
     */
    @Override
    public void addToMBeanOperations(String name, String description, String returnType, ArrayList<Map<String, String>> parameters) {

        if (!getBeanOperations().containsKey(name)) {
            ArrayList<Object> tempList = new ArrayList<Object>();
            if (description != null) {
                tempList.add(DESCRIPTION_POSITION, description);
            } else {
                tempList.add(DESCRIPTION_POSITION, "No description is provided");
            }
            if (returnType != null) {
                tempList.add(RETURN_TYPE_POSITION, returnType);
            } else {
                tempList.add(RETURN_TYPE_POSITION, "No return type is provided");
            }
            if (parameters != null) {
                tempList.add(PARAMETERS_POSITION, parameters);
            } else {
                tempList.add(PARAMETERS_POSITION, "This operation does not take any parameter");
            }
            getBeanOperations().put(name, tempList);
        }
    }

    /**
     * Purge data storage.
     */
    @Override
    public void purgeDataStorage() {
        getBeanAttributes().clear();
        getBeanOperations().clear();
    }
}
