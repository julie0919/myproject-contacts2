package com.julie.pms.handler;

import com.julie.driver.Statement;
import com.julie.pms.domain.School;
import com.julie.util.Prompt;

public class SchoolAddHandler implements Command {

  Statement stmt;

  public SchoolAddHandler(Statement stmt) {
    this.stmt = stmt;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[친구 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    School s = new School();

    s.setName(Prompt.printString("이름> "));
    if (s.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setTel(Prompt.printString("전화번호> "));
    if (s.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setMail(Prompt.printString("이메일> "));
    if (s.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setSchool(Prompt.printString("소속 그룹(학교/전공/학번)> "));
    if (s.getSchool() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setAddress(Prompt.printString("주소> "));
    if (s.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }
    stmt.executeUpdate("school/insert", String.format("%s,%s,%s,%s,%s",
        s.getName(), s.getTel(), s.getMail(), s.getSchool(), s.getAddress()));

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
