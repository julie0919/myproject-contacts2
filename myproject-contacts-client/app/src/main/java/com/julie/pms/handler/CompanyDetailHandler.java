package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.util.Prompt;

public class CompanyDetailHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고 싶은 연락처의 번호를 입력하세요> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt =con.prepareStatement(
            "select * from pms_company where no=?")) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 연락처가 없습니다.");
          return;
        }

        System.out.printf("이름: %s\n", rs.getString("name"));
        System.out.printf("전화번호: %s\n", rs.getString("tel"));
        System.out.printf("이메일: %s\n", rs.getString("mail"));
        System.out.printf("직장: %s\n", rs.getString("work"));
        System.out.printf("주소: %s\n", rs.getString("address"));
      }
    }
  }
}
