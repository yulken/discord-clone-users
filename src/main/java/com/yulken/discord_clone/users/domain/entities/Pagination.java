package com.yulken.discord_clone.users.domain.entities;

import com.yulken.discord_clone.users.domain.enums.SortByEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    private String orderBy = "id";
    private SortByEnum sort = SortByEnum.DESC;
    private int pageSize = 25;
    private int pageCount = 0;
}