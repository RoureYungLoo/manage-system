package com.luruoyang.mapper;

import com.luruoyang.model.dto.DeptDto;
import com.luruoyang.model.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

  @Select("select * from tb_dept")
  List<Dept> findAll();

  @Delete("delete from tb_dept where id = #{id}")
  int deleteById(Long id);

  int save(Dept dept);

  Dept findById(Long id);

  int updateById(@Param("id") Long id, @Param("dept") Dept dept);

  int update(Dept dbDept);

  List<Dept> findPage(Long offset, Long limit);

  List<Dept> findQuery(DeptDto dept);
}
