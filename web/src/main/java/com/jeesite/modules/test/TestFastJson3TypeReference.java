package com.jeesite.modules.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 泛型解析
 */
public class TestFastJson3TypeReference {

    public static void main(String[] args) {
        FJ3<FJ31> o1 = new FJ3<FJ31>(100, "N1001", new FJ31("软件园"));
        String json1 = JSON.toJSONString(o1);
        System.out.println(json1);
        FJ3<FJ31> o11 = JSON.parseObject(json1, new TypeReference<FJ3<FJ31>>() {
        });
        System.out.println(o11.getObj().getAddress());

        FJ3<FJ32> o2 = new FJ3<FJ32>(100, "N1001", new FJ32("123456@qq.com"));
        String json2 = JSON.toJSONString(o2);
        System.out.println(json2);
        FJ3<FJ32> o21 = JSON.parseObject(json2, new TypeReference<FJ3<FJ32>>() {
        });
        System.out.println(o21.getObj().getEmail());
    }
}

class FJ3<T> {

    private Integer id;
    private String name;
    private T obj;

    public FJ3(Integer id, String name, T obj) {
        super();
        this.id = id;
        this.name = name;
        this.obj = obj;
    }

    public FJ3() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}

class FJ31 {
    private String address;

    public FJ31(String address) {
        super();
        this.address = address;
    }

    public FJ31() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

class FJ32 {
    private String email;

    public FJ32(String email) {
        super();
        this.email = email;
    }

    public FJ32() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
