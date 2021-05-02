package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.julie.util.Prompt;

public class SchoolDeleteHandler implements Command {

  @Override
  public void service() throws Exception {
    int no = Prompt.printInt("삭제하고 싶은 연락처의 번호를 입력하세요> ");

    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("연락처 삭제를 취소하였습니다.");
      return;
    }

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "delete from pms_school where no=?")) {

      stmt.setInt(1, no);

      if (stmt.executeUpdate() == 0) {
        System.out.println("해당 번호의 연락처가 없습니다.");
      } else {
        System.out.println("연락처를 삭제했습니다.");
      }
    }
  }
}
