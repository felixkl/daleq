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

package de.brands4friends.daleq.integration.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

public class PrepareMysqlSchema implements InitializingBean {

    private final DataSource dataSource;

    public PrepareMysqlSchema(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Spring 3.1 deprecates SimpleJdbcTemplate, but not SimpleJdbcTestUtils. This will be resolved in Spring 3.2
        // Until then we rely on this deprecated class.
        // See as well https://jira.springsource.org/browse/SPR-9235
        SimpleJdbcTestUtils.executeSqlScript(
                new SimpleJdbcTemplate(dataSource),
                new ClassPathResource("schema-mysql.sql"),
                false);
    }
}
