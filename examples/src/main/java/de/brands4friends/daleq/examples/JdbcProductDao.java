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

package de.brands4friends.daleq.examples;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcProductDao extends JdbcDaoSupport implements ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = new RowMapper<Product>() {
        @Override
        public Product mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Product p = new Product();
            p.setId(rs.getLong("ID"));
            p.setName(rs.getString("NAME"));
            p.setSize(rs.getString("SIZE"));
            p.setPrice(rs.getBigDecimal("PRICE"));

            return p;
        }
    };

    public JdbcProductDao(final DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public Product findById(final long id) {
        return getJdbcTemplate().queryForObject(
                "select ID,NAME,SIZE,PRICE from PRODUCT",
                PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> findBySize(final String size) {
        return getJdbcTemplate().query(
                "select ID,NAME,SIZE,PRICE from PRODUCT where SIZE = ?",
                PRODUCT_ROW_MAPPER, size
        );
    }

    @Override
    public void save(final Product product) {
        getJdbcTemplate().update("INSERT INTO PRODUCT (NAME,SIZE,PRICE) VALUES (?,?,?)",
                product.getName(), product.getSize(), product.getPrice());
    }

    @Override
    public void saveAll(final Iterable<Product> products) {
        for (Product product : products) {
            save(product);
        }
    }
}
