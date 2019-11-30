package com.github.calve.util.mapper;

import com.github.calve.model.etc.Contact;
import com.github.calve.model.etc.Executor;
import com.github.calve.to.etc.ContactTo;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

// TODO: 30.11.2019 Есть мысть обьединить все интерфейсы;
public interface ContactDTOMapper {
    ContactDTOMapper CDM_INSTANCE = Mappers.getMapper(ContactDTOMapper.class);

    Contact getContact(ContactTo contact);

    Executor getExecutor(ContactTo contact);

    @Mappings(@Mapping(target = "name", source = "alias"))
    ContactTo getContactTo(Contact contact);

    ContactTo getExecutorTo(Executor executor);
}
