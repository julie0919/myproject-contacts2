package com.julie.pms.handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import com.julie.pms.domain.Company;
import com.julie.util.Prompt;

public class CompanyAddHandler implements Command {

  // 회사 등록 메소드
  @Override
  public void service(DataInputStream in, DataOutputStream out) throws Exception {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 빈 문자열을 입력해주세요 **");
    System.out.println();

    Company c = new Company();

    c.setName(Prompt.printString("이름> "));
    if (c.getName() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setTel(Prompt.printString("전화번호> "));
    if (c.getTel() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setMail(Prompt.printString("이메일> "));
    if (c.getMail() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setWork(Prompt.printString("직장(직위/부서/회사)> "));
    if (c.getWork() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setAddress(Prompt.printString("주소> "));
    if (c.getAddress() == null) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    // 서버에 데이터 입력을 요청한다.
    out.writeUTF("company/insert");
    out.writeInt(1);
    out.writeUTF(String.format("%s,%s,%s,%s,%s",
        c.getName(), c.getTel(), c.getMail(), c.getWork(), c.getAddress()));
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
