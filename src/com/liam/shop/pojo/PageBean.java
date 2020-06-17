package com.liam.shop.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
public class PageBean<E> {

    // 当前第几页
    private int currentPage;
    // 每页数量
    private int currentCount;
    // 总页数
    private int totalPage;
    // 总条目数
    private int totalCount;
    // 对象的集合
    private List<E> list;

    // 分页开始
    int start;
    int end;


}
