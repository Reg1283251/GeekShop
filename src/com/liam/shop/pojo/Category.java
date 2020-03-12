package com.liam.shop.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
	
//	cid	varchar(32)	NO	PRI
//	cname	varchar(20)	YES	
	
	private String cid;
	private String cname;

}
