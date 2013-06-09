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
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * xxxxxxxxxx
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 20/5/13
 *          Time: 7:04 AM
 * @since 1.0.0
 */
public interface DataStorage {

    /**
     * The constant CLASS_NAME.
     */
    final String CLASS_NAME = "CLASS_NAME";
    /**
     * The constant READABLE.
     */
    final String READABLE = "READABLE";
    /**
     * The constant WRITEABLE.
     */
    final String WRITEABLE = "WRITEABLE";
    /**
     * The constant IS_GETTER.
     */
    final String IS_GETTER = "IS_GETTER";
    /**
     * The constant DESCRIPTION.
     */
    final String DESCRIPTION = "DESCRIPTION";
    /**
     * The constant NAME.
     */
    final String NAME = "NAME";
    /**
     * The constant TYPE.
     */
    final String TYPE = "TYPE";

    /**
     * The DESCRIPTION _ pOSITION.
     */
    final int DESCRIPTION_POSITION = 0;
    /**
     * The RETURN _ tYPE _ pOSITION.
     */
    final int RETURN_TYPE_POSITION = 1;
    /**
     * The PARAMETERS _ pOSITION.
     */
    final int PARAMETERS_POSITION = 2;


    /**
     * Gets mbean attributes.
     *
     * @return the mbean attributes
     */
    Map<String, HashMap<String, String>> getBeanAttributes();

    /**
     * Sets mbean attributes.
     *
     * @param beanAttributes the mbean attributes
     */
    void setBeanAttributes(Map<String, HashMap<String, String>> beanAttributes);

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
    void addToBeanAttribute(String name, String className, String readable, String writeable, String isGetter, String description);

    /**
     * Gets bean operations.
     *
     * @return the bean operations
     */
    Map<String, ArrayList<Object>> getBeanOperations();

    /**
     * Sets bean operations.
     *
     * @param beanOperations the bean operations
     */
    void setBeanOperations(Map<String, ArrayList<Object>> beanOperations);

    /**
     * Add to m bean operations.
     *
     * @param name        the name
     * @param description the description
     * @param returnType  the return type
     * @param parameters  the parameters
     */
    void addToMBeanOperations(String name, String description, String returnType, ArrayList<Map<String, String>> parameters);

    /**
     * Purge data storage.
     */
    void purgeDataStorage();
}
