package org.nakedobjects.object.reflect;

import org.nakedobjects.object.Naked;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectSpecification;
import org.nakedobjects.object.NakedValue;
import org.nakedobjects.object.Session;
import org.nakedobjects.object.control.Consent;


public abstract class AbstractOneToOnePeer implements OneToOnePeer {
    private final OneToOnePeer decorated;

    public AbstractOneToOnePeer(OneToOnePeer local) {
        this.decorated = local;
    }

    public void clearAssociation(NakedObject inObject, NakedObject associate) {
        decorated.clearAssociation(inObject, associate);
    }

    public Naked getAssociation(NakedObject inObject) {
        return decorated.getAssociation(inObject);
    }

    public String getDescription() {
        return decorated.getDescription();
    }

    public Object getExtension(Class cls) {
        return decorated.getExtension(cls);
    }

    public Class[] getExtensions() {
        return decorated.getExtensions();
    }

    public MemberIdentifier getIdentifier() {
        return decorated.getIdentifier();
    }

    public String getName() {
        return decorated.getName();
    }

    public NakedObjectSpecification getType() {
        return decorated.getType();
    }

    public void initAssociation(NakedObject inObject, NakedObject associate) {
        decorated.initAssociation(inObject, associate);
    }

    public void initValue(NakedObject inObject, Object associate) {
        decorated.initValue(inObject, associate);
    }

    public boolean isAuthorised(Session session) {
        return decorated.isAuthorised(session);
    }

    public boolean isDerived() {
        return decorated.isDerived();
    }

    public boolean isEmpty(NakedObject inObject) {
        return decorated.isEmpty(inObject);
    }

    public boolean isHidden() {
        return decorated.isHidden();
    }

    public boolean isMandatory() {
        return decorated.isMandatory();
    }

    public boolean isObject() {
        return decorated.isObject();
    }

    public Consent isAvailable(NakedObject target) {
        return decorated.isUsable(target);
    }
    
    /** @deprecated */
    public Consent isUsable(NakedObject target) {
        return isAvailable(target);
    }

    public Consent isVisible(NakedObject target) {
        return decorated.isVisible(target);
    }

    public void setAssociation(NakedObject inObject, NakedObject associate) {
        decorated.setAssociation(inObject, associate);
    }

    public void setValue(NakedObject inObject, Object associate) {
        decorated.setValue(inObject, associate);
    }

    public Consent isAssociationValid(NakedObject inObject, NakedObject value) {
        return decorated.isAssociationValid(inObject, value);
    }

    public Consent isValueValid(NakedObject inObject, NakedValue value) {
        return decorated.isValueValid(inObject, value);
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business objects directly to the user.
 * Copyright (C) 2000 - 2005 Naked Objects Group Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address of Naked Objects Group is
 * Kingsway House, 123 Goldworth Road, Woking GU21 1NR, UK).
 */