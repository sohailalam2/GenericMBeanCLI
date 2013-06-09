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

import com.sohail.alam.generic.mbean.cli.jmx.DefaultJMXInitializer;
import com.sohail.alam.generic.mbean.cli.logger.Logger;

/**
 * <p/>
 * This {@link JMXUpdateTask} is responsible for updating the Data Storage of
 * Bean Information.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 20/5/13
 *          Time: 8:35 AM
 * @since 1.0.0
 */
public class JMXUpdateTask implements Runnable {

    /**
     * Run void.
     */
    @Override
    public void run() {
        Logger.logTrace("Running JMXUpdateTask", getClass(), true);
        DefaultJMXInitializer.getInstance().reload();
    }
}
