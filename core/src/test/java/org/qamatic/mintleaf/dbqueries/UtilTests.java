/*
 *
 *   *
 *   *  *
 *   *  *   ~
 *   *  *   ~ The MIT License (MIT)
 *   *  *   ~
 *   *  *   ~ Copyright (c) 2010-2017 QAMatic Team
 *   *  *   ~
 *   *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *   *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *   *  *   ~ in the Software without restriction, including without limitation the rights
 *   *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *   *  *   ~ furnished to do so, subject to the following conditions:
 *   *  *   ~
 *   *  *   ~ The above copyright notice and this permission notice shall be included in all
 *   *  *   ~ copies or substantial portions of the Software.
 *   *  *   ~
 *   *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   *  *   ~ SOFTWARE.
 *   *  *   ~
 *   *  *   ~
 *   *  *
 *   *
 *   *
 *
 * /
 */

package org.qamatic.mintleaf.dbqueries;

import org.junit.Test;
import org.qamatic.mintleaf.DbType;
import org.qamatic.mintleaf.core.BasicDatabase;
import org.qamatic.mintleaf.core.StandardQueries;

import static org.junit.Assert.assertEquals;

public class UtilTests {

    BasicDatabase builder = new BasicDatabase(null, null, null, null);

    @Test
    public void testDbType() {
        assertEquals(DbType.H2, DbType.getDbType("jdbc:H2:/"));
        assertEquals(DbType.MYSQL, DbType.getDbType("jdbc:MySql:/"));
        assertEquals(DbType.MSSQL, DbType.getDbType("/jdbc:SqlServer:/"));
        assertEquals(DbType.ORACLE, DbType.getDbType("jdbc:Oracle:/"));
    }

    @Test
    public void testQueryImpl() {
        assertEquals(H2Queries.class, StandardQueries.getQueryImplementation("jdbc:H2:/"));
        assertEquals(MySqlQueries.class, StandardQueries.getQueryImplementation("jdbc:MySql:/"));
        assertEquals(MSSqlQueries.class, StandardQueries.getQueryImplementation("jdbc:SqlServer:/"));
        assertEquals(OracleQueries.class, StandardQueries.getQueryImplementation("jdbc:Oracle:/"));
    }

}
