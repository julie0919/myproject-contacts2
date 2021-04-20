package com.julie.pms.handler;

import java.util.Iterator;
import com.julie.driver.Statement;

public class FamilyListHandler implements Command {

  Statement stmt;

  public FamilyListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    Iterator<String> results = stmt.executeQuery("family/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s / %s / %s / %s / %s\n",
          fields[0], fields[1], fields[2], fields[3], fields[4]);
    }
  }
}
