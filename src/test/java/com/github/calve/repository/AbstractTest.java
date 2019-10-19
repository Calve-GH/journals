package com.github.calve.repository;

import com.github.calve.service.ApplicationService;
import com.github.calve.service.ExecutorService;
import com.github.calve.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

//import org.slf4j.bridge.SLF4JBridgeHandler;
//import ru.javawebinar.topjava.ActiveDbProfileResolver;
//import ru.javawebinar.topjava.Profiles;
//import ru.javawebinar.topjava.TimingExtension;
//import static ru.javawebinar.topjava.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
//@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@ExtendWith(TimingExtension.class)
public abstract class AbstractTest {

    @Autowired
    private Environment env;

    @Autowired
    protected ApplicationService service;
    @Autowired
    protected ExecutorService executorService;
    @Autowired
    protected InfoService infoService;

    static {
        // needed only for java.util.logging (postgres driver)
//        SLF4JBridgeHandler.install();
    }

    boolean isJpaBased() {
//        return Arrays.stream(env.getActiveProfiles()).noneMatch(Profiles.JDBC::equals);
        return true;
    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        assertThrows(exceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
//                throw getRootCause(e);
            }
        });
    }
}
