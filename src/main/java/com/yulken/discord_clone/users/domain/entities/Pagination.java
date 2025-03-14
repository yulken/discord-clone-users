package com.yulken.discord_clone.users.domain.entities;

import com.yulken.discord_clone.users.domain.enums.OrderByEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    private OrderByEnum orderBy = OrderByEnum.DESC;
    private String sort;
    private int pageSize = 25;
    private int pageCount = 0;
}