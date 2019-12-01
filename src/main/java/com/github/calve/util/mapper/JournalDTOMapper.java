package com.github.calve.util.mapper;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.*;
import com.github.calve.to.journal.ApplicationTo;
import com.github.calve.to.journal.DefaultTo;
import com.github.calve.to.journal.ForeignerTo;
import com.github.calve.to.journal.RootTo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
//Mapper from DTO to Journal mails divided on two files, for better reading;
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface JournalDTOMapper {

    JournalDTOMapper JDM_INSTANCE = Mappers.getMapper(JournalDTOMapper.class);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Request getRequest(DefaultTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Generic getGeneric(DefaultTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Info getInfo(DefaultTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Complaint getComplaint(DefaultTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Foreigner getForeigner(ForeignerTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Application getApplication(ApplicationTo dto);

    @Named("constructExecutor")
    default Executor getExecutor(RootTo root) {
        //todo stab;
        //refactoring mb;
        return new Executor(root.getExecutor());
    }
}
