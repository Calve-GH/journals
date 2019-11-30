package com.github.calve.to.common;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.common.Outgoing;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.calve.util.mapper.CommonDTOMapper.CDM_INSTANCE;

public class CommonTransformUtil {

    public CommonTransformUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static OutgoingTo packOutgoing(Outgoing mail) {
        return CDM_INSTANCE.getOutgoingTo(mail);
    }

    public static Outgoing unpackOutgoing(OutgoingTo dto) {
        return CDM_INSTANCE.getOutgoing(dto);
    }

    public static List<OutgoingTo> packOutgoingList(List<Outgoing> src) {
        return src.stream().map(CommonTransformUtil::packOutgoing).collect(Collectors.toList());
    }

    public static IncomingTo packIncoming(Incoming mail) {
        return CDM_INSTANCE.getIncomingTo(mail);
    }

    public static Incoming unpackIncoming(IncomingTo dto) {
        return CDM_INSTANCE.getIncoming(dto);
    }

    public static List<IncomingTo> packIncomingList(List<Incoming> src) {
        return src.stream().map(CommonTransformUtil::packIncoming).collect(Collectors.toList());
    }
}
