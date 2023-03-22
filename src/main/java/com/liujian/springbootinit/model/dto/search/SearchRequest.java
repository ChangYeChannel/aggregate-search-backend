package com.liujian.springbootinit.model.dto.search;

import com.liujian.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索关键字
     */
    private String searchText;
    /**
     * 搜索哪一类型的数据
     */
    private String type;
    private static final long serialVersionUID = 1L;
}
