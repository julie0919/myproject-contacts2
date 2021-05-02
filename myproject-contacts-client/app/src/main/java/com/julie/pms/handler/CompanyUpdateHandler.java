package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.julie.pms.domain.Company;
import com.julie.util.Prompt;

public class CompanyUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고 싶은 연락처의 번호를 입력하세요> ");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt1 = con.prepareStatement(
            "select * from pms_company where no=?");
        PreparedStatement stmt2 = con.prepareStatement(
            "update pms_company set name=?,tel=?,mail=?,company=?,address=? where no=?")) {

      Company c = new Company();

      // 1) 기존 데이터 조회
      stmt1.setInt(1, no);
      try (ResultSet rs = stmt1.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 연락처가 없습니다.");
          return;
        }

        c.setNo(no);
        c.setName(rs.getString("name"));
        c.setTel(rs.getString("tel"));
        c.setMail(rs.getString("mail"));
        c.setWork(rs.getString("work"));
        c.setAddress(rs.getString("address"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      c.setName(Prompt.printString(String.format("이름(%s)> ", c.getName())));
      c.setTel(Prompt.printString(String.format("전화번호(%s)> ", c.getTel())));
      c.setMail(Prompt.printString(String.format("이메일(%s)> ", c.getMail())));
      c.setWork(Prompt.printString(String.format("직장(%s)> ", c.getWork())));
      c.setAddress(Prompt.printString(String.format("주소(%s)> ", c.getAddress())));

      String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("연락처 수정을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 연락처 변경을 요청한다.
      stmt2.setString(1, c.getName());
      stmt2.setString(2, c.getTel());
      stmt2.setString(3, c.getMail());
      stmt2.setString(4, c.getWork());
      stmt2.setString(5, c.getAddress());

      System.out.println("연락처 정보를 수정하였습니다.");
    }
  }
}
