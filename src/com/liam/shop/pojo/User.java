package com.liam.shop.pojo;

import java.util.Date;

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
@Data
@ToString
public class User {
//	  `uid` varchar(32) NOT NULL,
//	  `username` varchar(20) DEFAULT NULL,
//	  `password` varchar(20) DEFAULT NULL,
//	  `name` varchar(20) DEFAULT NULL,
//	  `email` varchar(30) DEFAULT NULL,
//	  `telephone` varchar(20) DEFAULT NULL,
//	  `birthday` date DEFAULT NULL,
//	  `sex` varchar(10) DEFAULT NULL,
//	  `state` int(11) DEFAULT NULL,
//	  `code` varchar(64) DEFAULT NULL,

	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private Date birthday;
	private String sex;
	private int state;
	private String code;


}
