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
package com.tonbeller.wcf.tree;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;
import com.tonbeller.wcf.selection.Unselectable;

/**
 * groups large amounts of children into groups by inserting
 * intermediate "artificial" nodes into the tree model. This is
 * to prevent users to open a large amount of children.
 * 
 * <p />
 * The implementation uses HashMap's so the tree nodes must properly support this.
 * TreeMaps will not work, because the result tree contains nodes of different
 * types that are not really comparable.
 * 
 * @author av
 */
public class GroupingTreeModelDecorator extends TreeModelDecorator {

  private int limit;
  private LabelProvider labelProvider;
  private Map mapChild2Parent;
  private Map mapParent2Children;
  private MessageFormat format;
  private static final Object NULL = new Object();

  /**
   * creates a GroupingTreeModel using HashMap's
   * 
   * @param labelProvider provides labels for the nodes in the tree model
   * @param decoree the tree model
   * @param limit number of children that will not divided into groups
   */
  public GroupingTreeModelDecorator(LabelProvider labelProvider, TreeModel decoree, int limit) {
    super(decoree);
    this.mapChild2Parent = new HashMap();
    this.mapParent2Children = new HashMap();
    initialize(labelProvider, limit);
  }

  /**
   * creates a GroupingTreeModel using HashMap's
   * 
   * @param nodeComparator compares nodes from underlying (decoree) tree model
   * @param labelProvider provides labels for the nodes in the tree model
   * @param decoree the tree model
   * @param limit number of children that will not divided into groups
   */
  public GroupingTreeModelDecorator(Comparator nodeComparator, LabelProvider labelProvider,
      TreeModel decoree, int limit) {
    super(decoree);
    Comparator comp = new GroupComparator(nodeComparator);
    this.mapChild2Parent = new TreeMap(comp);
    this.mapParent2Children = new TreeMap(comp);
    initialize(labelProvider, limit);
  }

  class GroupComparator implements Comparator {
    Comparator nodeComparator;

    public GroupComparator(Comparator nodeComparator) {
      this.nodeComparator = nodeComparator;
    }

    public int compare(Object o1, Object o2) {

      // compare NULL placeholder object
      if (o1 == NULL && o2 == NULL)
        return 0;
      else if (o1 == NULL)
        return -1;
      else if (o2 == NULL)
        return 1;

      // Group node involved?
      if (o1 instanceof Group) {
        if (o2 instanceof Group)
          return ((Group) o1).compareTo(o2);
        return 1;
      } else if (o2 instanceof Group)
        return -1;

      // other nodes
      return nodeComparator.compare(o1, o2);
    }
  };

  TreeModelChangeListener listener = new TreeModelChangeListener() {
    public void treeModelChanged(TreeModelChangeEvent event) {
      if (event.getSubtree() != null) {
        invalidateSubtree(event.getSubtree());
      } else {
        mapChild2Parent.clear();
        mapParent2Children.clear();
      }
    }

  };
  
  private void invalidateSubtree(Object parent) {
    Object[] children = (Object[]) mapParent2Children.remove(parent);
    if (children != null) {
      for (int i = 0; i < children.length; i++) {
        mapChild2Parent.remove(children[i]);
        invalidateSubtree(children[i]);
      }
    }
  }

  private void initialize(LabelProvider labelProvider, int limit) {
    this.limit = limit;
    this.labelProvider = labelProvider;
    super.getDecoree().addTreeModelChangeListener(listener);

    // label format
    RequestContext ctx = RequestContext.instance(false);
    if (ctx != null) {
      Resources res = ctx.getResources(GroupingTreeModelDecorator.class);
      String fmt = res.getString("tree.group.label");
      Locale loc = res.getLocale();
      this.format = new MessageFormat(fmt, loc);
    } else {
      /* test environment */
      this.format = new MessageFormat("{0} ...");
    }
  }

  public class Group implements Unselectable, Comparable {

    String label;

    public Group(String label) {
      this.label = label;
    }

    public Group(Object[] children) {
      Object[] args = new Object[2];
      args[0] = labelProvider.getLabel(children[0]);
      args[1] = labelProvider.getLabel(children[children.length - 1]);
      label = format.format(args, new StringBuffer(), null).toString();
    }

    public String toString() {
      return label;
    }

    public int compareTo(Object o) {
      Group g = (Group) o;
      return label.compareTo(g.label);
    }
  }

  private void updateCache(Object parent, Object[] children) {
    if (parent == null)
      parent = NULL;
    mapParent2Children.put(parent, children);
    for (int i = 0; i < children.length; i++)
      mapChild2Parent.put(children[i], parent);
  }

  Object[] group(Object parent, Object[] children) {
    // no need for grouping
    if (limit <= 0 || children.length <= limit) {
      updateCache(parent, children);
      return children;
    }
    // number of groups
    int groupCount = (children.length + limit - 1) / limit;
    Group[] groups = new Group[groupCount];
    for (int i = 0; i < groupCount; i++) {
      // compute which children belong to this group
      int fromIndex = i * limit;
      int toIndex = (i + 1) * limit;
      if (toIndex > children.length)
        toIndex = children.length;
      int count = toIndex - fromIndex;
      // create group node
      Object[] arr = new Object[count];
      System.arraycopy(children, fromIndex, arr, 0, count);
      groups[i] = new Group(arr);
      updateCache(groups[i], arr);
    }
    updateCache(parent, groups);
    return group(parent, groups);
  }

  public Object[] getRoots() {
    Object[] roots = (Object[]) mapParent2Children.get(NULL);
    if (roots != null)
      return roots;
    return group(NULL, super.getRoots());
  }

  public boolean hasChildren(Object node) {
    return node instanceof Group || super.hasChildren(node);
  }

  public Object[] getChildren(Object node) {
    Object[] children = (Object[]) mapParent2Children.get(node);
    if (children != null)
      return children;
    return group(node, super.getChildren(node));
  }

  public Object getParent(Object node) {
    Object parent = mapChild2Parent.get(node);
    if (parent == null) {
      parent = super.getParent(node);
      Object[] children;
      if (parent == null)
        children = super.getRoots();
      else
        children = super.getChildren(parent);
      group(parent, children);
    }
    // now its in the map!
    parent = mapChild2Parent.get(node);
    return parent == NULL ? null : parent;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
    fireModelChanged(false);
  }
}