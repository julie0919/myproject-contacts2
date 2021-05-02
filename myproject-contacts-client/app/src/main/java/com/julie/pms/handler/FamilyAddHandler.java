package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.pms.domain.Family;
import com.julie.util.Prompt;

public class FamilyAddHandler implements Command {

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

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "insert into pms_family(name,tel,mail,address,birth) values(?,?,?,?,?")) {

      stmt.setString(1, f.getName());
      stmt.setString(2, f.getTel());
      stmt.setString(3, f.getMail());
      stmt.setString(4, f.getAddress());
      stmt.setDate(5, f.getBirth());

      stmt.executeUpdate();
      System.out.println("연락처 등록을 완료했습니다.");
    }
  }
}
