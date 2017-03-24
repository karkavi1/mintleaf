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

import org.junit.BeforeClass;
import org.qamatic.mintleaf.Database;
import org.qamatic.mintleaf.DbQueries;
import org.qamatic.mintleaf.Mintleaf;
import org.qamatic.mintleaf.core.JdbcDriverSource;

/**
 * Created by qamatic on 3/3/16.
 */
public class H2TestCase {
    protected static Database h2Database;
    protected static DbQueries h2DbQueries;

    @BeforeClass
    public static void setupDb() {

        if (h2Database != null)
            return;

        h2Database = new Mintleaf.DatabaseBuilder().
                withDriverSource(JdbcDriverSource.class).
                withUrl("jdbc:h2:file:./target/H2DbScriptTests;mv_store=false;").
                build();
        h2DbQueries = h2Database.getNewConnection().getDbQueries();

        /*
            Database db = Database.builder().withUrl("").with

         */

    }

}