package com.yf.spring.ioc.complext;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ComplexBean {
    //包含特殊字符，如：&(&amp;)、>(&gt)、<(&lt)、"(&quot)、'(&apos)或者使用![CDATA[红旗轿车&ZF002]]方式
    private String name;
    //null值注入
    private Set<String> set;
    private Map<String,String> map;
    private List<String> list;
    private Properties properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ComplexBean{" +
                "name='" + name + '\'' +
                ", set=" + set +
                ", map=" + map +
                ", list=" + list +
                ", properties=" + properties +
                '}';
    }
}
