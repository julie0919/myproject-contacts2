package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.util.Prompt;

public class SchoolDeleteHandler implements Command {

  Statement stmt;

  public SchoolDeleteHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    int no = Prompt.printInt("삭제하고 싶은 연락처의 번호를 입력하세요> ");

    stmt.executeQuery("school/select", Integer.toString(no));

    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("연락처 삭제를 취소하였습니다.");
      return;
    }

    stmt.executeUpdate("school/delete", Integer.toString(no));
    System.out.println("연락처를 삭제하였습니다.");
  }
}
