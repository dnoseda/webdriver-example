package vip.testpages

import org.openqa.selenium.*

class FollowPage extends Page {
	
	void clickReturn(){
		browser.findElement(By.xpath("//*[@class='btn secondary']")).click()

	}
	

	
}
