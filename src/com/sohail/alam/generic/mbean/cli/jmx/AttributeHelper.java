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

import javax.management.AttributeList;
import javax.management.ObjectName;
import java.util.HashMap;

/**
 * <p/>
 * The {@link AttributeHelper} is an interface declaring various methods to deal with
 * MBean Attributes
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 19/5/13
 *          Time: 6:54 PM
 * @since 1.0.0
 */
public interface AttributeHelper {

    /**
     * Gets all attributes.
     *
     * @return the all attributes
     */
    StringBuffer getAllAttributes();

    /**
     * Gets all attributes.
     *
     * @param beanName the mbean name
     *
     * @return the all attributes
     */
    StringBuffer getAllAttributes(String beanName);

    /**
     * Gets attribute info.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute info
     */
    StringBuffer getAttributeHelp(String attributeName);

    /**
     * Gets attribute info.
     *
     * @param beanName      the mbean name
     * @param attributeName the attribute name
     *
     * @return the attribute info
     */
    StringBuffer getAttributeHelp(String beanName, String attributeName);

    /**
     * Gets attribute description.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute description
     */
    StringBuffer getAttributeInfo(String attributeName);

    /**
     * Gets attribute description.
     *
     * @param beanName      the mbean name
     * @param attributeName the attribute name
     *
     * @return the attribute description
     */
    StringBuffer getAttributeInfo(String beanName, String attributeName);

    /**
     * Set all attributes.
     *
     * @param attributesMap the attributes map
     *
     * @return the boolean
     */
    boolean SetAllAttributes(HashMap<String, AttributeList> attributesMap);

    /**
     * Sets all attributes.
     *
     * @param beanObjectName the mbean object name
     * @param attributeList  the attribute list
     *
     * @return the all attributes
     */
    boolean setAllAttributes(ObjectName beanObjectName, AttributeList attributeList);

    /**
     * Gets attribute value.
     *
     * @param beanName      the mbean name
     * @param attributeName the attribute name
     *
     * @return the attribute value
     */
    StringBuffer getAttributeValue(String beanName, String attributeName);

    /**
     * Gets attribute value.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute value
     */
    StringBuffer getAttributeValue(String attributeName);

    /**
     * Sets attribute value.
     *
     * @param beanName       the bean name
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     *
     * @return the attribute value
     */
    StringBuffer setAttributeValue(String beanName, String attributeName, String attributeValue);

    /**
     * Sets attribute value.
     *
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     *
     * @return the attribute value
     */
    StringBuffer setAttributeValue(String attributeName, String attributeValue);

    /**
     * Check conflicts.
     *
     * @param attributeName the attribute name
     *
     * @return the string buffer
     */
    StringBuffer checkConflicts(String attributeName);

    /**
     * Check conflicts.
     *
     * @param mbeanName     the mbean name
     * @param attributeName the attribute name
     *
     * @return the string buffer
     */
    StringBuffer checkConflicts(String mbeanName, String attributeName);

}
