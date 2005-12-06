package org.nakedobjects.viewer.skylark.core;

import org.nakedobjects.object.Dump;
import org.nakedobjects.object.Naked;
import org.nakedobjects.object.NakedCollection;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.utility.DebugInfo;
import org.nakedobjects.utility.DebugString;


public class DebugObjectGraph implements DebugInfo {
    private final Naked object;

    public DebugObjectGraph(final Naked object) {
        this.object = object;
    }

    public String getDebugData() {
        DebugString debug = new DebugString();

        if (object instanceof NakedObject) {
            NakedObject obj = (NakedObject) object;
            dumpGraph(obj, debug);

        } else if (object instanceof NakedCollection) {
            NakedCollection collection = (NakedCollection) object;
            dumpGraph(collection, debug);
        }
        
        return debug.toString();
    }

    public String getDebugTitle() {
        return "Object Graph";
    }

    private void dumpGraph(final Naked object, DebugString info) {
        if (object != null) {
            Dump.graph(object, info);
        }
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business objects directly to the
 * user. Copyright (C) 2000 - 2005 Naked Objects Group Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address of Naked Objects
 * Group is Kingsway House, 123 Goldworth Road, Woking GU21 1NR, UK).
 */