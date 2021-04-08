package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.pms.domain.School;
import com.julie.util.Prompt;

public class SchoolAddHandler implements Command {

  //친구 등록 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[친구 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    School s = new School();

    s.setName(Prompt.printString("이름> "));
    if (s.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setTel(Prompt.printString("전화번호> "));
    if (s.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setMail(Prompt.printString("이메일> "));
    if (s.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setSchool(Prompt.printString("소속 그룹(학교/전공/학번)> "));
    if (s.getSchool() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setAddress(Prompt.printString("주소> "));
    if (s.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("school/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s",
        s.getName(), s.getTel(), s.getMail(), s.getSchool(), s.getAddress()));
    out.flush();

    // 서버의 응답을 받는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
