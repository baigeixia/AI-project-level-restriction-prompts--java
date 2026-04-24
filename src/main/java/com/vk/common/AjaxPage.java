package com.vastknowledge.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AjaxPage&lt;T&gt; implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long total;

    private List&lt;T&gt; list;

    private Integer pageNum;

    private Integer pageSize;

    public static &lt;T&gt; AjaxPage&lt;T&gt; of(Long total, List&lt;T&gt; list, Integer pageNum, Integer pageSize) {
        AjaxPage&lt;T&gt; page = new AjaxPage&lt;&gt;();
        page.setTotal(total);
        page.setList(list);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }
}
