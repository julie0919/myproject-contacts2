package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SchoolListHandler implements Command {

  // 친구 목록 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    // 서버에 데이터 목록을 달라고 요청한다.
    out.writeUTF("school/selectall");
    out.writeInt(0);
    out.flush();

    // 서버의 응답 데이터를 읽는다.
    String status = in.readUTF();
    int length = in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    } 

    for (int i = 0; i < length; i++) {
      String[] fields = in.readUTF().split(",");
      System.out.printf("%s / %s / %s / %s / %s\n", 
          fields[0], fields[1], fields[2], fields[3], fields[4]);      
    }
  }
}
