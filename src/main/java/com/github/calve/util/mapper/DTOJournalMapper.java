package com.github.calve.util.mapper;

import com.github.calve.model.Mail;
import com.github.calve.model.journal.*;
import com.github.calve.to.etc.Remain;
import com.github.calve.to.journal.ApplicationTo;
import com.github.calve.to.journal.DefaultTo;
import com.github.calve.to.journal.ForeignerTo;
import com.github.calve.to.journal.ResultTo;
import com.github.calve.util.DateTimeUtil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
//Mapper from Journal mails to DTO divided on two files, for better reading;
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DTOJournalMapper {
    DTOJournalMapper DJM_INSTANCE = Mappers.getMapper(DTOJournalMapper.class);

    @Mappings({
            @Mapping(target = "executor", source = "executor.name"),
            @Mapping(source = "request", target = "remain", qualifiedByName = "constructRemain"),
            @Mapping(source = "request", target = "excess", qualifiedByName = "excessFlag")})
    DefaultTo getRequestTo(Request request);

    @Mappings({
            @Mapping(target = "executor", source = "executor.name"),
            @Mapping(source = "generic", target = "remain", qualifiedByName = "constructRemainGeneric"),
            @Mapping(source = "generic", target = "excess", qualifiedByName = "excessFlagGeneric")})
    DefaultTo getGenericTo(Generic generic);

    @Mappings({
            @Mapping(target = "executor", source = "executor.name"),
            @Mapping(source = "info", target = "remain", qualifiedByName = "constructRemain"),
            @Mapping(source = "info", target = "excess", qualifiedByName = "excessFlag")})
    ResultTo getInfoTo(Info info);

    @Mappings({
            @Mapping(target = "executor", source = "executor.name"),
            @Mapping(source = "complaint", target = "remain", qualifiedByName = "constructRemain"),
            @Mapping(source = "complaint", target = "excess", qualifiedByName = "excessFlag")})
    ResultTo getComplaintTo(Complaint complaint);

    @Mappings(@Mapping(target = "executor", source = "executor.name"))
    ForeignerTo getForeignerTo(Foreigner foreigner);

    @Mappings({
            @Mapping(target = "executor", source = "executor.name"),
            @Mapping(source = "application", target = "remain", qualifiedByName = "constructRemain"),
            @Mapping(source = "application", target = "excess", qualifiedByName = "excessFlag")})
    ApplicationTo getApplicationTo(Application application);

    @Named("constructRemain")
    default Remain constructRemain10(Mail mail) {
        return DateTimeUtil.initRemains(mail.getIncomeDate(), 14, mail.getDoneDate());
    }

    @Named("excessFlag")
    default boolean constructExcess10(Mail mail) {
        return DateTimeUtil.initExcess(mail.getIncomeDate(), 14, mail.getDoneDate());
    }

    @Named("constructRemainGeneric")
    default Remain constructRemain15(Mail mail) {
        return DateTimeUtil.initRemains(mail.getIncomeDate(), 19, mail.getDoneDate());
    }

    @Named("excessFlagGeneric")
    default boolean constructExcess15(Mail mail) {
        return DateTimeUtil.initExcess(mail.getIncomeDate(), 19, mail.getDoneDate());
    }
}
