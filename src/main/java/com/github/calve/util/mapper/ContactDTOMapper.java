package com.github.calve.util.mapper;

import com.github.calve.model.etc.Contact;
import com.github.calve.model.etc.Executor;
import com.github.calve.to.etc.ContactTo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContactDTOMapper {
    ContactDTOMapper CDM_INSTANCE = Mappers.getMapper(ContactDTOMapper.class);

    Contact getContact(ContactTo contact);

    Executor getExecutor(ContactTo contact);

    ContactTo getContactTo(Contact contact);

    ContactTo getExecutorTo(Executor executor);
}
