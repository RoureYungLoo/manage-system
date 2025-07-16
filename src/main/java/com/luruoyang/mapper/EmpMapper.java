package com.luruoyang.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.model.dto.*;
import com.luruoyang.model.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper extends BaseMapper<Emp> {
  List<Emp> findAll() throws Exception;

  //  int deleteById(List<Long> ids) throws Exception;
  int deleteBatch(List<Long> ids) throws Exception;

  int insert(Emp emp);

  int updateEmpById(Emp emp);

  Emp getById(Long id);

  List<Emp> getEmpPage(@Param("empPageParam") EmpPageParam empPageParam, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

  List<Emp> getEmpPage2(@Param("empPageParam") EmpPageParam empPageParam);

  @Select("select count(*) from tb_emp")
  Long count();

  List<EmpGenderDto> genderStatistics();

  //  EmpJobDto jobStatistics();
  List<EmpJobDto> jobStatistics();

  List<EmpJobDtoForTest> jobStatisticsForTest();

  List<Emp> findByIds(List<Long> empIds);

  Integer deleteById(Long id);


  List<Emp> findAllEmps();

  Long getCountByDeptId(Long deptId);

  Emp login(LoginInfoDto loginInfo);

  int increase(@Param("ew") Wrapper wrapper, @Param("amount") int amount);
}
