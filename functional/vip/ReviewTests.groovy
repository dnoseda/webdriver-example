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



import meli.vip.Item;
 

import grails.test.*
import grails.test.GrailsUnitTestCase;
import vip.testpages.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


class ReviewTests extends GrailsUnitTestCase {
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
		// given: estoy logueado
		(new LoginMockService()).setLoggedTrue()
		
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		assert page.hasTab("opinions")
		def opinions = page.getTab("opinions")
		assert opinions instanceof ReviewPageFragment, "${opinions.getClass()}"
		int cant = opinions.getReviewsQuantity()
		assert cant > 0, "cant: ${cant}"
		int reviewToVote = RandomUtils.nextInt(cant)
		assert reviewToVote in 0..cant 
		opinions.voteYes(reviewToVote)
		//assert opinions.hasVoted() // TODO
	}
    void testNotLogged(){
		int quantity = RandomUtils.nextInt(10)
		// given: tiene opiniones
//		List opinions = generateOpinions(item.catalogProductId,quantity)
		
		// given: no estoy logueado
		(new LoginMockService()).setLoggedFalse()
		
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		assert page.hasTab("opinions")
		def opinions = page.getTab("opinions")
		assert opinions instanceof ReviewPageFragment, "${opinions.getClass()}"
		int cant = opinions.getReviewsQuantity()
		assert cant > 0, "cant: ${cant}"
		int reviewToVote = RandomUtils.nextInt(cant)
		assert reviewToVote in 0..cant 
		opinions.voteYes(reviewToVote)
		
		assert waitToClosure(browser, {bro -> 
				if(!browser.getCurrentUrl().toUpperCase().contains("LOGIN") ){
					throw new Exception("Not yet; current ${browser.getCurrentUrl()}")
				}
				return browser.getCurrentUrl().toUpperCase().contains("LOGIN")
			},20)
	}
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
