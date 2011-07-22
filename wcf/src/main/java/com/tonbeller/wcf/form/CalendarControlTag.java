package com.tonbeller.wcf.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.tonbeller.tbutils.res.Resources;
import com.tonbeller.wcf.controller.RequestContext;

public class CalendarControlTag extends TagSupport {
  
  public int doStartTag() throws JspException {
    RequestContext context = RequestContext.instance();
    Resources res = context.getResources(CalendarControlTag.class);
    HttpServletRequest req = context.getRequest();
    String contextUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
    
    JspWriter out = pageContext.getOut();    
    try {
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextUrl + "/wcf/calendar/calendar.css\">\n");
      out.write("<script src=\"" + contextUrl + "/wcf/calendar/calendar.js\" language=\"JavaScript1.2\" type=\"text/javascript\"></script>\n");      
      out.write("<script>\n");
      out.write("  jscalendarImgDir = \"" + contextUrl + "/wcf/calendar/\";\n");     
      //jscalendarMonthName = new Array("January","February","March","April","May","June","July","August","September","October","November","December");
      out.write("  jscalendarMonthName = new Array(" + res.getString("cal.month.fullnames") + ");\n");      
      //jscalendarMonthName2 = new Array("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC");
      out.write("  jscalendarMonthName2 = new Array(" + res.getString("cal.month.shortnames") + ");\n");      
      //var jscalendarDayName = jscalendarStartAt==0 ? new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat") : new Array("Mon","Tue","Wed","Thu","Fri","Sat","Sun");
      out.write("  jscalendarDayName = " + res.getString("cal.days.shortnames") + "\n");
      //jscalendarTodayString = "Today is"
      out.write("  jscalendarTodayString = " + res.getString("cal.todaystring") + ";\n");
      //jscalendarWeekString = "Wk"
      out.write("  jscalendarWeekString = " + res.getString("cal.weekstring") + ";\n");            
      //var jscalendarGotoString = "Go To Current Month"
      out.write("  jscalendarGotoString = " + res.getString("cal.goto.current") + "\n");      
      //var jscalendarScrollLeftMessage = "Click to scroll to previous month. Hold mouse button to scroll automatically."
      out.write("  jscalendarScrollLeftMessage = " + res.getString("cal.scroll.left") + "\n");
      //var jscalendarScrollRightMessage = "Click to scroll to next month. Hold mouse button to scroll automatically."
      out.write("  jscalendarScrollRightMessage = " + res.getString("cal.scroll.right") + "\n");
      //var jscalendarSelectMonthMessage = "Click to select a month."
      out.write("  jscalendarSelectMonthMessage = " + res.getString("cal.sel.month") + "\n");
      //var jscalendarSelectYearMessage = "Click to select a year."
      out.write("  jscalendarSelectYearMessage = " + res.getString("cal.sel.year") + "\n");
      //var jscalendarSelectDateMessage = "Select [date] as date." // do not replace [date], it will be replaced by date.
      out.write("  jscalendarSelectDateMessage = " + res.getString("cal.sel.date") + "\n");
      //Nochmal initialisieren nach Änderung der Variablen
      out.write("  jscalendarInit0();\n");      
      out.write("</script>\n");      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return super.doStartTag();
  }
}
