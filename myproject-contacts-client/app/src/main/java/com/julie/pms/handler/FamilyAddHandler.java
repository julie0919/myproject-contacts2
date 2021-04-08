package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.pms.domain.Family;
import com.julie.util.Prompt;

public class FamilyAddHandler implements Command {

  //가족 등록 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    Family f = new Family();

    f.setName(Prompt.printString("이름> "));
    if (f.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setTel(Prompt.printString("전화번호> "));
    if (f.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setMail(Prompt.printString("이메일> "));
    if (f.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setAddress(Prompt.printString("주소> "));
    if (f.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setBirth(Prompt.printDate("생일> "));
    if (f.getBirth() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("family/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s", 
        f.getName(), f.getTel(), f.getMail(), f.getAddress(), f.getBirth()));
    out.flush();

    // 서버의 응답을 읽는다.
    String status = in.readUTF();
    in.readInt();

    if (status.equals("error")) {
      System.out.println(in.readUTF());
      return;
    }

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
