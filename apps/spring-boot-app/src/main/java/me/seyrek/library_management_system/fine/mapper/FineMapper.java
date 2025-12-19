package me.seyrek.library_management_system.fine.mapper;

import me.seyrek.library_management_system.fine.dto.*;
import me.seyrek.library_management_system.fine.model.Fine;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FineMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "loan.id", target = "loanId")
    FineDto toFineDto(Fine fine);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "status", constant = "UNPAID")
    @Mapping(target = "fineDate", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    Fine fromFineCreateRequest(FineCreateRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fineDate", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    void updateFineFromRequest(FineUpdateRequest request, @MappingTarget Fine fine);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "loan", ignore = true)
    @Mapping(target = "fineDate", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    void patchFineFromRequest(FinePatchRequest request, @MappingTarget Fine fine);

    @Mapping(target = "userId", source = "userId")
    FineSearchRequest toFineSearchRequest(FineUserSearchRequest userRequest, Long userId);
}
