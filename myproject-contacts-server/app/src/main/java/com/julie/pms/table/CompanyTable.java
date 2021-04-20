package com.julie.pms.table;

import java.io.File;
import java.util.List;
import com.julie.pms.domain.Company;
import com.julie.util.JsonFileHandler;
import com.julie.util.Request;
import com.julie.util.Response;

public class CompanyTable implements DataTable {

  File jsonFile = new File("company.json");
  List<Company> list;

  public CompanyTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Company.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception {
    Company company = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "company/insert":

        fields = request.getData().get(0).split(",");

        company = new Company();

        if (list.size() > 0) {
          company.setNo(list.get(list.size() - 1).getNo() + 1);
        } else {
          company.setNo(1);
        }

        company.setName(fields[0]);
        company.setTel(fields[1]);
        company.setMail(fields[2]);
        company.setWork(fields[3]);
        company.setAddress(fields[4]);

        list.add(company);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "company/selectall":
        for (Company c : list) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              c.getNo(), c.getName(), c.getTel(), c.getMail(), c.getWork(), c.getAddress()));
        }
        break;

      case "company/select":
        int no = Integer.parseInt(request.getData().get(0));

        company = getCompany(no);
        if (company != null) {
          response.appendData(String.format("%d,%s,%s,%s,%s,%s", 
              company.getNo(), 
              company.getName(), 
              company.getTel(),
              company.getMail(),
              company.getWork(),
              company.getAddress()));
        } else {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }
        break;

      case "company/update":
        fields = request.getData().get(0).split(",");

        company = getCompany(Integer.parseInt(fields[0]));
        if (company == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        company.setName(fields[1]);
        company.setTel(fields[2]);
        company.setMail(fields[3]);
        company.setWork(fields[4]);
        company.setAddress(fields[5]);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      case "company/delete":
        no = Integer.parseInt(request.getData().get(0));
        company = getCompany(no);
        if (company == null) {
          throw new Exception("해당 번호의 연락처가 없습니다.");
        }

        list.remove(company);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;

      default:
        throw new Exception("해당 명령을 처리할 수 없습니다.");
    }
  }

  private Company getCompany(int companyNo) {
    for (Company c : list) {
      if (c.getNo() == companyNo) {
        return c;
      }
    }
    return null;
  }
}
