package com.tonbeller.tbutils.httpunit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Vergleicht zwei Textfiles
 * 
 * @author eb
 * @since 19.05.2004
 */
public class FileDiff {

  public boolean equalsFiles(File f1, File f2) throws IOException {
    return equalsFiles(f1, f2, new DefaultHandler());
  }

  public boolean equalsFiles(File f1, File f2, FileDiffHandler h) throws IOException {
    FileDiffHandler handler = h == null ? new DefaultHandler() : h;

    System.out.println("Comparing ");
    System.out.println("> " + f1);
    System.out.println("< " + f2);
    
    BufferedReader fr1 = null;
    try {
      fr1 = new BufferedReader(new FileReader(f1));
      BufferedReader fr2 = null;
      try {
        fr2 = new BufferedReader(new FileReader(f2));
        int line = 0;
        int errCnt = 0;
        while (errCnt<10) {
          line++;
          String line1 = handler.prepareLine(fr1.readLine());
          String line2 = handler.prepareLine(fr2.readLine());

          if (!equalsLines(line1, line2)) {
            System.out.println("Lines different at " +line);
            System.out.println("> " + line1);
            System.out.println("< " + line2);
            errCnt++;
          }

          if (line1 == null)
            break;
        }
        return errCnt==0;
      } finally {
        if (fr2 != null)
          fr2.close();
      }
    } finally {
      if (fr1 != null)
        fr1.close();
    }
  }

  private boolean equalsLines(String line1, String line2) {
    if (line1 == line2)
      return true;
    if (line1 == null)
      return false;

    return line1.equals(line2);
  }

  public interface FileDiffHandler {

    public String prepareLine(String line);
  }

  static class DefaultHandler implements FileDiffHandler {

    public String prepareLine(String line) {
      if (line == null)
        return null;
      return line.trim();
    }

  }
}