package com.julie.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FamilyListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    try (Connection con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select * from pms_family order by no desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d) %s / %s / %s / %s / %s\n",
            rs.getInt("no"), rs.getString("name"), rs.getString("tel"), rs.getString("mail"), rs.getString("address"), rs.getDate("birth"));
      }
    }
  }
}
