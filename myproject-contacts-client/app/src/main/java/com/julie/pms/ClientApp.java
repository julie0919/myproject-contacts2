package com.julie.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import com.julie.driver.Statement;
import com.julie.pms.handler.Command;
import com.julie.pms.handler.CompanyAddHandler;
import com.julie.pms.handler.CompanyDeleteHandler;
import com.julie.pms.handler.CompanyDetailHandler;
import com.julie.pms.handler.CompanyListHandler;
import com.julie.pms.handler.CompanyUpdateHandler;
import com.julie.pms.handler.FamilyAddHandler;
import com.julie.pms.handler.FamilyDeleteHandler;
import com.julie.pms.handler.FamilyDetailHandler;
import com.julie.pms.handler.FamilyListHandler;
import com.julie.pms.handler.FamilyUpdateHandler;
import com.julie.pms.handler.SchoolAddHandler;
import com.julie.pms.handler.SchoolDeleteHandler;
import com.julie.pms.handler.SchoolDetailHandler;
import com.julie.pms.handler.SchoolListHandler;
import com.julie.pms.handler.SchoolUpdateHandler;
import com.julie.util.Prompt;

public class ClientApp {

  //사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  String serverAddress;
  int port;

  public static void main(String[] args) {

    ClientApp app = new ClientApp("localhost", 8888);

    try {
      app.execute();

    } catch (Exception e) {
      System.out.println("클라이언트 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }

  public ClientApp(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  public void execute() throws Exception {

    // 서버와 통신하는 것을 대행해 줄 객체를 준비한다.
    Statement stmt = new Statement(serverAddress, port);

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("연락처 추가(가족)", new FamilyAddHandler(stmt));
    commandMap.put("연락처 목록(가족)", new FamilyListHandler(stmt));
    commandMap.put("연락처 상세보기(가족)", new FamilyDetailHandler(stmt));
    commandMap.put("연락처 수정(가족)", new FamilyUpdateHandler(stmt));
    commandMap.put("연락처 삭제(가족)", new FamilyDeleteHandler(stmt));

    commandMap.put("연락처 추가(친구)", new SchoolAddHandler(stmt));
    commandMap.put("연락처 목록(친구)", new SchoolListHandler(stmt));
    commandMap.put("연락처 상세보기(친구)", new SchoolDetailHandler(stmt));
    commandMap.put("연락처 수정(친구)", new SchoolUpdateHandler(stmt));
    commandMap.put("연락처 삭제(친구)", new SchoolDeleteHandler(stmt));

    commandMap.put("연락처 추가(회사)", new CompanyAddHandler(stmt));
    commandMap.put("연락처 목록(회사)", new CompanyListHandler(stmt));
    commandMap.put("연락처 상세보기(회사)", new CompanyDetailHandler(stmt));
    commandMap.put("연락처 수정(회사)", new CompanyUpdateHandler(stmt));
    commandMap.put("연락처 삭제(회사)", new CompanyDeleteHandler(stmt));

    try (Socket socket = new Socket(this.serverAddress, this.port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream())) {

      while (true) {
        String command = Prompt.printString("--------------------원하시는 메뉴를 선택해주세요--------------------\n"
            + ">>연락처 추가(가족/친구/회사)<<\n>>연락처 목록(가족/친구/회사)<<\n>>연락처 상세보기(가족/친구/회사)<<\n"
            + ">>연락처 수정(가족/친구/회사)<<\n>>연락처 삭제(가족/친구/회사)<<\n>>검색기록조회(처음부터/마지막부터)<<\n"
            + ">>나가기<<\n>> ");

        if (command.length() == 0)
          continue;
        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try { 
          Command commandHandler = commandMap.get(command);

          if (command.equals("검색기록조회(처음부터)")) {
            printCommandHistory(commandQueue.iterator());
          } else if(command.equals("검색기록조회(마지막부터)")) {
            printCommandHistory(commandStack.iterator());

          }else if (command.equals("나가기")) {
            // 서버에게 종료한다고 메세지를 보낸다.
            out.writeUTF("나가기");
            out.writeInt(0);
            out.flush();

            // 서버의 응답을 읽는다.
            //  - 서버가 보낸 응답을 읽지 않으면 프로토콜 위반이다.
            //  - 서버가 보낸 데이터를 사용하지 않더라도 프로토콜 규칙에 따라 읽어야한다.
            in.readUTF();
            in.readInt();

            System.out.println("안녕!");
            return;

          } else if (commandHandler == null) {
            System.out.println("실행할 수 없는 명령입니다.");
          }  else {
            commandHandler.service();
          }

        } catch (Exception e) {
          System.out.println("------------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s\n", e.getMessage());
          System.out.println("------------------------------------------");
        }
        System.out.println(); // 이전 명령의 실행을 구분하기 위해 빈 줄 출력
      }

    } catch (Exception e) {
      System.out.println("서버와 통신하는 중에 오류 발생!");
    }
    Prompt.close();
  }

  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++count % 5) == 0) {
        String input = Prompt.printString(": ");
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}
