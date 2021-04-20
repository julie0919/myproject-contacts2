package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.util.Prompt;

public class FamilyDetailHandler implements Command {

  Statement stmt;

  public FamilyDetailHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고 싶은 연락처의 번호를 입력하세요> ");

    String[] fields = stmt.executeQuery("family/select", Integer.toString(no)).next().split(",");

    System.out.printf("이름: %s\n", fields[1]);
    System.out.printf("전화번호: %s\n", fields[2]);
    System.out.printf("이메일: %s\n", fields[3]);
    System.out.printf("주소: %s\n", fields[4]);
    System.out.printf("생일: %s\n", fields[5]);
  }
}
