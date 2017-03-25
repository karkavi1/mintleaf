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

import org.junit.Before;
import org.junit.Test;
import org.qamatic.mintleaf.Executable;
import org.qamatic.mintleaf.MintLeafException;
import org.qamatic.mintleaf.core.ChangeSets;
import org.qamatic.mintleaf.tools.DbImporter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.qamatic.mintleaf.Mintleaf.csvTodbTransfer;
import static org.qamatic.mintleaf.Mintleaf.dbTocsvTransfer;
import static org.qamatic.mintleaf.Mintleaf.dbTodbTransfer;

/**
 * Created by qamatic on 3/6/16.
 */
public class ImportExportTests extends H2TestCase {


    @Before
    public void applyChangeSet() throws IOException, SQLException, MintLeafException {
        ChangeSets.migrate(h2Database.getNewConnection(), "res:/example-changesets.sql", "create schema,load seed data");
    }


    @Test
    public void writeCSVTest() throws SQLException, IOException, MintLeafException {
        File f = new File("users.csv");
        if (f.exists())
            f.delete();

        assertFalse(new File("users.csv").exists());

        Executable dataAction = dbTocsvTransfer().
                withSourceDb(h2Database).
                withSourceSql("select * from HRDB.USERS").
                withTargetCsvFile("users.csv").
                build();


        dataAction.execute();
        assertTrue(new File("users.csv").exists());
    }


    @Test
    public void importCSVTest() throws SQLException, IOException, MintLeafException {
        writeCSVTest();//dependent..

        Executable action = csvTodbTransfer().
                withSourceCsvFile("users.csv").
                withTargetDb(h2Database).
                withTargetSqlTemplate("UPDATE HRDB.USERS SET USERNAME = '$USERNAME$-changed' WHERE USERID=$USERID$").
                build();

        action.execute();
        h2DbQueries.query("SELECT USERNAME FROM HRDB.USERS", (row, resultSet) -> {
            assertTrue(resultSet.asString("USERNAME").contains("-changed"));
            return null;
        });

    }

    @Test
    public void testInlineParamRegEx() {
        Pattern pattern = Pattern.compile("\\$(\\w+)\\$", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("UPDATE HRDB.USERS SET USERID= $USERID$, USERNAME = '$USERNAME$'");
        matcher.find();
        assertEquals("USERID", matcher.group(1));
    }


    @Test
    public void DbToDbImport() throws SQLException, IOException, MintLeafException {
        ChangeSets.migrate(h2Database.getNewConnection(), "res:/example-changesets.sql", "DROP_CREATE_USERS_IMPORT_TABLE");

        Executable action = dbTodbTransfer().
                withSourceDb(h2Database).
                withSourceSql("SELECT * FROM HRDB.USERS").
                withTargetDb(h2Database).
                withTargetSqlTemplate("INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME) VALUES ($USERID$, '$USERNAME$')").
                build();

        action.execute();
        assertEquals(7, h2DbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));

    }


    @Test
    public void DbToDbImportNullIssue() throws SQLException, IOException, MintLeafException {
        ChangeSets.migrate(h2Database.getNewConnection(), "res:/example-changesets.sql", "DROP_CREATE_USERS_IMPORT_TABLE");

        Executable action = new DbImporter(h2Database.getNewConnection(),
                "SELECT * FROM HRDB.USERS",
                h2Database.getNewConnection(),
                "INSERT INTO HRDB.USERS_IMPORT_TABLE (USERID, USERNAME, RATE) VALUES ($USERID$, '$USERNAME$', $RATE$)"
        );
        action.execute();

        assertEquals(7, h2DbQueries.getCount("HRDB.USERS_IMPORT_TABLE"));


    }

}
