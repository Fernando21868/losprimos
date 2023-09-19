package com.example.backend.service;

import com.example.backend.dto.request.PatientDTORequest;
import com.example.backend.dto.request.PersonDTORequest;
import com.example.backend.dto.response.PatientDTOResponse;
import com.example.backend.dto.response.PersonDTOResponse;

public interface IPatientService extends IPersonService<PatientDTOResponse, PatientDTORequest>{
}
