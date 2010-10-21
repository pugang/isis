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


package org.apache.isis.extensions.wicket.viewer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.isis.common.jmock.FixtureMockery;
import org.apache.isis.extensions.wicket.ui.pages.PageClassRegistry;
import org.apache.isis.extensions.wicket.ui.pages.PageType;
import org.apache.isis.extensions.wicket.ui.pages.home.HomePage;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class WicketObjectsApplication_Pages {

	private FixtureMockery context = new FixtureMockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	private WicketObjectsApplication application;

	@Test
	public void delegatesToPageClassRegistryToObtainPageTypes() {
		final PageType pageType = PageType.HOME;
		final Class<HomePage> expectedPageClass = HomePage.class;
		
		final PageClassRegistry mockPageClassRegistry = context.mock(PageClassRegistry.class);
		application = new WicketObjectsApplication() {
            private static final long serialVersionUID = 1L;

            @Override
			public PageClassRegistry getPageClassRegistry() {
				return mockPageClassRegistry;
			}
			
		};
		context.checking(new Expectations() {
			{
				one(mockPageClassRegistry).getPageClass(pageType);
				will(returnValue(expectedPageClass));
			}
		});
		final Class<? extends Page> pageClass = application.getHomePage();
		assertThat(expectedPageClass.isAssignableFrom(pageClass), is(true));
	}

	@Test
	public void delegatesToPageClassRegistryToObtainPageTypes_ForSignIn() {
		
		final PageType pageType = PageType.SIGN_IN;
		final Class<WebPage> expectedPageClass = WebPage.class;
		
		final PageClassRegistry mockPageClassRegistry = context.mock(PageClassRegistry.class);
		application = new WicketObjectsApplication() {
            private static final long serialVersionUID = 1L;

            @Override
			public PageClassRegistry getPageClassRegistry() {
				return mockPageClassRegistry;
			}
		};
		context.checking(new Expectations() {
			{
				one(mockPageClassRegistry).getPageClass(pageType);
				will(returnValue(expectedPageClass));
			}
		});
		final Class<? extends Page> pageClass = application.getSignInPageClass();
		assertThat(expectedPageClass.isAssignableFrom(pageClass), is(true));
	}

}
