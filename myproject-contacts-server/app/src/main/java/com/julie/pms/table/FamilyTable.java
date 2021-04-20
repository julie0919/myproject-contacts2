package com.julie.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.julie.pms.domain.Family;
import com.julie.util.JsonFileHandler;
import com.julie.util.Request;
import com.julie.util.Response;

public class FamilyTable implements DataTable {

  File jsonFile = new File("family.json");
  List<Family> list;

  public FamilyTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Family.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Family family = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "family/insert":

        fields = request.getData().get(0).split(",");

        family = new Family();

        if (list.size() > 0) {
          family.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          family.setNo(1);
        }

        family.setName(fields[0]);
        family.setTel(fields[1]);
        family.setMail(fields[2]);
        family.setAddress(fields[3]);
        family.setBirth(Date.valueOf(fields[4]));

        list.add(family);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "family/selectall":
        for (Family f : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              f.getNo(), f.getName(), f.getTel(), f.getMail(), f.getAddress(), f.getBirth()));
        }
        break;

      case "family/select":
        int no = Integer.parseInt(request.getData().get(0));

        family = getFamily(no);
        if (family != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              family.getNo(), 
              family.getName(), 
              family.getTel(),
              family.getMail(),
              family.getAddress(),
              family.getBirth()));
        } else {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }
        break;

      case "family/update":
        fields = request.getData().get(0).split(",");

        family = getFamily(Integer.parseInt(fields[0]));
        if (family == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        family.setName(fields[1]);
        family.setTel(fields[2]);
        family.setMail(fields[3]);
        family.setAddress(fields[4]);
        family.setBirth(Date.valueOf(fields[5]));

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "family/delete":
        no = Integer.parseInt(request.getData().get(0));
        family = getFamily(no);
        if (family == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        list.remove(family);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Family getFamily(int familyNo) {
    for (Family f : list) {
      if (f.getNo() == familyNo) {
        return f;
      }
    }
    return null;
  }
}
