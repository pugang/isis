package org.apache.isis.core.unittestsupport.jmocking;

import org.jmock.auto.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.ClassUnderTest;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Ignoring;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2.Mode;

public class JUnitRuleMockery2Test_mockAnnotatedWithIgnoring {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(Mode.INTERFACES_AND_CLASSES);

    @Ignoring
    @Mock
    private Collaborator collaborator;

    @ClassUnderTest
	private CollaboratingUsingConstructorInjection collaborating;

    @Before
	public void setUp() throws Exception {
    	collaborating = context.getClassUnderTest();
	}
    
    @Test
    public void invocationOnCollaboratorIsIgnored() {
    	collaborating.collaborateWithCollaborator();
    }

    @Test
    public void lackOfInvocationOnCollaboratorIsIgnored() {
    	collaborating.dontCollaborateWithCollaborator();
    }

}
