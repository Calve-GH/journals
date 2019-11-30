package com.github.calve.to.journal;

import com.github.calve.model.journal.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.calve.util.mapper.DTOJournalMapper.DJM_INSTANCE;
import static com.github.calve.util.mapper.JournalDTOMapper.JDM_INSTANCE;

// GENERIC 30.11.2019 mb i can use generics;
public final class MailTransformUtil {

    private MailTransformUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static DefaultTo packRequest(Request mail) {
        return DJM_INSTANCE.getRequestTo(mail);
    }

    public static Request unpackRequest(DefaultTo dto) {
        return JDM_INSTANCE.getRequest(dto);
    }

    public static List<DefaultTo> packRequestList(List<Request> src) {
        return src.stream().map(MailTransformUtil::packRequest).collect(Collectors.toList());
    }

    public static DefaultTo packGeneric(Generic mail) {
        return DJM_INSTANCE.getGenericTo(mail);
    }

    public static Generic unpackGeneric(DefaultTo dto) {
        return JDM_INSTANCE.getGeneric(dto);
    }

    public static List<DefaultTo> packGenericList(List<Generic> src) {
        return src.stream().map(MailTransformUtil::packGeneric).collect(Collectors.toList());
    }

    public static ResultTo packInfo(Info mail) {
        return DJM_INSTANCE.getInfoTo(mail);
    }

    public static Info unpackInfo(ResultTo dto) {
        return JDM_INSTANCE.getInfo(dto);
    }

    public static List<ResultTo> packInfoList(List<Info> src) {
        return src.stream().map(MailTransformUtil::packInfo).collect(Collectors.toList());
    }

    public static ResultTo packComplaint(Complaint mail) {
        return DJM_INSTANCE.getComplaintTo(mail);
    }

    public static Complaint unpackComplaint(ResultTo dto) {
        return JDM_INSTANCE.getComplaint(dto);
    }

    public static List<ResultTo> packComplaintList(List<Complaint> src) {
        return src.stream().map(MailTransformUtil::packComplaint).collect(Collectors.toList());
    }

    public static ForeignerTo packForeigner(Foreigner mail) {
        return DJM_INSTANCE.getForeignerTo(mail);
    }

    public static Foreigner unpackForeigner(ForeignerTo dto) {
        return JDM_INSTANCE.getForeigner(dto);
    }

    public static List<ForeignerTo> packForeignerList(List<Foreigner> src) {
        return src.stream().map(MailTransformUtil::packForeigner).collect(Collectors.toList());
    }

    public static ApplicationTo packApplication(Application mail) {
        return DJM_INSTANCE.getApplicationTo(mail);
    }

    public static Application unpackApplication(ApplicationTo dto) {
        return JDM_INSTANCE.getApplication(dto);
    }

    public static List<ApplicationTo> packApplicationList(List<Application> src) {
        return src.stream().map(MailTransformUtil::packApplication).collect(Collectors.toList());
    }
}
