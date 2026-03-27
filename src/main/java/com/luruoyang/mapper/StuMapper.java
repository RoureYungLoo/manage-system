package com.luruoyang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.model.dto.StuClazzCountDto;
import com.luruoyang.model.dto.StuDegreeDto;
import com.luruoyang.model.dto.StuPageParam;
import com.luruoyang.model.pojo.Stu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luruoyang
 */
@Mapper
public interface StuMapper extends BaseMapper<Stu> {
  // List<Stu> findPage(StuPageParam stuParam);

  // Integer deleteBatch(List<Long> ids);

  // int save(Stu stu);

  // Stu findStuById(Long id);

  // int updateById(Stu stu);

  // boolean violation(@Param("id") Long id,@Param("violationScore") Integer score);

  // List<StuDegreeDto> degreeStatistic();

   List<StuClazzCountDto> groupByClazz();

}
