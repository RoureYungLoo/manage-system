package com.luruoyang;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luruoyang.mapper.DeptMapper;
import com.luruoyang.mapper.EmpMapper;
import com.luruoyang.model.dto.EmpDto;
import com.luruoyang.model.pojo.Dept;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.service.IEmpService;
import com.luruoyang.utils.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class MybatisPlusTest {

  @Autowired
  private EmpMapper empMapper;

  @Test
  public void testQueryWrapper() {
    QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
    queryWrapper.likeRight("name", "李");
    queryWrapper.select("name", "username", "salary");
    List<Emp> deptList = empMapper.selectList(queryWrapper);
    deptList.forEach(System.out::println);
  }

  @Test
  public void testUpdateWrapper() {
    UpdateWrapper<Emp> updateWrapper = new UpdateWrapper<>();
    updateWrapper.setSql("salary = salary + 1000")
        .in("id", 107, 108, 218);
    int updated = empMapper.update(updateWrapper);
    System.out.println(updated);
  }

  @Test
  public void testLambdaQueryWrapper() {
    LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.like(Emp::getName, "李")
        .ge(Emp::getSalary, 6000);
    List<Emp> empList = empMapper.selectList(lambdaQueryWrapper);
    empList.forEach(System.out::println);
  }

  @Test
  public void testLambdaUpdateWrapper() {
    /* mock param */
    Emp param = new Emp();
    param.setSalary(10000L);

    LambdaUpdateWrapper<Emp> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    lambdaUpdateWrapper.in(Emp::getId, 107, 108, 218)
        .setSql("salary=salary+1000");
//        .set(Objects.nonNull(param.getSalary()), Emp::getSalary, param.getSalary())
    int updated = empMapper.update(lambdaUpdateWrapper);
    System.out.println(updated);
  }

  /* 分页 */
  @Test
  public void testPagination() {
    EmpDto dto = EmpDto.builder().page(1).pageSize(3).build();
    /* 模拟前端传递的参数 */
    dto.setName("李");
    dto.setGender(1);

    /* 第 1 步: 注入配置类 com.luruoyang.config.MybatisPlusConfiguration */

    IPage<Emp> page = new Page<>(dto.getPage(), dto.getPageSize());
    LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

    /* 第 2 步: 准备 queryWrapper */
    String name = dto.getName();
    if (Objects.isNull(name)) name = "";
    queryWrapper.like(StringUtils.hasText(name.trim()), Emp::getName, name);

    IPage<Emp> empIPage = empMapper.selectPage(page, queryWrapper);

    PageResult<Emp> pageResult = PageResult.getResult(empIPage.getRecords(), empIPage.getTotal());

    System.out.println(pageResult);


  }

  /*自定义SQL*/
  @Test
  public void testSQL() {
    int amount = 1000;

    UpdateWrapper<Emp> updateWrapper = new UpdateWrapper<>();
    updateWrapper.in("id", 107, 108, 219);

    LambdaUpdateWrapper<Emp> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    lambdaUpdateWrapper.in(Emp::getId, 107, 108, 219);

//    int updated = empMapper.increase(updateWrapper, amount);
    int updated = empMapper.increase(lambdaUpdateWrapper, amount);
    System.out.println(updated);
  }

  @Autowired
  private IEmpService iEmpService;

  /* IService */
  @Test
  public void testIService() {
    LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

    /* 条件查询 */
    queryWrapper.like(Emp::getName, "李")
        .eq(Emp::getGender, 1);
//        .between(Emp::getEntryDate, "2020-01-01", "2025-12-31")
//        .between(Emp::getSalary, 6000, 10000);

    /*分页查询*/
    IPage<Emp> iPage = new Page<>(1,100);

//    List<Emp> list = iEmpService.list(queryWrapper);
    List<Emp> list = iEmpService.list(iPage, queryWrapper);
    list.forEach(System.out::println);
  }

  @Test
  public void testPaginationAndQueryParam() {

  }

}
