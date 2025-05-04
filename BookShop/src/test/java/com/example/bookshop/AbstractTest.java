package com.example.bookshop;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bookshop.TestUtil.getCauseException;
import static com.example.bookshop.util.ValidationUtil.getRootCause;

@SpringBootTest
@ActiveProfiles({"test"})
//@Sql(scripts = "classpath:db/bookshop_data.sql", config = @SqlConfig(encoding = "UTF-8"))
//@Transactional
public abstract class AbstractTest {
    public static final int START_SEQ = 1;

    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable test) {
        Assertions.assertThrows(rootExceptionClass, () -> {
            try {
                test.run();
            } catch (Throwable e) {
                throw getRootCause(e);
            }
        });
    }

    protected <T extends Throwable> void validateCause(Class<T> causeClass, String causeExceptionMessage, Runnable test) {
        Assertions.assertThrows(causeClass, () -> {
            try {
                test.run();
            } catch (Throwable t) {
                throw getCauseException(causeClass, causeExceptionMessage, t);
            }
        });
    }
}


