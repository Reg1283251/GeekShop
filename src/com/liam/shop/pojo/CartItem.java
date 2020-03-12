package com.liam.shop.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartItem {
	
	/**
	 * ��Ʒ��Ϣ
	 */
	private Product product;
	/**
	 * ��������
	 */
	private int buyNum;
	/**
	 * �ܼ�
	 */
	private double subTotal;
}
