package com.epam.demo.mapper;

import com.epam.demo.Entity.Employee;
import com.epam.demo.dto.EmployeeDto;
import com.epam.demo.repository.DataDictRepository;
import com.epam.demo.service.LoginService;
import com.epam.demo.vo.EmployeeVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
    Employee convertDTO(EmployeeDto employeeDto);

    Employee convertDtoToEntity(@MappingTarget Employee employee, EmployeeDto employeeDto);

    EmployeeVO convertToVO(Employee employee);
    

}
