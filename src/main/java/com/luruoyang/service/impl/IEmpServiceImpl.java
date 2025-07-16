package com.luruoyang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.mapper.EmpMapper;
import com.luruoyang.model.pojo.Emp;
import com.luruoyang.service.IEmpService;
import org.springframework.stereotype.Service;


@Service
public class IEmpServiceImpl extends ServiceImpl<EmpMapper,Emp> implements IEmpService {

}
