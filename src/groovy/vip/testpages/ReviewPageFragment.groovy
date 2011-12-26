package vip.testpages

import org.openqa.selenium.*

class ReviewPageFragment extends PageFragment {
	void voteYes(int reviewNumber){
		browser.executeScript("\$(\".review\").find(\"input[name='voteYes']\")[${reviewNumber}].click();")
	}
	int getReviewsQuantity(){
		browser.findElements(By.xpath("//*[@class='review']")).size()
	}
	boolean hasVoted(){
		waitTo(browser, "//span[@class='statusOk']",10)
	}
}
