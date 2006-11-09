/*
 * Copyright (c) 1971-2003 TONBELLER AG, Bensheim.
 * All rights reserved.
 */
package com.tonbeller.wcf.utils;

import java.io.File;

/**
 * deletes fiels on system exit, session timeout etc.
 * @author av
 * @since Jan 4, 2006
 */
public interface TempFileDeleter {
  void register(File f);
}
