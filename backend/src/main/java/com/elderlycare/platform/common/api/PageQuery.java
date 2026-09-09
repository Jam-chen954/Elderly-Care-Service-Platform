package com.elderlycare.platform.common.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/** 统一分页边界；Controller 使用 @Valid 绑定，禁止无上限查询。 */
public record PageQuery(@Min(1) Integer page, @Min(1) @Max(100) Integer pageSize) {
    public PageQuery {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
    }

    public long offset() {
        return ((long) page - 1) * pageSize;
    }
}
