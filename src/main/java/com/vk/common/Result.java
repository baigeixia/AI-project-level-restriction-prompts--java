package com.vastknowledge.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result&lt;T&gt; implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private T data;

    public static &lt;T&gt; Result&lt;T&gt; success() {
        return success(null);
    }

    public static &lt;T&gt; Result&lt;T&gt; success(T data) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static &lt;T&gt; Result&lt;T&gt; error(String message) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static &lt;T&gt; Result&lt;T&gt; error(Integer code, String message) {
        Result&lt;T&gt; result = new Result&lt;&gt;();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
