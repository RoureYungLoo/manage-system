package com.luruoyang;

import com.luruoyang.enums.ClientError;
import com.luruoyang.mapper.EmpExprMapper;
import com.luruoyang.model.pojo.EmpExpr;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class EmpExprMapperTest {

  @Autowired
  EmpExprMapper empExprMapper;

  @Test
  public void test() {
    List<Map<Map<String, Long>, List<Map<String, EmpExpr>>>> exprsMapByEmpIds = empExprMapper.findExprsMapByEmpIds(List.of(1L, 2L, 3L));
    for (Map<Map<String, Long>, List<Map<String, EmpExpr>>> exprsMapByEmpId : exprsMapByEmpIds) {
      Set<Map.Entry<Map<String, Long>, List<Map<String, EmpExpr>>>> entries = exprsMapByEmpId.entrySet();
      for (Map.Entry<Map<String, Long>, List<Map<String, EmpExpr>>> entry : entries) {
        System.out.println("===>  entry: "+entry);
        System.out.println(entry.getKey());
        System.out.println(entry.getValue());
      }

    }
  }

  @Test
  public void testEnum(){
    System.out.println(ClientError.INVALID_DATE.getMsg());
  }
}
