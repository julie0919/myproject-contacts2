package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from pms_company order by no desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d) %s, %s, %s, %s, %s\n",
            rs.getInt("no"),
            rs.getString("name"),
            rs.getString("tel"),
            rs.getString("mail"),
            rs.getString("work"),
            rs.getString("address"));
      }
    }
  }
}