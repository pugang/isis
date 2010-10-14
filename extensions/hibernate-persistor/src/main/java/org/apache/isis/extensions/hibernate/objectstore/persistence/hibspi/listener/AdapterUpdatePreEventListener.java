/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */


package org.apache.isis.extensions.hibernate.objectstore.persistence.hibspi.listener;

import org.apache.log4j.Logger;
import org.hibernate.event.PreUpdateEvent;
import org.hibernate.event.PreUpdateEventListener;


/**
 * Respond to events within Hibernate which need to be reflected within the [[NAME]] System.
 */
public class AdapterUpdatePreEventListener extends AdapterEventListenerAbstract
        implements PreUpdateEventListener {

    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(AdapterUpdatePreEventListener.class);



    public boolean onPreUpdate(final PreUpdateEvent event) {
        // do nothing
        return false;
    }




}