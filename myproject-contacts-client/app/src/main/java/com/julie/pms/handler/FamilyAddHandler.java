package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.pms.domain.Family;
import com.julie.util.Prompt;

public class FamilyAddHandler implements Command {

  Statement stmt;

  public FamilyAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    Family f = new Family();

    f.setName(Prompt.printString("이름> "));
    if (f.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setTel(Prompt.printString("전화번호> "));
    if (f.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setMail(Prompt.printString("이메일> "));
    if (f.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setAddress(Prompt.printString("주소> "));
    if (f.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setBirth(Prompt.printDate("생일> "));
    if (f.getBirth() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    stmt.executeUpdate("family/insert", String.format("%s,%s,%s,%s,%s", 
        f.getName(), f.getTel(), f.getMail(), f.getAddress(), f.getBirth()));

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
