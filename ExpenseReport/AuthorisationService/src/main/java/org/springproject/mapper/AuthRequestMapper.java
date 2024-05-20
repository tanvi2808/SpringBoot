package org.springproject.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springproject.dto.AuthRequest;
import org.springproject.model.SignUpRequest;

@Mapper
public interface AuthRequestMapper {

    AuthRequestMapper INSTANCE = Mappers.getMapper(AuthRequestMapper.class);

    SignUpRequest mapToSignUp(AuthRequest authRequest);
}