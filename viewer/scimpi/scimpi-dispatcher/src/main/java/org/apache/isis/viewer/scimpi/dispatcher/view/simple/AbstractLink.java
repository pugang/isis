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

package org.apache.isis.viewer.scimpi.dispatcher.view.simple;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.runtimes.dflt.runtime.system.context.IsisContext;
import org.apache.isis.viewer.scimpi.dispatcher.AbstractElementProcessor;
import org.apache.isis.viewer.scimpi.dispatcher.ForbiddenException;
import org.apache.isis.viewer.scimpi.dispatcher.ScimpiException;
import org.apache.isis.viewer.scimpi.dispatcher.context.RequestContext;
import org.apache.isis.viewer.scimpi.dispatcher.context.RequestContext.Scope;
import org.apache.isis.viewer.scimpi.dispatcher.processor.Request;
import org.apache.isis.viewer.scimpi.dispatcher.util.MethodsUtils;

public abstract class AbstractLink extends AbstractElementProcessor {

    @Override
    public void process(final Request request) {
        final String title = request.getOptionalProperty(FORM_TITLE);
        final String name = request.getOptionalProperty(NAME);
        final String cls = request.getOptionalProperty(CLASS, "action");
        final String object = request.getOptionalProperty(OBJECT);
        final RequestContext context = request.getContext();
        String objectId = object != null ? object : (String) context.getVariable(RequestContext.RESULT);
        ObjectAdapter adapter = MethodsUtils.findObject(context, objectId);

        // REVIEW this is common used code
        final String fieldName = request.getOptionalProperty(FIELD);
        if (fieldName != null) {
            final ObjectAssociation field = adapter.getSpecification().getAssociation(fieldName);
            if (field == null) {
                throw new ScimpiException("No field " + fieldName + " in "
                    + adapter.getSpecification().getFullIdentifier());
            }
            if (field.isVisible(IsisContext.getAuthenticationSession(), adapter).isVetoed()) {
                throw new ForbiddenException(field, ForbiddenException.VISIBLE);
            }
            IsisContext.getPersistenceSession().resolveField(adapter, field);
            adapter = field.get(adapter);
            if (adapter != null) {
                objectId = context.mapObject(adapter, Scope.INTERACTION);
            }
        }

        if (adapter != null && valid(request, adapter)) {
            final String variable = request.getOptionalProperty("param-name", RequestContext.RESULT);
            final String variableSegment = variable + "=" + objectId;

            String view = request.getOptionalProperty(VIEW);
            if (view == null) {
                view = defaultView();
            }
            view = context.fullUriPath(view);
            final String classSegment = " class=\"" + cls + "\"";
            final String titleSegment = title == null ? "" : (" title=\"" + title + "\"");
            String additionalSegment = additionalParameters(request);
            additionalSegment = additionalSegment == null ? "" : "&amp;" + additionalSegment;
            request.appendHtml("<a" + classSegment + titleSegment + " href=\"" + view + "?" + variableSegment
                + context.encodedInteractionParameters() + additionalSegment + "\">");
            request.pushNewBuffer();
            request.processUtilCloseTag();
            final String buffer = request.popBuffer();
            if (buffer.trim().length() > 0) {
                request.appendHtml(buffer);
            } else {
                request.appendAsHtmlEncoded(linkLabel(name, adapter));
            }
            request.appendHtml("</a>");
        } else {
            request.skipUntilClose();
        }
    }

    protected abstract String linkLabel(String name, ObjectAdapter object);

    protected String additionalParameters(final Request request) {
        return null;
    }

    protected abstract boolean valid(Request request, ObjectAdapter adapter);

    protected abstract String defaultView();
}