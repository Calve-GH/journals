package com.github.calve.util.mapper;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;
import com.github.calve.to.email.EmailTo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmailDTOMapper {
    EmailDTOMapper EDM_INSTANCE = Mappers.getMapper(EmailDTOMapper.class);

    @Mappings({@Mapping(target = "contact", source = "contact.name"),
            @Mapping(target = "option", source = "answer")})
    EmailTo getInboxTo(Inbox mail);

    @Mappings({@Mapping(target = "contact", source = "contact.name"),
            @Mapping(target = "option", source = "auto")})
    EmailTo getSentTo(Sent mail);

    @Mappings({@Mapping(source = "dto", target = "contact", qualifiedByName = "constructExecutor"),
            @Mapping(target = "answer", source = "option")})
    Inbox getInbox(EmailTo dto);

    @Mappings({@Mapping(source = "dto", target = "contact", qualifiedByName = "constructExecutor"),
            @Mapping(target = "auto", source = "option")})
    Sent getSent(EmailTo dto);

    @Named("constructExecutor")
    default Contact getExecutor(EmailTo dto) {
        //todo stab;
        //refactoring mb;
        return new Contact(dto.getContact(), null);
    }
}
