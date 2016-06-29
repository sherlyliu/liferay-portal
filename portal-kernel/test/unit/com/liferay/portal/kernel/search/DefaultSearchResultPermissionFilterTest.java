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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Sherly Liu
 */
public class DefaultSearchResultPermissionFilterTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIsGroupAdmin_IsSiteAdmin() throws Exception {
		SearchContext searchContext = SearchContextTestUtil
			.getSearchContext(_group.getGroupId());

		searchContext.setAttribute(Field.GROUP_ID, _group.getGroupId());

		Assert.assertTrue(new DefaultSearchResultPermissionFilter(null, null)
			.isGroupAdmin(searchContext));
	}

	@Test
	public void testIsGroupAdmin_NotSiteAdmin() throws Exception {
		SearchContext searchContext = SearchContextTestUtil
			.getSearchContext(_group.getGroupId());

		searchContext.setAttribute(Field.GROUP_ID, 0);

		Assert.assertFalse(new DefaultSearchResultPermissionFilter(null, null)
			.isGroupAdmin(searchContext));
	}

	@Test
	public void testIsGroupAdmin_OtherSiteAdmin() throws Exception {
		SearchContext searchContext = SearchContextTestUtil
			.getSearchContext(_group.getGroupId());

		searchContext.setAttribute(Field.GROUP_ID, 12345);

		Assert.assertFalse(new DefaultSearchResultPermissionFilter(null, null)
			.isGroupAdmin(searchContext));
	}

	@DeleteAfterTestRun
	private Group _group;

}