package com.github.calve.util.mapper;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.common.Outgoing;
import com.github.calve.model.etc.Executor;
import com.github.calve.to.common.IncomingTo;
import com.github.calve.to.common.OutgoingTo;
import com.github.calve.to.journal.RootTo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommonDTOMapper {
    CommonDTOMapper CDM_INSTANCE = Mappers.getMapper(CommonDTOMapper.class);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Outgoing getOutgoing(OutgoingTo dto);

    @Mappings(@Mapping(source = "dto", target = "executor", qualifiedByName = "constructExecutor"))
    Incoming getIncoming(IncomingTo dto);

    @Mappings(@Mapping(target = "executor", source = "executor.name"))
    OutgoingTo getOutgoingTo(Outgoing mail);

    @Mappings(@Mapping(target = "executor", source = "executor.name"))
    IncomingTo getIncomingTo(Incoming mail);

    @Named("constructExecutor")
    default Executor getExecutor(RootTo root) {
        //todo stab;
        //refactoring mb;
        return new Executor(root.getExecutor());
    }
}
