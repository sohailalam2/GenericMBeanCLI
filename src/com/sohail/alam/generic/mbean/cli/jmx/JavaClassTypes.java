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

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * This {@link JavaClassTypes} class contains the mapping of various primitive and wrapper
 * class types of Java with an integer value for using Switch-Case
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 20/5/13
 *          Time: 7:46 AM
 * @since 1.0.0
 */
public class JavaClassTypes {

    public static final Map<String, Integer> CLASS_TYPE_MAP = new HashMap<String, Integer>();

    public static final String INT = "int";
    public static final int INT_INT = 1;
    public static final String INTEGER_WRAPPER = "java.lang.Integer";
    public static final int INTEGER_WRAPPER_INT = 2;

    public static final String FLOAT = "float";
    public static final int FLOAT_INT = 3;
    public static final String FLOAT_WRAPPER = "java.lang.Float";
    public static final int FLOAT_WRAPPER_INT = 4;

    public static final String DOUBLE = "double";
    public static final int DOUBLE_INT = 5;
    public static final String DOUBLE_WRAPPER = "java.lang.Double";
    public static final int DOUBLE_WRAPPER_INT = 6;

    public static final String BOOLEAN = "boolean";
    public static final int BOOLEAN_INT = 7;
    public static final String BOOLEAN_WRAPPER = "java.lang.Boolean";
    public static final int BOOLEAN_WRAPPER_INT = 8;

    public static final String STRING = "java.lang.String";
    public static final int STRING_INT = 9;

    public static final String MAP = "java.util.Map";
    public static final int MAP_INT = 10;

    static {
        CLASS_TYPE_MAP.put(INT, INT_INT);
        CLASS_TYPE_MAP.put(INTEGER_WRAPPER, INTEGER_WRAPPER_INT);
        CLASS_TYPE_MAP.put(FLOAT, FLOAT_INT);
        CLASS_TYPE_MAP.put(FLOAT_WRAPPER, FLOAT_WRAPPER_INT);
        CLASS_TYPE_MAP.put(DOUBLE, DOUBLE_INT);
        CLASS_TYPE_MAP.put(DOUBLE_WRAPPER, DOUBLE_WRAPPER_INT);
        CLASS_TYPE_MAP.put(BOOLEAN, BOOLEAN_INT);
        CLASS_TYPE_MAP.put(BOOLEAN_WRAPPER, BOOLEAN_WRAPPER_INT);
        CLASS_TYPE_MAP.put(STRING, STRING_INT);
        CLASS_TYPE_MAP.put(MAP, MAP_INT);
    }

}
