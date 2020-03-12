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
//	购物车中所有商品集合
	private Map<String, CartItem> cartitem = new HashMap<String, CartItem>();
//	购物车中商品总计
	private double total;
}
