/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.item.selector.test;

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeProviderHandler;
import com.liferay.item.selector.ItemSelectorView;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class ItemSelectorReturnTypeProviderHandlerTest {

	@Before
	public void setUp() throws Exception {
		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			ItemSelectorReturnTypeProviderHandler.class);

		_itemSelectorReturnTypeProviderHandler = _bundleContext.getService(
			_serviceReference);
	}

	@After
	public void tearDown() throws BundleException {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testItemSelectorReturnTypeProviderHandler() {
		TestItemSelectorView testItemSelectorView = new TestItemSelectorView();

		ServiceRegistration<ItemSelectorView>
			itemSelectorViewServiceRegistration = registerItemSelectorView(
				testItemSelectorView, "test-view");

		try {
			List<ItemSelectorReturnType> itemSelectorReturnTypes =
				_itemSelectorReturnTypeProviderHandler.
					getSupportedItemSelectorReturnTypes(testItemSelectorView);

			Assert.assertEquals(1, itemSelectorReturnTypes.size());

			ItemSelectorReturnType itemSelectorReturnType =
				itemSelectorReturnTypes.get(0);

			Assert.assertTrue(
				itemSelectorReturnType instanceof TestItemSelectorReturnType);
		}
		finally {
			itemSelectorViewServiceRegistration.unregister();
		}
	}

	@ArquillianResource
	public Bundle bundle;

	protected ServiceRegistration<ItemSelectorView> registerItemSelectorView(
		ItemSelectorView itemSelectorView, String itemSelectorViewKey) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("item.selector.view.key", itemSelectorViewKey);

		return _bundleContext.registerService(
			ItemSelectorView.class, itemSelectorView, properties);
	}

	private BundleContext _bundleContext;
	private ItemSelectorReturnTypeProviderHandler
		_itemSelectorReturnTypeProviderHandler;
	private ServiceReference<ItemSelectorReturnTypeProviderHandler>
		_serviceReference;

}