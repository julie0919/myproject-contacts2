package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.util.Prompt;

public class FamilyUpdateHandler implements Command {

  Statement stmt;

  public FamilyUpdateHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고 싶은 연락처의 번호를 입력하세요> ");

    String[] fields = stmt.executeQuery("family/select", Integer.toString(no)).next().split(",");
    String name = Prompt.printString(String.format("이름(%s)> ", fields[1]));
    String tel = Prompt.printString(String.format("연락처(%s)> ", fields[2]));
    String mail = Prompt.printString(String.format("이메일(%s)> ", fields[3]));
    String address = Prompt.printString(String.format("주소(%s)> ", fields[4]));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("연락처 수정을 취소하였습니다.");
      return;
    }
    stmt.executeUpdate("family/update", String.format("%d,%s,%s,%s,%s", 
        no, name, tel, mail, address));

    System.out.println("연락처 정보를 수정하였습니다.");
  }
}
