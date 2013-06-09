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
 * @version 1.0.0
 *          Date: 20/5/13
 *          Time: 7:33 AM
 * @since 1.0.0
 */
public enum States {

    /**
     * The ERROR STATE.
     */
    ERROR("ERROR: \n", 8),

    /**
     * The CONFLICT STATE.
     */
    CONFLICT("CONFLICT: \n", 11),

    /**
     * The OK STATE.
     */
    OK("OK: \n", 5),

    /**
     * The FATAL STATE.
     */
    FATAL("FATAL: \n", 8);
    /**
     * The State type.
     */
    private final String stateType;
    /**
     * The State type length.
     */
    private final int stateTypeLength;

    /**
     * Instantiates a new States.
     *
     * @param stateType       the state type
     * @param stateTypeLength the state type length
     */
    private States(String stateType, int stateTypeLength) {
        this.stateType = stateType;
        this.stateTypeLength = stateTypeLength;
    }

    /**
     * Gets stateType.
     *
     * @return the stateType
     */
    public String getStateType() {
        return stateType;
    }

    /**
     * Gets state type length.
     *
     * @return the state type length
     */
    public int getStateTypeLength() {
        return stateTypeLength;
    }
}
