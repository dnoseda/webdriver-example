package vip.testpages

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class CalificationsPageFragment extends PageFragment {
	int getCalificationsQuantity(){
		def elements = browser.findElements(By.xpath("//ol[@class='calificationsList']//li"))?.findAll {
			println "encontro '${it.getText()}'" 
			return it.getText().length() > 0
		}
		def i =0
		elements?.each { WebElement element ->
				println "elemento: $i:"			
				println "text '${element.getText()}'"
				println "name '${element.getTagName()}'"				
			}
		
		return elements?.size()
	}
}
