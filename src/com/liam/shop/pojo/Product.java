package com.liam.shop.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
//	pid	varchar(32)	NO	PRI
//	pname	varchar(50)	YES	
//	market_price	double	YES	
//	shop_price	double	YES	
//	pimage	varchar(200)	YES	
//	pdate	date	YES	
//	is_hot	int(11)	YES	
//	pdesc	varchar(255)	YES	
//	pflag	int(11)	YES	
//	cid	varchar(32)	YES	MUL
	
	private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private	String pimage;
    private Date pdate;
    private int is_hot;
    private String pdesc;
    private int pflag;
    private String cid;
    
}
