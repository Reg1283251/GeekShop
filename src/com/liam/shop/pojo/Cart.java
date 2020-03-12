package com.liam.shop.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Cart {
//	���ﳵ��������Ʒ����
	private Map<String, CartItem> cartitem = new HashMap<String, CartItem>();
//	���ﳵ����Ʒ�ܼ�
	private double total;
}
