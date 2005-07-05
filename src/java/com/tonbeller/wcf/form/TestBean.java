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
package com.tonbeller.wcf.form;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;

import com.tonbeller.wcf.controller.RequestContext;

/**
 * Bean containing test data
 * @author av
 */
public class TestBean {
  String stringValue = "some string";
  int intValue = 0;
  Date dateValue = new Date();
  Date dateTimeValue = new Date();
  double doubleValue = 0.0;
  String password = "";

  String textArea;
  boolean checkBox1;
  boolean checkBox2;
  int list1 = 2;
  int[] listN = new int[]{1, 2};
  int dynList = 0;

  boolean radioButton1;
  boolean radioButton2 = true;

  FileItem fileItem = null;


  /**
   * this method is called when the user presses a button and
   * the requested validation succeeded. This means, that all properties
   * have their new values from user input.
   * <p />
   * The method name is specified by the <tt>actionReference</tt> attribute
   * of the DOM element representing the button.
   *
   * @param context
   */
  public void testActionReference(RequestContext context) {
    setTextArea("Form Button pressed!");
    System.out.println("Form Button pressed!");  }

  /**
   * Returns the checkBox1.
   * @return boolean
   */
  public boolean isCheckBox1() {
    return checkBox1;
  }

  /**
   * Returns the checkBox2.
   * @return boolean
   */
  public boolean isCheckBox2() {
    return checkBox2;
  }

  /**
   * Returns the dateValue.
   * @return Date
   */
  public Date getDateValue() {
    return dateValue;
  }

  /**
   * Returns the intValue.
   * @return int
   */
  public int getIntValue() {
    return intValue;
  }

  /**
   * Returns the list1.
   * @return int
   */
  public int getList1() {
    return list1;
  }

  /**
   * Returns the listN.
   * @return int[]
   */
  public int[] getListN() {
    return listN;
  }

  /**
   * Returns the password.
   * @return String
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the stringValue.
   * @return String
   */
  public String getStringValue() {
    return stringValue;
  }

  /**
   * Returns the textArea.
   * @return String
   */
  public String getTextArea() {
    return textArea;
  }

  /**
   * Sets the checkBox1.
   * @param checkBox1 The checkBox1 to set
   */
  public void setCheckBox1(boolean checkBox1) {
    this.checkBox1 = checkBox1;
  }

  /**
   * Sets the checkBox2.
   * @param checkBox2 The checkBox2 to set
   */
  public void setCheckBox2(boolean checkBox2) {
    this.checkBox2 = checkBox2;
  }

  /**
   * Sets the dateValue.
   * @param dateValue The dateValue to set
   */
  public void setDateValue(Date dateValue) {
    this.dateValue = dateValue;
  }

  /**
   * Sets the intValue.
   * @param intValue The intValue to set
   */
  public void setIntValue(int intValue) {
    this.intValue = intValue;
  }

  /**
   * Sets the list1.
   * @param list1 The list1 to set
   */
  public void setList1(int list1) {
    this.list1 = list1;
  }

  /**
   * Sets the listN.
   * @param listN The listN to set
   */
  public void setListN(int[] listN) {
    this.listN = listN;
  }

  /**
   * Sets the password.
   * @param password The password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Sets the stringValue.
   * @param stringValue The stringValue to set
   */
  public void setStringValue(String stringValue) {
    this.stringValue = stringValue;
  }

  /**
   * Sets the textArea.
   * @param textArea The textArea to set
   */
  public void setTextArea(String textArea) {
    this.textArea = textArea;
  }

  /**
   * Returns the radioButton1.
   * @return boolean
   */
  public boolean isRadioButton1() {
    return radioButton1;
  }

  /**
   * Returns the radioButton2.
   * @return boolean
   */
  public boolean isRadioButton2() {
    return radioButton2;
  }

  /**
   * Sets the radioButton1.
   * @param radioButton1 The radioButton1 to set
   */
  public void setRadioButton1(boolean radioButton1) {
    this.radioButton1 = radioButton1;
  }

  /**
   * Sets the radioButton2.
   * @param radioButton2 The radioButton2 to set
   */
  public void setRadioButton2(boolean radioButton2) {
    this.radioButton2 = radioButton2;
  }

  /**
   * Returns the doubleValue.
   * @return double
   */
  public double getDoubleValue() {
    return doubleValue;
  }

  /**
   * Sets the doubleValue.
   * @param doubleValue The doubleValue to set
   */
  public void setDoubleValue(double doubleValue) {
    this.doubleValue = doubleValue;
  }

  /**
   * Returns the dynList.
   * @return int
   */
  public int getDynList() {
    return dynList;
  }

  /**
   * Sets the dynList.
   * @param dynList The dynList to set
   */
  public void setDynList(int dynList) {
    this.dynList = dynList;
  }

  public Date getDateTimeValue() {
    return dateTimeValue;
  }
  public void setDateTimeValue(Date dateTimeValue) {
    this.dateTimeValue = dateTimeValue;
  }

  public FileItem getFileItem() {
    return fileItem;
  }

  public void setFileItem(FileItem fileItem) {
    this.fileItem = fileItem;
  }

  public String getUploadInfo() throws UnsupportedEncodingException {
    if(fileItem==null)
      return "No file received";

    StringBuffer info = new StringBuffer();

    info.append("File received: ").append(fileItem.getName()).append("\n");
    info.append("Content type: ").append(fileItem.getContentType()).append("\n");
    // Size unterschiedlich auf Linux/Windows
    //info.append("Size: ").append(fileItem.getSize()).append("\n");
    info.append("In Memory: ").append(fileItem.isInMemory()).append("\n");

    if("text/plain".equals(fileItem.getContentType()) && fileItem.getSize()<1000)
      info.append("\n").append(fileItem.getString("ISO-8859-1")).append("\n");

    return info.toString();
  }
}
