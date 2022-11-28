

import org.testng.TestNG;

public class RunnerClass {

	static TestNG testngobj;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			testngobj = new TestNG();
			testngobj.setTestClasses(new Class[] {TestCase1.class,TestCase2.class,TestCase3.class});
			testngobj.addListener(null);
			testngobj.run();
		}
	

}
