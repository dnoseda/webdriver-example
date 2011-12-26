package vip.testpages

import vip.test.utils.WebdriverUtils;

import org.openqa.selenium.*

class VipPage extends Page {
	void goTo(String itemId){
		if(itemId == "MLA1"){			
			browser.get("${WebdriverUtils.getMyUrl()}/MLA-1-Camara-Digital-Olympus-6-Gb-Funda-Gtia_JM")
			waitTo(browser,"//body",10)
			waitTo(browser,"//div[@id='communityTabsContainer']",10)
		}else if(itemId == "MLA4"){
			browser.get("${WebdriverUtils.getMyUrl()}/MLA-4-Camara-Digital-Olympus-6-Gb-Funda-Gtia_JM")
			waitTo(browser,"//body",10)
			waitTo(browser,"//div[@id='communityTabsContainer']",10)
		}else if(itemId == "MLA91621272"){
			browser.get("${WebdriverUtils.getMyUrl()}/MLA-91621272-Camara-Digital-Olympus-6-Gb-Funda-Gtia_JM")
			waitTo(browser,"//body",10)
			waitTo(browser,"//div[@id='communityTabsContainer']",10)
		}
	}
	
	void goTo(String itemId, String tab){
		if(itemId == "MLA1"){
			browser.get("${WebdriverUtils.getMyUrl()}/MLA-1-Camara-Digital-Olympus-6-Gb-Funda-Gtia_JM${tab}")
			waitTo(browser,"//body",10)
			waitTo(browser,"//div[@id='communityTabsContainer']")
		}
	}
	
	boolean hasTab(String tabName){
		if(waitTo(browser,"//*[@name='${tabName}']",10)){
			return true
		}
		return false
	}
	
	PageFragment getTab(String tabName){
		waitTo(browser,"//*[@name='${tabName}']").click()
		PageFragment page
		if(tabName=="opinions"){
			waitToClosure(browser,{bro -> bro.findElements(By.xpath("//*[@class='review']"))},10)
			page = new ReviewPageFragment()
		}else if(tabName =="califications"){
			waitToClosure(browser,{bro -> bro.findElements(By.xpath("//ol[@class='califications']"))},10)
			page =new CalificationsPageFragment()
		}else if(tabName == "historyTenders"){
			waitToClosure(browser,{bro -> bro.findElements(By.xpath("//ol[@class='historyTenders']"))},10)
			page =new HistoryTendersFragment()
		}
		page.setBrowser(browser)
		return page
	}
	
	FollowPage clickLink(String linkId){
		waitTo(browser,"//*[@id='${linkId}']").click()
		waitToClosure(browser,{bro -> bro.findElements(By.xpath("//*[@class='messagesVip']"))},10)
		FollowPage page
		if(linkId=="followPublication"){
			page = new FollowPage()
		}
		
		page.setBrowser(browser)
		return page
	}
	
	ModalPage openModal(String id)
	{
		WebElement button = waitTo(browser, "//*[@id='${id}']")
		assert button
		button.click()
		ModalPage page
		if (id in ["makeQuest","btnQuest"]){
			page = new QuestionModalPage()  
		}
		page.setBrowser(browser)
		return page
	}
	
	boolean findQuestion(String questionToFind){
		WebElement questionsDiv = waitTo(browser,"//div[@class='contentTabs questions']",10)
		assert questionsDiv
		List<WebElement> allQuestions = browser.findElements(By.xpath("//*[@class='question']//span"))
		for (WebElement question : allQuestions) {
			if (question.getText() == questionToFind)
				return true
		}
		return false
	}
	
	boolean checkFooter(){
		
	}

	
}
