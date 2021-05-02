package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.pms.domain.Family;
import com.julie.util.Prompt;

public class FamilyUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고 싶은 연락처의 번호를 입력하세요> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select * from pms_family where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_family set name=?, tel=?, mail=?, address=?, birth=?")) {

      Family f = new Family();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 연락처가 없습니다.");
          return;
        }

        f.setNo(no);
        f.setName(rs.getString("name"));
        f.setTel(rs.getString("tel"));
        f.setMail(rs.getString("mail"));
        f.setAddress(rs.getString("address"));
        f.setBirth(rs.getDate("birth"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      f.setName(Prompt.printString(String.format("이름(%s)> ", f.getName())));
      f.setTel(Prompt.printString(String.format("전화번호(%s)> ", f.getTel())));
      f.setMail(Prompt.printString(String.format("이메일(%s)> ", f.getMail())));
      f.setAddress(Prompt.printString(String.format("주소(%s)> ", f.getAddress())));
      f.setBirth(Prompt.printDate(String.format("생일(%s)> ", f.getBirth())));

      String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("연락처 수정을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 연락처 변경을 요청한다.
      stmt2.setString(1, f.getName());
      stmt2.setString(2, f.getTel());
      stmt2.setString(3, f.getMail());
      stmt2.setString(4, f.getAddress());
      stmt2.setDate(5, f.getBirth());
      stmt2.executeUpdate();

      System.out.println("연락처 정보를 수정하였습니다.");
    }
  }
}
