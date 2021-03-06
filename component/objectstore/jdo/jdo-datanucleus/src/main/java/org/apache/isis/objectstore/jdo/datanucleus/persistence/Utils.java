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
package org.apache.isis.objectstore.jdo.datanucleus.persistence;

import java.util.Date;

import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.spi.PersistenceCapable;

import org.apache.isis.core.commons.authentication.AuthenticationSession;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.adapter.version.SerialNumberVersion;
import org.apache.isis.core.metamodel.adapter.version.Version;

public class Utils {

    @SuppressWarnings("unused")
    private static Object jdoObjectIdFor(InstanceLifecycleEvent event) {
        PersistenceCapable persistenceCapable = Utils.persistenceCapableFor(event);
        Object jdoObjectId = persistenceCapable.jdoGetObjectId();
        return jdoObjectId;
    }

    static PersistenceCapable persistenceCapableFor(InstanceLifecycleEvent event) {
        return (PersistenceCapable)event.getSource();
    }

    static void clearDirtyFor(final ObjectAdapter adapter) {
        adapter.getSpecification().clearDirty(adapter);
    }

    static Version getVersionIfAny(final PersistenceCapable pojo, final AuthenticationSession authenticationSession) {
        Object jdoVersion = pojo.jdoGetVersion();
        if(jdoVersion instanceof Long) {
            return SerialNumberVersion.create((Long) jdoVersion, authenticationSession.getUserName(), new Date()); 
        } 
        return null;
    }

}
