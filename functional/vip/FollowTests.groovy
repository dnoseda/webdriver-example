package vip

import vip.test.utils.WebdriverUtils;


import meli.component.catalog.CatalogProductAttribute;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.groovy.grails.plugins.webdriver.WebDriverTestCase;
import meli.component.opinions.*
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import api.login.LoginMockService;
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.*

// para remote
import org.openqa.selenium.remote.*
import org.openqa.selenium.SeleneseCommandExecutor

import clover.org.apache.log4j.Level;
import clover.org.apache.log4j.Logger;
import vip.testpages.*;

import meli.vip.Item;
 

import grails.test.*
import grails.test.GrailsUnitTestCase;

class FollowTests extends GrailsUnitTestCase {
	Logger log = Logger.getLogger(getClass())
	WebDriver browser
    protected void setUp() {
        super.setUp()
		log.setLevel(Level.ALL)
		browser = WebdriverUtils.getDriver()
		(new LoginMockService()).setLoggedFalse()
    }
	
    protected void tearDown() {
        super.tearDown()
		browser.close()
		(new LoginMockService()).setLoggedFalse()
    }
	
	
	void testLogged(){
		(new LoginMockService()).setLoggedTrue()
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		def follow = page.clickLink("followPublication")
		assert follow instanceof FollowPage, "${follow.getClass()}"
		follow.clickReturn()
	}

	void testNotLogged(){
		(new LoginMockService()).setLoggedFalse()
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		def follow = page.clickLink("followPublication")
		
		// redirige?
		assert WebdriverUtils.isLoginPage(browser)
		
		//Simulo login
		(new LoginMockService()).setLoggedTrue()

		//voy a return login
		browser.get("${WebdriverUtils.getMyUrl()}/followPublication/returnLogin?token=MLA1")
		
		follow.clickReturn()
	}
	
//	void testQuestionLogged(){
//		(new LoginMockService()).setLoggedTrue()
//		VipPage page = new VipPage()
//		page.setBrowser(browser)
//		page.goTo("MLA1")
//		def follow = page.clickLink("followPublication")
//		assert follow instanceof FollowPage, "${follow.getClass()}"
//		page.goTo("MLA1","#!questions")
//		page.doQuestion()
//		int cant = browser.findElements(By.xpath("//*[@class='follow']")).size()
//		assert cant == 0, "cant: ${cant}"
//		}
	
		
	def waitToClosure(WebDriver browser, Closure clo, int to=3){
		for(i in 0..to){
			try{
				return clo(browser)
			}catch(Exception e){
				Thread.sleep(1000)
			}
		}
		clo(browser)
	}
	def waitTo(WebDriver browser, String exp, int to=3){
		waitToClosure(browser, { bro -> browser.findElement(By.xpath(exp))} ,to)
	}
	
}
