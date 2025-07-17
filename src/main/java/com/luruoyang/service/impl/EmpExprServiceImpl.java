package com.luruoyang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.model.dto.ExprDto;
import com.luruoyang.mapper.EmpExprMapper;
import com.luruoyang.model.pojo.EmpExpr;
import com.luruoyang.service.EmpExprService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ruoyang
 */
@Service
@Slf4j
public class EmpExprServiceImpl extends ServiceImpl<EmpExprMapper, EmpExpr> implements EmpExprService {

  // @Autowired
  // private EmpExprMapper empEXprMapper;

  @Override
  public boolean saveExprList(List<EmpExpr> exprList) {
    // empEXprMapper.saveBatch(exprList);
    return this.saveBatch(exprList);
  }

  @Override
  public boolean deleteExprList(List<Long> empIds) {
    log.info("批量删除工作经历: {}", empIds);
    LambdaUpdateWrapper<EmpExpr> wrapper = Wrappers.lambdaUpdate();
    wrapper.in(CollectionUtils.isNotEmpty(empIds), EmpExpr::getTbEmpId, empIds);
    return this.remove(wrapper);
  }

  @Override
  public boolean deleteByExprListById(Long id) {
    return this.removeById(id);
  }

  @Override
  public List<EmpExpr> findByEmpId(Long id) {
    LambdaQueryWrapper<EmpExpr> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(Objects.nonNull(id), EmpExpr::getTbEmpId, id);
    return this.list(wrapper);
  }

  @Override
  public List<EmpExpr> findAll() {
    return this.list();
  }

  @Override
  public List<ExprDto> findByEmpIds(List<Long> empIds) {
    ArrayList<ExprDto> exprDtos = new ArrayList<>();
    LambdaQueryWrapper<EmpExpr> wrapper = Wrappers.lambdaQuery();
    empIds.forEach(empId -> {
      wrapper.eq(Objects.nonNull(empId), EmpExpr::getTbEmpId, empId);
      List<EmpExpr> empExprList = this.list(wrapper);
      exprDtos.add(new ExprDto(empId, empExprList));
    });
    return exprDtos;
  }
}
