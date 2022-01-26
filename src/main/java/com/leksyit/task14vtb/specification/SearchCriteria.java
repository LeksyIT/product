package com.leksyit.task14vtb.specification;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;

}
