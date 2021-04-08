package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.util.Prompt;

public class FamilyDeleteHandler implements Command {

  // 가족 연락처 삭제 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[연락처 삭제]");
    int no = Prompt.printInt("삭제하고싶은 연락처의 번호를 입력하세요> ");

    // 서버에 해당 번호의 게시글이 있는지 조회한다.
    out.writeUTF("family/select");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();
    String data = in.readUTF();

    if (status.equals("error")) {
      System.out.println(data);
      return;
    }

    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (!input.equalsIgnoreCase("Y")) {
      System.out.println("연락처 삭제를 취소하였습니다.");
      return;
    }

    // 서버에 데이터 삭제를 요청한다.
    out.writeUTF("family/delete");
    out.writeInt(1);
    out.writeUTF(Integer.toString(no));
    out.flush();

    // 서버의 응답을 읽는다.
    status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("연락처를 삭제하였습니다.");
  }
}
