package vip

import grails.test.GrailsUnitTestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import api.login.LoginMockService;

import vip.test.utils.WebdriverUtils;
import vip.testpages.VipPage;


class PaymentMethodTests  extends GrailsUnitTestCase {
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
		
		// click payment methods
		WebElement paymethodLink = WebdriverUtils.waitTo(browser, "//a[@id='PMethods']")
		assert paymethodLink		
		paymethodLink.click()
		// TODO: select visa
		// TODO: select a banc
		// TODO: select a times to pay
		// buy... TODO: si dependo de modal local ,mientras tanto
		
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
