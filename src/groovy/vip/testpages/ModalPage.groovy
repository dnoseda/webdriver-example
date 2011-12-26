package vip.testpages

import org.openqa.selenium.*

class ModalPage extends Page{
	
	boolean hasClose(){
		//TODO
		waitTo(browser, "//span[@class='statusOk']",10)
	}
	
	void clickSubmit()
	{
		
	}
}
