package com.julie.pms.table;

import com.julie.util.Request;
import com.julie.util.Response;

public interface DataTable {
  void service(Request request, Response response) throws Exception;
}
