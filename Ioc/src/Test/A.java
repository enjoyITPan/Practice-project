package Test;

public class A {
	
	private String school;

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public A(String school) {
		this.school = school;
	}
    
	public A() {
		super();
	}
	
	@Override
	public String toString() {
		return "A [school=" + school + "]";
	}

}
