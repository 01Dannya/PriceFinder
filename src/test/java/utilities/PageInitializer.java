package utilities;

import pages.ZillowHomePage;

public class PageInitializer extends BaseClass {
	
	public static ZillowHomePage hp;
	
	public static void initialize() {
		
		hp = new ZillowHomePage();
	}

}
