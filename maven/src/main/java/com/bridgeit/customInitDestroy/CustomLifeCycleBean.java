package com.bridgeit.customInitDestroy;

public class CustomLifeCycleBean {
	private String name;

	public CustomLifeCycleBean() {
		System.out.println("constructor of custom life cycle");
	}
	public void customDestroy() throws Exception {
        
        System.out.println("custom destroy method of  bean is called !! ");
    }
 
    public void customInit() throws Exception {
        System.out.println("custom Init  method of  bean is called !! ");
    }
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
