package Test;

public class B{
	private int age;
	private String name;
	private A aa;

	public B(int age, String name,A aa) {
		super();
		this.age = age;
		this.name = name;
		this.aa=aa;
	}
	
	public B() {
		super();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public A getAa() {
		return aa;
	}

	public void setAa(A aa) {
		this.aa = aa;
	}

	@Override
	public String toString() {
		return "B [age=" + age + ", name=" + name + ", a=" + aa + "]";
	}


}
