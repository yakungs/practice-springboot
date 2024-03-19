package com.example.health.mapper;

import com.example.health.entity.User;
import com.example.health.model.MRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toRegisterResponse(User user);

}
