package com.tonbeller.wcf.utils;

import java.sql.Time;

import org.apache.log4j.Logger;

/**
 * @author av
 * @since 01.02.2005
 */
public class SqlUtils {
  
  private static Logger logger = Logger.getLogger(SqlUtils.class);
  
  private SqlUtils() {}
  

  /**
   * replaces '\n' with ' ' because DB2 does not like linefeeds in SQL Statements
   */
  public static final String convStatement(String genstmt) {
    StringBuffer sb = new StringBuffer();
    int n = genstmt.length();
    for (int i = 0; i < n; i++) {
      char c = genstmt.charAt(i);
      if (Character.isWhitespace(c))
        sb.append(' ');
      else
        sb.append(c);
    }
    return sb.toString();
  }

  /**
   * creates a SQL literal from value. 
   */
  public static String escapeSql(Object value) {
    if (value == null)
      return "NULL";
    if (value instanceof String)
      return "'" + escapeSqlString((String) value) + "'";
    if (value instanceof java.sql.Date || value instanceof Time
        || value instanceof java.sql.Timestamp)
      return "'" + value.toString() + "'";
    if (value instanceof java.util.Date) {
      java.sql.Date d = new java.sql.Date(((java.util.Date) value).getTime());
      return "'" + d.toString() + "'";
    }
    return String.valueOf(value);
  }

  private static String escapeSqlString(String s) {
    int pos = s.indexOf('\'');
    if (pos >= 0) {
      StringBuffer sb = new StringBuffer();
      while (pos >= 0) {
        sb.append(s.substring(0, pos));
        sb.append("''");
        s = s.substring(pos + 1);
        pos = s.indexOf('\'');
      }
      sb.append(s);
      s = sb.toString();
    }
    return s;
  }

}