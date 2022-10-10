package com.limengxiang.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {
    public static  <V> V copyBean(Object source,Class<V> clazz){
        V vo = null;
        try {
             vo = clazz.newInstance();
             BeanUtils.copyProperties(source,vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    public static <T,V> List<V> copyBeanList(List<T> list,Class<V> clazz){
        return list.stream().map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
