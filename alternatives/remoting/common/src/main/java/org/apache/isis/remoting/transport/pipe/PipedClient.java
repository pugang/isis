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


package org.apache.isis.remoting.transport.pipe;

import org.apache.isis.remoting.client.ClientConnection;
import org.apache.isis.remoting.exchange.Request;
import org.apache.isis.remoting.exchange.ResponseEnvelope;


public class PipedClient implements ClientConnection {

    private PipedConnection communication;

    public void init() {}

    public void shutdown() {}


    public synchronized void setConnection(final PipedConnection communication) {
        this.communication = communication;
    }

    public synchronized ResponseEnvelope executeRemotely(final Request request) {
        communication.setRequest(request);
        return communication.getResponse();
    }

}