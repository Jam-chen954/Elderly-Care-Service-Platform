package com.elderlycare.platform.common;

import com.elderlycare.platform.common.api.PageQuery;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PageQueryTest {
    @Test
    void defaultAndMaximumPageSizeAreValidated() {
        assertThat(new PageQuery(null, null)).isEqualTo(new PageQuery(1, 20));
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertThat(validator.validate(new PageQuery(0, 101))).hasSize(2);
            assertThat(validator.validate(new PageQuery(1, 100))).isEmpty();
        }
        assertThat(new PageQuery(Integer.MAX_VALUE, 100).offset()).isEqualTo(214748364600L);
    }
}
