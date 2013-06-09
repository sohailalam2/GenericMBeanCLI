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

package com.sohail.alam.generic.mbean.cli.logger;

import com.sohail.alam.generic.mbean.cli.CliProperties;

import java.util.GregorianCalendar;

/**
 * <p/>
 * This {@link Logger} is a helper class to log various messages, exception etc. onto the console.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 20/5/13
 *          Time: 7:31 AM
 * @since 1.0.0
 */
public class Logger {

    private static CliProperties CLI_PROPERTIES = CliProperties.getInstance();

    private Logger() {

    }

    /**
     * Log exception.
     *
     * @param ex    the exception
     * @param clazz the Class
     */
    public static void logException(Exception ex, Class clazz) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n--------------------  EXCEPTION CAUGHT  --------------------\n");
        if (CLI_PROPERTIES.getLogLevel().equalsIgnoreCase("DEBUG") || CLI_PROPERTIES.getLogLevel().equalsIgnoreCase("TRACE")) {
            buffer.append(new GregorianCalendar().getInstance().getTime());
            buffer.append("\nPackage : ").append(clazz.getPackage());
            buffer.append("\nClass   : ").append(clazz.getSimpleName());
            buffer.append("\n\n");
            buffer.append(ex.getMessage());
            buffer.append("\n");
            for (StackTraceElement traceElement : ex.getStackTrace()) {
                buffer.append("\nFile Name   : ").append(traceElement.getFileName());
                buffer.append("\nClass Name  : ").append(traceElement.getClassName());
                buffer.append("\nMethod Name : ").append(traceElement.getMethodName());
                buffer.append("\nLine Number : ").append(traceElement.getLineNumber());
                buffer.append("\n");
            }
        } else {
            buffer.append(new GregorianCalendar().getInstance().getTime());
            buffer.append("\nPackage : ").append(clazz.getPackage());
            buffer.append("\nClass   : ").append(clazz.getSimpleName());
            buffer.append("\n\n");
            buffer.append(ex.getMessage());
        }
        buffer.append("\n------------------------------------------------------------\n");
        System.err.println(buffer);
    }

    /**
     * Log message.
     *
     * @param message the message
     * @param clazz   the Class
     */
    public static void logMessage(String message, Class clazz) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\n------------------------------------------------------------\n");
        buffer.append(new GregorianCalendar().getInstance().getTime());
        buffer.append("\nPackage : ").append(clazz.getPackage());
        buffer.append("\nClass   : ").append(clazz.getSimpleName());
        buffer.append("\nMessage : ").append(message);
        buffer.append("\n------------------------------------------------------------\n");
        System.out.print(buffer);
    }

    /**
     * Log message.
     *
     * @param message the message
     */
    public static void logMessage(String message) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(message);
        System.out.println(buffer);
    }

    /**
     * Log trace.
     *
     * @param message the message
     * @param enable
     */
    public static void logTrace(String message, Class clazz, Boolean enable) {
        if (enable) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("\n--------------------  TRACE ENABLED  --------------------");
            buffer.append("\nClass   : ").append(clazz.getSimpleName());
            buffer.append("\nMESSAGE : ").append(message);
            System.err.println(buffer);
        }
    }
}
