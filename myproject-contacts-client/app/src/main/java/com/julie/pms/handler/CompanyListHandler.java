package com.julie.pms.handler;

import java.util.Iterator;
import com.julie.driver.Statement;

public class CompanyListHandler implements Command {

  Statement stmt;

  public CompanyListHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");
    Iterator<String> results = stmt.executeQuery("company/selectall");

    while (results.hasNext()) {
      String[] fields = results.next().split(",");
      System.out.printf("%s, %s, %s, %s, %s\n",
          fields[0], fields[1], fields[2], fields[3], fields[4]);
    }
  }
}
