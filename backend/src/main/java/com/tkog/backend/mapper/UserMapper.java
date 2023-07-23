package com.tkog.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkog.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
