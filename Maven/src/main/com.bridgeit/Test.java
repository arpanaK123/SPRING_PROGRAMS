
public class Test {

	public static void main(String[] args) {

		  Resource resource=new ClassPathResource("ApplicationContext.xml");  
		    BeanFactory factory=new XmlBeanFactory(resource);  
		      
		    Student student=(Student)factory.getBean("studentbean");  
		    student.displayInfo();  
	}

}
