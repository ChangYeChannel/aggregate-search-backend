package com.liujian.springbootinit.model.dto.search;

import com.liujian.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    private String searchText;
    private static final long serialVersionUID = 1L;
}
