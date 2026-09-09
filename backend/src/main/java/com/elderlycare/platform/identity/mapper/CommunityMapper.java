package com.elderlycare.platform.identity.mapper;

import com.elderlycare.platform.identity.domain.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityMapper {
    Community findActiveById(@Param("id") long id);
}
