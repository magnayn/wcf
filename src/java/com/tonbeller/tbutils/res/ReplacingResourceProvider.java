package com.tonbeller.tbutils.res;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacingResourceProvider implements ResourceProvider {
  ResourceProvider decoree;
  // matches "${xxx}"
  Pattern pattern = Pattern.compile("\\$\\{([a-zA-Z0-9_\\.]+)\\}");

  public ReplacingResourceProvider(ResourceProvider decoree) {
    this.decoree = decoree;
  }

  public void close() {
    decoree.close();
  }

  public String getString(String key) {
    String s = decoree.getString(key);
    return replace(s);
  }

  public Collection keySet() {
    return decoree.keySet();
  }

  private String replace(String s) {
    if (s == null)
      return null;

    // found pattern?
    Matcher m = pattern.matcher(s);
    if (!m.find())
      return s;

    StringBuffer sb = new StringBuffer();
    int start = 0;
    do {
      // append stuff before the "${"
      sb.append(s.substring(start, m.start()));
      start = m.end();

      // escape: "$${xxx}" becomes "${xxx}" like ant
      if (m.start() > 0 && s.charAt(m.start() - 1) == '$') {
        sb.append(s.substring(m.start() + 1, m.end()));
      } else {
        // append replacement string
        String key = m.group(1);
        String val = decoree.getString(key);
        if (val != null)
          sb.append(val);
        else
          sb.append(s.substring(m.start(), m.end()));
      }
    } while (m.find());
    sb.append(s.substring(start, s.length()));

    return sb.toString();
  }

  public void dump(Dumper d) {
    decoree.dump(d);
  }

  public String getName() {
    return getClass().getName();
  }

}
