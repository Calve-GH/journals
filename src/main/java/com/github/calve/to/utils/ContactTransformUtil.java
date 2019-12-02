package com.github.calve.to.utils;

import com.github.calve.model.etc.Contact;
import com.github.calve.model.etc.Executor;
import com.github.calve.to.etc.ContactTo;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.calve.util.mapper.ContactDTOMapper.CDM_INSTANCE;

public class ContactTransformUtil {
    private ContactTransformUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static ContactTo packContact(Contact contact) {
        return CDM_INSTANCE.getContactTo(contact);
    }

    public static Contact unpackContact(ContactTo dto) {

        return CDM_INSTANCE.getContact(dto);
    }
    public static List<ContactTo> packContactList(List<Contact> src) {
        return src.stream().map(ContactTransformUtil::packContact).collect(Collectors.toList());
    }

    public static ContactTo packExecutor(Executor executor) {
        return CDM_INSTANCE.getExecutorTo(executor);
    }

    public static Executor unpackExecutor(ContactTo dto) {
        return CDM_INSTANCE.getExecutor(dto);
    }

    public static List<ContactTo> packExecutorList(List<Executor> src) {
        return src.stream().map(ContactTransformUtil::packExecutor).collect(Collectors.toList());
    }
}
