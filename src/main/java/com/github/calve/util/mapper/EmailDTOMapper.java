package com.github.calve.util.mapper;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;
import com.github.calve.to.email.EmailTo;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmailDTOMapper {
    EmailDTOMapper EDM_INSTANCE = Mappers.getMapper(EmailDTOMapper.class);

    @Mappings({@Mapping(target = "contact", source = "contact.name"),
            @Mapping(target = "option", source = "mail",  qualifiedByName = "stringAnswer"),
            })
    EmailTo getInboxTo(Inbox mail);

    @Mappings({@Mapping(target = "contact", source = "contact.name"),
            @Mapping(target = "option", source = "auto")})
    EmailTo getSentTo(Sent mail);

    @Mappings({@Mapping(source = "dto", target = "contact", qualifiedByName = "constructExecutor"),
            @Mapping(source = "dto", target = "answer", qualifiedByName = "booleanAnswer"),
            })
    Inbox getInbox(EmailTo dto);

    @Mappings({@Mapping(source = "dto", target = "contact", qualifiedByName = "constructExecutor"),
            @Mapping(target = "auto", source = "option")})
    Sent getSent(EmailTo dto);

    @Named("constructExecutor")
    default Contact getExecutor(EmailTo dto) {
        System.out.println(dto.getContact());//todo sout;
        //todo stab;
        //refactoring mb;
        return new Contact(dto.getContact(), null);
    }
    @Named("stringAnswer")
    default String getAnswerTo(Inbox email) {
        return email.getAnswer() ? "yes" : "no";
    }
    @Named("booleanAnswer")
    default boolean getAnswer(EmailTo email) {
        return !Objects.isNull(email.getOption());
    }
}
