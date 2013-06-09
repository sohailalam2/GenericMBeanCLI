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

/**
 * <p/>
 * xxxxxxxxxx
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0           Date: 20/5/13
 *          Time: 8:02 AM
 * @since 1.0.0
 */
class MessageFormatter {

    /**
     * The Name.
     */
    private String name = null;
    /**
     * The Type.
     */
    private String type = null;
    /**
     * The Value.
     */
    private String value = null;
    /**
     * The Readable.
     */
    private Boolean readable = null;
    /**
     * The Writable.
     */
    private Boolean writable = null;
    /**
     * The Bool getter.
     */
    private Boolean boolGetter = null;
    /**
     * The Description.
     */
    private String description = null;
    /**
     * The Return type.
     */
    private String returnType = null;

    /**
     * Instantiates a new Message formatter.
     */
    private MessageFormatter() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MessageFormatter getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets readable.
     *
     * @return the readable
     */
    public boolean getReadable() {
        return readable;
    }

    /**
     * Sets readable.
     *
     * @param readable the readable
     */
    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    /**
     * Gets writable.
     *
     * @return the writable
     */
    public boolean getWritable() {
        return writable;
    }

    /**
     * Sets writable.
     *
     * @param writable the writable
     */
    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    /**
     * Gets bool getter.
     *
     * @return the bool getter
     */
    public boolean getBoolGetter() {
        return boolGetter;
    }

    /**
     * Sets bool getter.
     *
     * @param boolGetter the bool getter
     */
    public void setBoolGetter(boolean boolGetter) {
        this.boolGetter = boolGetter;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets return type.
     *
     * @return the return type
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets return type.
     *
     * @param returnType the return type
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Gets attribute info.
     *
     * @return the attribute info
     */
    public StringBuffer getAttributeInfo() {
        return null;
    }

    /**
     * Gets operation info.
     *
     * @return the operation info
     */
    public StringBuffer getOperationInfo() {
        return null;
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
        static MessageFormatter instance = new MessageFormatter();
    }
}
