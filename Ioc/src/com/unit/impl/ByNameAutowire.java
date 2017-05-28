package com.unit.impl;

import com.unit.Autowire;

public class ByNameAutowire implements Autowire {
    private String value;
    
    
	public ByNameAutowire(String value) {
		this.value = value;
	}


	@Override
	public String getValue() {
		return value;
		
	}

}
