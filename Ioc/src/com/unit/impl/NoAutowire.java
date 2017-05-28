package com.unit.impl;

import com.unit.Autowire;

public class NoAutowire implements Autowire {
	 private String value;
	    
	    
		public NoAutowire(String value) {
			this.value = value;
		}


		@Override
		public String getValue() {
			return value;
			
		}

}
