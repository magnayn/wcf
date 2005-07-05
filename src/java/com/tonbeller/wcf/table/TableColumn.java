/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * Copyright (C) 2003-2004 TONBELLER AG.
 * All Rights Reserved.
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 *
 * 
 */
package com.tonbeller.wcf.table;
import java.util.Comparator;

/**
 * describes a TableColumn via the following properties:
 * <ul>
 *   <li> sorting properties (ascending/descending)
 *   <li> how the values are compared (Comparator) for sorting
 *   <li> how the values are rendered
 * </ul>
 */

public class TableColumn {

  private boolean descending = false;
  private boolean sortable = true;
  private Comparator comparator;
  private CellRenderer renderer;
  private int columnIndex;
  private boolean hidden = false;

  public TableColumn(int columnIndex) {
    this.columnIndex = columnIndex;
    comparator = new Comparator() {
      public int compare(Object o1, Object o2) {
        if (o1 instanceof Boolean) {
          boolean b1 = ((Boolean) o1).booleanValue();
          boolean b2 = ((Boolean) o2).booleanValue();
          if (b1 == b2)
            return 0;
          if (b1)
            return 1;
          return -1;
        }
        return ((Comparable) o1).compareTo(o2);
      }
    };
    renderer = new DefaultCellRenderer();
  }

  public void setDescending(boolean newDescending) {
    descending = newDescending;
  }
  public boolean isDescending() {
    return descending;
  }
  public void setComparator(Comparator newComparator) {
    comparator = newComparator;
  }
  public Comparator getComparator() {
    return comparator;
  }
  public void setCellRenderer(CellRenderer newRenderer) {
    renderer = newRenderer;
  }
  public CellRenderer getCellRenderer() {
    return renderer;
  }
  public void setSortable(boolean newSortable) {
    sortable = newSortable;
  }
  public boolean isSortable() {
    return sortable;
  }

  /**
   * the column index for TableRow.getValue() that this TableColumn that
   * is displayed by this table column.
   * The following is <em>not</em> true, after the
   * user has swapped columns:
   * <pre><code>
   *   TableComponent.getTableColumn(index).getColumnIndex() == index
   * </code></pre>
   *
   * @return
   */
  public int getColumnIndex() {
    return columnIndex;
  }
  public void setHidden(boolean newHidden) {
    hidden = newHidden;
  }
  public boolean isHidden() {
    return hidden;
  }
}
