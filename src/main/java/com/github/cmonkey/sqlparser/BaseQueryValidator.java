package com.github.cmonkey.sqlparser;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.fun.SqlBaseContextVariable;
import com.google.common.collect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseQueryValidator {
    public static List<String> extractTableNames(SqlNode node) {
        final List<String> tables = new ArrayList<>();
        if(node.getKind().equals(SqlKind.ORDER_BY)){
            node = ((SqlSelect)((SqlOrderBy)node).query).getFrom();
        }else{
            node = ((SqlSelect)node).getFrom();
        }

        if(node == null){
            return tables;
        }
        if(node.getKind().equals(SqlKind.IDENTIFIER)){
            SqlIdentifier sqlIdentifier = (SqlIdentifier)node;
            sqlIdentifier.names.stream().forEach(tables::add);
            return tables;
        }

        if(node.getKind().equals(SqlKind.AS)){
            tables.add(((SqlBasicCall)node).operand(1).toString());
            return tables;
        }

        if (node.getKind().equals(SqlKind.JOIN)){
            final SqlJoin from = (SqlJoin)node;

            if(from.getLeft().getKind().equals(SqlKind.AS)){
                tables.add(((SqlBasicCall)from.getLeft()).operand(1).toString());
            }else{
                SqlJoin left = (SqlJoin)from.getLeft();

                while(!left.getLeft().getKind().equals(SqlKind.AS)){
                    tables.add(((SqlBasicCall)left.getRight()).operand(1).toString());
                    left = (SqlJoin)left.getLeft();
                }

                tables.add(((SqlBasicCall) left.getLeft()).operand(1).toString());
                tables.add(((SqlBasicCall)left.getRight()).operand(1).toString());

            }

            tables.add(((SqlBasicCall)from.getRight()).operand(1).toString());

            return tables;
        }
        return tables;
    }

    public static Map<String, String> extractWhereClauses(SqlSelect sqlSelect) {
        Map<String, String> map = new HashMap<>();

        //TODO extractWhereClauses
        return map;
    }
}
