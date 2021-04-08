package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.util.Prompt;

public class FamilyDetailHandler implements Command {

  // 가족 연락처 검색 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");

    // 서버에 저장한 번호의 데이터를 요청한다.
    out.writeUTF("family/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 받는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    String[] fields = in.readUTF().split(",");

    System.out.printf("이름: %s\n", fields[1]);
    System.out.printf("전화번호: %s\n", fields[2]);
    System.out.printf("이메일: %s\n", fields[3]);
    System.out.printf("주소: %s\n", fields[4]);
    System.out.printf("생일: %s\n", fields[5]);
  }
}
