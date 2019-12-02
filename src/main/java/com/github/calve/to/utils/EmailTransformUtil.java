package com.github.calve.to.utils;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.email.Sent;
import com.github.calve.to.email.EmailTo;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.calve.util.mapper.EmailDTOMapper.*;
public class EmailTransformUtil {

    private EmailTransformUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static EmailTo packInbox(Inbox mail) {
        return EDM_INSTANCE.getInboxTo(mail);
    }

    public static Inbox unpackInbox(EmailTo dto) {
        return EDM_INSTANCE.getInbox(dto);
    }

    public static List<EmailTo> packInboxList(List<Inbox> src) {
        return src.stream().map(EmailTransformUtil::packInbox).collect(Collectors.toList());
    }

    public static EmailTo packSent(Sent mail) {
        return EDM_INSTANCE.getSentTo(mail);
    }

    public static Sent unpackSent(EmailTo dto) {
        return EDM_INSTANCE.getSent(dto);
    }

    public static List<EmailTo> packSentList(List<Sent> src) {
        return src.stream().map(EmailTransformUtil::packSent).collect(Collectors.toList());
    }
}
