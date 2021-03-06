/*
 * Copyright 2012 brands4friends, Private Sale GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brands4friends.daleq.spring;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import de.brands4friends.daleq.core.DaleqSupport;

public class DaleqSupportBeanTest extends EasyMockSupport {


    private DaleqSupportBean bean;

    @Before
    public void setUp() {
        bean = new DaleqSupportBean();
        bean.setDataSource(EasyMock.createMock(DataSource.class));
    }

    @Test(expected = NullPointerException.class)
    public void nullDataSource_should_fail() {
        bean.setDataSource(null);
        init();
    }

    @Test
    public void bean_should_beInstantiated() {
        init();
        assertThat(bean.getObject(), notNullValue());
    }

    @Test
    public void bean_should_beSingleton() {
        init();
        assertThat(bean.isSingleton(), is(true));
    }

    @Test
    public void class_shouldBe_DaleqSupport() {
        init();
        final Class<?> clazz = bean.getObjectType();
        final Class<?> expected = DaleqSupport.class;
        assertThat(clazz.equals(expected), is(true));
    }

    private void init() {
        bean.afterPropertiesSet();
    }

}
