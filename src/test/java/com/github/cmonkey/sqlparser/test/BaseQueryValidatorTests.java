package com.github.cmonkey.sqlparser.test;

import com.github.cmonkey.sqlparser.BaseQueryValidator;
import org.apache.calcite.sql.SqlJoin;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class BaseQueryValidatorTests {

    @Test
    @DisplayName("testQueryValidator")
    public void testQueryValidator() throws SqlParseException {
        final String query = "SELECT e.first_name AS FirstName, s.salary AS Salary from employee AS e join salary AS s on e.emp_id=s.emp_id where e.organization = 'Tesla' and s.organization = 'Tesla'";
        SqlParser sqlParser = SqlParser.create(query);
        SqlNode sqlNode = sqlParser.parseQuery();
        SqlSelect sqlSelect = (SqlSelect)sqlNode;
        SqlJoin from = (SqlJoin)sqlSelect.getFrom();
        List<String> tables = BaseQueryValidator.extractTableNames(from);
        tables.forEach(table -> {
            System.out.println(" table = " + table);
        });
        Map<String, String> whereClauses = BaseQueryValidator.extractWhereClauses(sqlSelect);
        whereClauses.forEach((k, v) -> {
            System.out.println("k = " + k  + " by value = " + v);
        });
    }
}
