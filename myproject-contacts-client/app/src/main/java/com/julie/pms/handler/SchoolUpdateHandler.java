package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.util.Prompt;

public class SchoolUpdateHandler implements Command {

  // 친구 연락처 수정 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고싶은 연락처의 번호를 입력하세요> ");

    // 서버에 지정한 번호의 데이터를 요청한다.
    out.writeUTF("school/select");
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
    String name = Prompt.printString(String.format("이름(%s)> ", fields[1]));
    String tel = Prompt.printString(String.format("연락처(%s)> ", fields[2]));
    String mail = Prompt.printString(String.format("이메일(%s)> ", fields[3]));
    String school = Prompt.printString(String.format("소속 그룹(%s)> ", fields[4]));
    String address = Prompt.printString(String.format("주소(%s)> ", fields[5]));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("연락처 수정을 취소하였습니다.");
      return;
    }


    // 서버에 데이터 변경을 요청한다.
    out.writeUTF("school/update");
    out.writeInt(1);
    out.writeUTF(String.format("%d,%s,%s,%s,%s,%s", 
        no, name, tel, mail, school, address));
    out.flush();

    // 서버의 응답을 받는다.
    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("연락처 정보를 수정하였습니다.");
  }
}
