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
	 * 商品信息
	 */
	private Product product;
	/**
	 * 订购数量
	 */
	private int buyNum;
	/**
	 * 总价
	 */
	private double subTotal;
}
