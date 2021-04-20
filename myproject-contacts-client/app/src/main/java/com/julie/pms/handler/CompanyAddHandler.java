package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.pms.domain.Company;
import com.julie.util.Prompt;

public class CompanyAddHandler implements Command {

  Statement stmt;

  public CompanyAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    Company c = new Company();

    c.setName(Prompt.printString("이름> "));
    if (c.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setTel(Prompt.printString("전화번호> "));
    if (c.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setMail(Prompt.printString("이메일> "));
    if (c.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setWork(Prompt.printString("직장(직위/부서/회사)> "));
    if (c.getWork() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setAddress(Prompt.printString("주소> "));
    if (c.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    stmt.executeUpdate("company/insert", String.format("%s,%s,%s,%s,%s",
        c.getName(), c.getTel(), c.getMail(), c.getWork(), c.getAddress()));

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
