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

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;
import java.util.HashMap;
import java.util.Set;

/**
 * <p/>
 * The Implementation of {@link AttributeHelper}. This is where most of the 'Magic' happens.
 * This class is solely responsible for anything and everything that has to do with MBean Attributes
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 19/5/13
 *          Time: 7:03 PM
 * @since 1.0.0
 */
public class DefaultAttributeHelper implements AttributeHelper {

    private final JMXInitializer JMX_INITIALIZER;
    private DataStorage storage;

    /**
     * Instantiates a new Attribute helper.
     */
    private DefaultAttributeHelper() {
        JMX_INITIALIZER = DefaultJMXInitializer.getInstance();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AttributeHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Gets attribute info corresponding to a given Attribute Name.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute info
     */
    @Override
    public StringBuffer getAttributeHelp(String attributeName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getAttributeHelp(beanObjectName.getCanonicalName(), attributeName));
        }
        return buffer;
    }

    /**
     * Gets attribute info corresponding to a given Attribute Name present in a given MBean.
     *
     * @param beanName the bean name
     * @param name     the name
     *
     * @return the attribute info
     */
    @Override
    public StringBuffer getAttributeHelp(String beanName, String name) {

        StringBuffer buffer = new StringBuffer();
        String userAttributeName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userAttributeName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                buffer = getAttributeHelp(beanName, userAttributeName, 0);
            } else if (startsWithWildcard) {
                buffer = getAttributeHelp(beanName, userAttributeName, 1);
            } else if (endsWithWildcard) {
                buffer = getAttributeHelp(beanName, userAttributeName, 2);
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String attributeName : storage.getBeanAttributes().keySet()) {

                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such attribute => " + name + " found in MBean => " + beanName + ", try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Gets attribute description.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute description
     */
    @Override
    public StringBuffer getAttributeInfo(String attributeName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getAttributeInfo(beanObjectName.getCanonicalName(), attributeName));
        }
        return buffer;
    }

    /**
     * Gets attribute description.
     *
     * @param beanName the bean name
     * @param name     the name
     *
     * @return the attribute description
     */
    @Override
    public StringBuffer getAttributeInfo(String beanName, String name) {

        StringBuffer buffer = new StringBuffer();
        String userAttributeName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userAttributeName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                getAttributeInfo(beanName, buffer, userAttributeName, 0);
            } else if (startsWithWildcard) {
                getAttributeInfo(beanName, buffer, userAttributeName, 0);
            } else if (endsWithWildcard) {
                getAttributeInfo(beanName, buffer, userAttributeName, 0);
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String attributeName : storage.getBeanAttributes().keySet()) {
                    if (attributeName.equalsIgnoreCase(name.toLowerCase())) {
                        formatAttributeInfo(buffer, beanObjectName, attributeName);
                    }
                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such attribute => " + name + " found in MBean => " + beanName + ", try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Gets all attributes.
     *
     * @return the all attributes
     */
    @Override
    public StringBuffer getAllAttributes() {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getAllAttributes(beanObjectName.getCanonicalName()));
        }
        return buffer;
    }

    /**
     * Gets all attributes.
     *
     * @param beanName the bean name
     *
     * @return the all attributes
     */
    @Override
    public StringBuffer getAllAttributes(String beanName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            Set<String> allAttributeNames = storage.getBeanAttributes().keySet();
            buffer.append("\n==================================================================================================\n");
            buffer.append("ALL  ATTRIBUTES  INFORMATION  FOR  MBEAN  =>  ")
                    .append(String.format("%-57s", beanObjectName.getCanonicalName()))
                    .append("\n")
                    .append("==================================================================================================\n")
                    .append(String.format("%-60s", "NAME"))
                    .append(String.format("%-15s", "READABLE"))
                    .append(String.format("%-15s", "WRITABLE"))
                    .append(String.format("%-15s", "VALUE"))
                    .append("\n--------------------------------------------------------------------------------------------------\n");

            for (String attributeName : allAttributeNames) {

                buffer.append(String.format("%-60s", attributeName))
                        .append(String.format("%-15s", storage.getBeanAttributes().get(attributeName).get(storage.READABLE)))
                        .append(String.format("%-15s", storage.getBeanAttributes().get(attributeName).get(storage.WRITEABLE)))
                        .append(String.format("%-15s", getAttributeValue(beanObjectName, attributeName)))
                        .append("\n");
            }
            buffer.append("--------------------------------------------------------------------------------------------------\n");
        }
        return buffer;
    }

    /**
     * TODO: Set all attributes.
     *
     * @param attributesMap the attributes map
     *
     * @return the boolean
     */
    @Override
    public boolean SetAllAttributes(HashMap<String, AttributeList> attributesMap) {
        return false;
    }

    /**
     * TODO: Sets all attributes.
     *
     * @param beanObjectName the bean object name
     * @param attributeList  the attribute list
     *
     * @return the all attributes
     */
    @Override
    public boolean setAllAttributes(ObjectName beanObjectName, AttributeList attributeList) {
        return false;
    }

    /**
     * Gets attribute value.
     *
     * @param beanName the bean name
     * @param name     the name
     *
     * @return the attribute value
     */
    @Override
    public StringBuffer getAttributeValue(String beanName, String name) {

        StringBuffer buffer = new StringBuffer();
        String userAttributeName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userAttributeName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                getAttributeValue(beanName, buffer, userAttributeName, 0);
            } else if (startsWithWildcard) {
                getAttributeValue(beanName, buffer, userAttributeName, 1);
            } else if (endsWithWildcard) {
                getAttributeValue(beanName, buffer, userAttributeName, 2);
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String attributeName : storage.getBeanAttributes().keySet()) {
                    if (attributeName.equalsIgnoreCase(name.toLowerCase())) {
                        getAttributeValue(buffer, beanObjectName, attributeName);
                    }
                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such attribute => " + name + " found in MBean => " + beanName + ", try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Gets attribute value.
     *
     * @param attributeName the attribute name
     *
     * @return the attribute value
     */
    @Override
    public StringBuffer getAttributeValue(String attributeName) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(getAttributeValue(beanObjectName.getCanonicalName(), attributeName));
        }
        return buffer;
    }

    /**
     * Sets attribute value.
     *
     * @param beanName the bean name
     * @param name     the name
     * @param value    the attribute value
     *
     * @return the attribute value
     */
    @Override
    public StringBuffer setAttributeValue(String beanName, String name, String value) {

        StringBuffer buffer = new StringBuffer();
        String userAttributeName;
        boolean startsWithWildcard = false;
        boolean endsWithWildcard = false;

        if (name.contains("*")) {
            startsWithWildcard = name.startsWith("*");
            endsWithWildcard = name.endsWith("*");
            userAttributeName = name.replaceAll("[*]", "").toLowerCase();
            if (startsWithWildcard && endsWithWildcard) {
                setAttributeValue(beanName, value, buffer, userAttributeName, 0);
            } else if (startsWithWildcard) {
                setAttributeValue(beanName, value, buffer, userAttributeName, 1);
            } else if (endsWithWildcard) {
                setAttributeValue(beanName, value, buffer, userAttributeName, 2);
            }
        } else {
            for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
                storage = DefaultDataStorage.getInstance(beanObjectName);
                for (String attributeName : storage.getBeanAttributes().keySet()) {
                    if (attributeName.equalsIgnoreCase(name.toLowerCase())) {
                        setValue(value, buffer, beanObjectName, attributeName);
                    }
                }
            }
        }

        if (buffer.length() == 0) {
            buffer.append("No such attribute => " + name + " found in MBean => " + beanName + ", try using wildcard (*) search\n");
        }

        return buffer;
    }

    /**
     * Sets attribute.
     *
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     *
     * @return the attribute
     */
    @Override
    public StringBuffer setAttributeValue(String attributeName, String attributeValue) {

        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames()) {
            buffer.append(setAttributeValue(beanObjectName.getCanonicalName(), attributeName, attributeValue));
        }
        return buffer;
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
            for (String attributeName : storage.getBeanAttributes().keySet()) {
                if (attributeName.equalsIgnoreCase(name)) {
                    conflictCounter = 1;
                    buffer = new StringBuffer("\nMBean Name: ");
                    buffer.append(beanObjectName.getCanonicalName())
                            .append("\nAttribute Name: ").append(attributeName);
                    break;
                }
                if (attributeName.toLowerCase().contains(name)) {
                    conflictCounter++;
                    buffer.append("\nMBean Name: ").append(beanObjectName.getCanonicalName())
                            .append("\nAttribute Name: ").append(attributeName).append("\n");
                }
            }
        }
        buffer.append("\n................................................................................");

        if (conflictCounter == 1) {
            // Get the attribute name only
            buffer = new StringBuffer(
                    buffer.toString().split("\n")[2].replaceAll("Attribute Name: ", "")
            );
        } else {
            buffer.insert(0, "\nERROR:\nThere was a conflict resolving your command!\n");
        }

        return buffer;
    }

    /**
     * Gets attribute help.
     *
     * @param beanName          the bean name
     * @param userAttributeName the user attribute name
     * @param condition         the condition
     *
     * @return the attribute help
     */
    private StringBuffer getAttributeHelp(String beanName, String userAttributeName, int condition) {
        StringBuffer buffer = new StringBuffer();
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            for (String attributeName : storage.getBeanAttributes().keySet()) {
                switch (condition) {
                    case 0:
                        if (attributeName.toLowerCase().contains(userAttributeName)) {
                            formatAttributeHelp(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 1:
                        if (attributeName.toLowerCase().endsWith(userAttributeName)) {
                            formatAttributeHelp(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 2:
                        if (attributeName.toLowerCase().startsWith(userAttributeName)) {
                            formatAttributeHelp(buffer, beanObjectName, attributeName);
                        }
                        break;
                }
            }
        }
        return buffer;
    }

    /**
     * Format attribute help.
     *
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param attributeName  the attribute name
     */
    private void formatAttributeHelp(StringBuffer buffer, ObjectName beanObjectName, String attributeName) {

        buffer
                .append("\n")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("                                       ATTRIBUTE HELP                                      ")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("MBean Name   :  ").append(beanObjectName.getCanonicalName()).append("\n")
                .append("Name         :  ").append(attributeName).append("\n")
                .append("Value        :  ").append(getAttributeValue(beanObjectName, attributeName)).append("\n")
                .append("Type         :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.CLASS_NAME)).append("\n")
                .append("Is Readable  :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.READABLE)).append("\n")
                .append("Is Writable  :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.WRITEABLE)).append("\n")
                .append("Is Boolean   :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.IS_GETTER)).append("\n")
                .append("Description  :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.DESCRIPTION)).append("\n")
                .append("---------------------------------------------------------------------------------------\n");
    }

    /**
     * Gets attribute info.
     *
     * @param beanName          the bean name
     * @param buffer            the buffer
     * @param userAttributeName the user attribute name
     * @param condition         the condition
     */
    private void getAttributeInfo(String beanName, StringBuffer buffer, String userAttributeName, int condition) {
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            for (String attributeName : storage.getBeanAttributes().keySet()) {
                switch (condition) {
                    case 0:
                        if (attributeName.toLowerCase().contains(userAttributeName)) {
                            formatAttributeInfo(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 1:
                        if (attributeName.toLowerCase().endsWith(userAttributeName)) {
                            formatAttributeInfo(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 2:
                        if (attributeName.toLowerCase().startsWith(userAttributeName)) {
                            formatAttributeInfo(buffer, beanObjectName, attributeName);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Format attribute info.
     *
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param attributeName  the attribute name
     */
    private void formatAttributeInfo(StringBuffer buffer, ObjectName beanObjectName, String attributeName) {
        buffer
                .append("\n")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("                                 ATTRIBUTE INFORMATION                                 ")
                .append("\n---------------------------------------------------------------------------------------\n")
                .append("MBean Name   :  ").append(beanObjectName.getCanonicalName()).append("\n")
                .append("Name         :  ").append(attributeName).append("\n")
                .append("Description  :  ").append(storage.getBeanAttributes().get(attributeName).get(storage.DESCRIPTION)).append("\n")
                .append("---------------------------------------------------------------------------------------\n");
    }

    /**
     * Gets attribute value.
     *
     * @param beanName          the bean name
     * @param buffer            the buffer
     * @param userAttributeName the user attribute name
     * @param condition         the condition
     */
    private void getAttributeValue(String beanName, StringBuffer buffer, String userAttributeName, int condition) {
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            for (String attributeName : storage.getBeanAttributes().keySet()) {
                switch (condition) {
                    case 0:
                        if (attributeName.toLowerCase().contains(userAttributeName)) {
                            getAttributeValue(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 1:
                        if (attributeName.toLowerCase().endsWith(userAttributeName)) {
                            getAttributeValue(buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 2:
                        if (attributeName.toLowerCase().startsWith(userAttributeName)) {
                            getAttributeValue(buffer, beanObjectName, attributeName);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Gets value.
     *
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param attributeName  the attribute name
     */
    private void getAttributeValue(StringBuffer buffer, ObjectName beanObjectName, String attributeName) {
        buffer
                .append("\nMBean Name      : ").append(beanObjectName.getCanonicalName())
                .append("\nAttribute Name  : ").append(attributeName)
                .append("\nAttribute Value : ").append(getAttributeValue(beanObjectName, attributeName))
                .append("\n");
    }

    /**
     * Gets attribute value.
     *
     * @param beanObjectName the bean object name
     * @param attributeName  the attribute name
     *
     * @return the attribute value
     */
    private String getAttributeValue(ObjectName beanObjectName, String attributeName) {
        String attributeValue = null;
        if (Boolean.parseBoolean(storage.getBeanAttributes().get(attributeName).get(storage.READABLE))) {
            try {
                attributeValue = (String) JMX_INITIALIZER.getMbsc().getAttribute(beanObjectName, attributeName);
            } catch (Exception e) {
            }
        } else {
            attributeValue = " ** ATTRIBUTE IS NOT READABLE **";
        }
        return attributeValue;
    }

    /**
     * Sets attribute value.
     *
     * @param beanName          the bean name
     * @param value             the value
     * @param buffer            the buffer
     * @param userAttributeName the user attribute name
     * @param condition         the condition
     */
    private void setAttributeValue(String beanName, String value, StringBuffer buffer, String userAttributeName, int condition) {
        for (ObjectName beanObjectName : JMX_INITIALIZER.getMBeanObjectNames(beanName)) {
            storage = DefaultDataStorage.getInstance(beanObjectName);
            for (String attributeName : storage.getBeanAttributes().keySet()) {
                switch (condition) {
                    case 0:
                        if (attributeName.toLowerCase().contains(userAttributeName)) {
                            setValue(value, buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 1:
                        if (attributeName.toLowerCase().endsWith(userAttributeName)) {
                            setValue(value, buffer, beanObjectName, attributeName);
                        }
                        break;
                    case 2:
                        if (attributeName.toLowerCase().startsWith(userAttributeName)) {
                            setValue(value, buffer, beanObjectName, attributeName);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Sets value.
     *
     * @param value          the value
     * @param buffer         the buffer
     * @param beanObjectName the bean object name
     * @param attributeName  the attribute name
     */
    private void setValue(String value, StringBuffer buffer, ObjectName beanObjectName, String attributeName) {
        Attribute attribute = null;
        Boolean isWritable = Boolean.parseBoolean(storage.getBeanAttributes().get(attributeName).get(storage.WRITEABLE));
        if (isWritable) {
            try {
                String attributeType = storage.getBeanAttributes().get(attributeName).get(storage.CLASS_NAME);
                // Configure the attribute according to its type
                int attributeClassTypeSwitch;
                if (JavaClassTypes.CLASS_TYPE_MAP.containsKey(attributeType)) {
                    attributeClassTypeSwitch = JavaClassTypes.CLASS_TYPE_MAP.get(attributeType);
                } else {
                    attributeClassTypeSwitch = -1;
                }
                switch (attributeClassTypeSwitch) {
                    case JavaClassTypes.INT_INT:
                    case JavaClassTypes.INTEGER_WRAPPER_INT:
                        attribute = new Attribute(attributeName, Integer.parseInt(value));
                        break;
                    case JavaClassTypes.FLOAT_INT:
                    case JavaClassTypes.FLOAT_WRAPPER_INT:
                        attribute = new Attribute(attributeName, Float.parseFloat(value));
                        break;
                    case JavaClassTypes.DOUBLE_INT:
                    case JavaClassTypes.DOUBLE_WRAPPER_INT:
                        attribute = new Attribute(attributeName, Double.parseDouble(value));
                        break;
                    case JavaClassTypes.BOOLEAN_INT:
                    case JavaClassTypes.BOOLEAN_WRAPPER_INT:
                        attribute = new Attribute(attributeName, Boolean.parseBoolean(value));
                        break;
                    case JavaClassTypes.STRING_INT:
                        attribute = new Attribute(attributeName, value);
                        break;
                    default:
                        buffer.insert(0, "This attribute contains parameters that can not be set from CLI\n");
                        break;
                }
                // Try to set the value of the attribute
                if (attribute != null) {
                    JMX_INITIALIZER.getMbsc().setAttribute(beanObjectName, attribute);
                }
            } catch (Exception e) {
                Logger.logException(e, getClass());
            }
        } else {
            buffer.append(" ** ATTRIBUTE IS NOT WRITABLE **");
        }
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
        static AttributeHelper instance = new DefaultAttributeHelper();
    }
}
