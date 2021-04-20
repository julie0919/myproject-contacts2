package com.julie.pms.table;

import java.io.File;
import java.util.List;
import com.julie.pms.domain.School;
import com.julie.util.JsonFileHandler;
import com.julie.util.Request;
import com.julie.util.Response;

public class SchoolTable implements DataTable {

  File jsonFile = new File("school.json");
  List<School> list;

  public SchoolTable() {
    list = JsonFileHandler.loadObjects(jsonFile, School.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    School school = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "school/insert":

        fields = request.getData().get(0).split(",");

        school = new School();

        if (list.size() > 0) {
          school.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          school.setNo(1);
        }

        school.setName(fields[0]);
        school.setTel(fields[1]);
        school.setMail(fields[2]);
        school.setSchool(fields[3]);
        school.setAddress(fields[4]);

        list.add(school);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "school/selectall":
        for (School s : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              s.getNo(), s.getName(), s.getTel(), s.getMail(), s.getSchool(), s.getAddress()));
        }
        break;

      case "school/select":
        int no = Integer.parseInt(request.getData().get(0));

        school = getSchool(no);
        if (school != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              school.getNo(), 
              school.getName(), 
              school.getTel(),
              school.getMail(),
              school.getSchool(),
              school.getAddress()));
        } else {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }
        break;

      case "school/update":
        fields = request.getData().get(0).split(",");

        school = getSchool(Integer.parseInt(fields[0]));
        if (school == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        school.setName(fields[1]);
        school.setTel(fields[2]);
        school.setMail(fields[3]);
        school.setSchool(fields[4]);
        school.setAddress(fields[5]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "school/delete":
        no = Integer.parseInt(request.getData().get(0));
        school = getSchool(no);
        if (school == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        list.remove(school);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private School getSchool(int schoolNo) {
    for (School s : list) {
      if (s.getNo() == schoolNo) {
        return s;
      }
    }
    return null;
  }
}
