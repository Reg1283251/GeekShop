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
	
	// ��ǰ�ڼ�ҳ 
	private int currentPage;
	// ÿҳ����
	private int currentCount;
	// ��ҳ��
	private int totalPage;
	// ����Ŀ�� 
	private int totalCount;
	// ����ļ���
	private List<E> list;
	
	// ��ҳ��ʼ
	int start;
	int end;
	

}
