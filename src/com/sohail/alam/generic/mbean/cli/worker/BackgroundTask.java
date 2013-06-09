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

package com.sohail.alam.generic.mbean.cli.worker;

import com.sohail.alam.generic.mbean.cli.logger.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p/>
 * This {@link BackgroundTask} class is responsible for scheduling background tasks.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0 Date: 20/5/13
 *          Time: 8:32 AM
 * @since 1.0.0
 */
public class BackgroundTask {

    /**
     * The constant INITIAL_DELAY.
     */
    public static final int INITIAL_DELAY = 0;
    /**
     * The constant PERIOD.
     */
    public static final int PERIOD = 10;
    /**
     * The constant TIME_UNIT.
     */
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;


    /**
     * Instantiates a new Background task.
     */
    private BackgroundTask() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BackgroundTask getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Run jMX update.
     */
    public void runJMXUpdate() {

        Logger.logMessage("Background Worker Started : JMX Update Task\n");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new JMXUpdateTask(), INITIAL_DELAY, PERIOD, TIME_UNIT);
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
        static BackgroundTask instance = new BackgroundTask();
    }
}
