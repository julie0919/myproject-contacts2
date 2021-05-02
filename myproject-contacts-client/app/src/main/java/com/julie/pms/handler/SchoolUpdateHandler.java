package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.pms.domain.School;
import com.julie.util.Prompt;

public class SchoolUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고 싶은 연락처의 번호를 입력하세요> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select * from pms_school where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_school set name=?,tel=?,mail=?,school=?,address=? where no=?")) {

      School s = new School();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 연락처가 없습니다.");
          return;
        }

        s.setNo(no);
        s.setName(rs.getString("name"));
        s.setTel(rs.getString("tel"));
        s.setMail(rs.getString("mail"));
        s.setSchool(rs.getString("school"));
        s.setAddress(rs.getString("address"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력받는다.
      s.setName(Prompt.printString(String.format("이름(%s)> ", s.getName())));
      s.setTel(Prompt.printString(String.format("전화번호(%s)> ", s.getTel())));
      s.setMail(Prompt.printString(String.format("이메일(%s)> ", s.getMail())));
      s.setSchool(Prompt.printString(String.format("소속 그룹(%s)> ", s.getSchool())));
      s.setAddress(Prompt.printString(String.format("주소(%s)> ", s.getAddress())));

      String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("연락처 수정을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 연락처 변경을 요청한다.
      stmt2.setString(1, s.getName());
      stmt2.setString(2, s.getTel());
      stmt2.setString(3, s.getMail());
      stmt2.setString(4, s.getSchool());
      stmt2.setString(5, s.getAddress());

      System.out.println("연락처 정보를 수정하였습니다.");
    }
  }
}
