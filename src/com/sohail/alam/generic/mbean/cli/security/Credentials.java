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

package com.sohail.alam.generic.mbean.cli.security;

/**
 * <p/>
 * The {@link Credentials} interface exposes the default {@link #USERNAME} and {@link #PASSWORD}
 * that is used for user authentication by the {@link Authentication} interface.
 * <p/>
 *
 * @author Sohail Alam
 * @version 1.0.0
 *          Date: 19/5/13
 *          Time: 7:53 PM
 * @since 1.0.0
 */
interface Credentials {

    /*
     * TODO: ADD ENCRYPTION
     * DESCRIPTION: Need to add encrypted USERNAME/PASSWORD here
     * DATE: 21/5/13
     * TIME: 11:14 PM
     */

    /**
     * The USERNAME.
     */
    final String USERNAME = "admin";
    /**
     * The PASSWORD.
     */
    final String PASSWORD = "pass";
}
