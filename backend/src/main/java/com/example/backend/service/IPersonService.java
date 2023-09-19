package com.example.backend.service;

import com.example.backend.dto.request.PersonDTORequest;
import com.example.backend.dto.response.PersonDTOResponse;
import com.example.backend.dto.response.ResponseSuccessDto;

import java.util.List;

public interface IPersonService<Response extends PersonDTOResponse, Request extends PersonDTORequest> {

    ResponseSuccessDto<List<Response>> getAll();
    ResponseSuccessDto<Response> getById(Long id);
    ResponseSuccessDto<Response> create(Request request);
    ResponseSuccessDto<Response> update(Long id, Request userDtoRequest);
    ResponseSuccessDto<Response> delete(Long id);

}