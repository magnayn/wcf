package com.tonbeller.wcf.param;

import java.sql.Time;
import java.sql.Timestamp;

import junit.framework.TestCase;

public class SqlGeneratorTest extends TestCase {

  public void testColumnNames() {
    SqlGenerator sg = new SqlGenerator("C1");
    SqlEqualExpr eq = new SqlEqualExpr();

    // no columnID -> use defaultColumn
    eq.setSqlValue(new Integer(12));
    assertEquals("C1 = 12", sg.generate(eq));

    // mapping for defaultColumn
    sg.getColumnMap().put(SqlGenerator.DEFAULT_COLUMN_ID, "D1");
    assertEquals("D1 = 12", sg.generate(eq));

    // no mapping, use columnID
    eq.setColumnId("C2");
    assertEquals("C2 = 12", sg.generate(eq));

    // use mapping
    sg.getColumnMap().put("C2", "C3");
    assertEquals("C3 = 12", sg.generate(eq));
  }

  public void testTypes() {
    SqlGenerator sg = new SqlGenerator("C");
    SqlEqualExpr eq = new SqlEqualExpr();

    eq.setSqlValue(new Integer(12));
    assertEquals("C = 12", sg.generate(eq));
    
    eq.setSqlValue(new Double(12.34));
    assertEquals("C = 12.34", sg.generate(eq));
    
    eq.setSqlValue("abc");
    assertEquals("C = 'abc'", sg.generate(eq));
    eq.setSqlValue("ab'c");
    assertEquals("C = 'ab''c'", sg.generate(eq));

    java.sql.Date sd = java.sql.Date.valueOf("2004-12-01");
    eq.setSqlValue(sd);
    assertEquals("C = '2004-12-01'", sg.generate(eq));
    java.util.Date ud = new java.util.Date(sd.getTime());
    eq.setSqlValue(ud);
    assertEquals("C = '2004-12-01'", sg.generate(eq));
    
    Time ti = Time.valueOf("14:24:34");
    eq.setSqlValue(ti);
    assertEquals("C = '14:24:34'", sg.generate(eq));

    Timestamp ts = Timestamp.valueOf("2004-12-01 14:24:34.123");
    eq.setSqlValue(ts);
    assertEquals("C = '2004-12-01 14:24:34.123'", sg.generate(eq));
  }
}