package com.tonbeller.wcf.table;

import junit.framework.TestCase;

import com.tonbeller.wcf.controller.TestContext;
import com.tonbeller.wcf.selection.SelectionModel;

public class TableModelDecoratorTest extends TestCase {

  class My extends TableModelDecorator {
    int changeCount = 0;
    public My(TableModel model) {
      super(model);
    }
    public void tableModelChanged(TableModelChangeEvent event) {
      changeCount += 1;
    }
  }

  public TableModelDecoratorTest(String arg0) {
    super(arg0);
  }

  public void testEvents() {
    TableColumn tc = new TableColumn(0);
    RowComparator comp = new RowComparator(tc);

    TableModel model = new TestModel();
    SortedTableModel sorter = new SortedTableModel(model);
    My my = new My(sorter);

    assertEquals("Hello World: 0", my.getRow(0).getValue(0));
    tc.setDescending(false);
    sorter.sort(comp);
    assertEquals("Hello World: 0", my.getRow(0).getValue(0));
    tc.setDescending(true);
    sorter.sort(comp);
    assertEquals("Hello World: 9", my.getRow(0).getValue(0));
    model.fireModelChanged(false);
    assertEquals("Hello World: 0", my.getRow(0).getValue(0));
    assertEquals("change count", 1, my.changeCount);
  }

  public void testSelectionCleared() throws Exception {
    TableModel model = new TestModel();
    TableComponent comp = new TableComponent("id", null, model);
    comp.initialize(new TestContext());

    SelectionModel sm = comp.getSelectionModel();
    sm.add(model.getRow(0));
    model.fireModelChanged(false);
    assertTrue(!sm.isEmpty());
    model.fireModelChanged(true);
    assertTrue(sm.isEmpty());
  }
}
