package vip

import grails.test.GrailsUnitTestCase;
import grails.util.GrailsUtil;
import groovy.lang.Closure;

import meli.component.calification.Calification;
import meli.vip.Item;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import api.login.LoginMockService;

import vip.test.utils.WebdriverUtils;
import vip.testpages.CalificationsPageFragment;
import vip.testpages.ReviewPageFragment;
import vip.testpages.VipPage;


class CalificationTests  extends GrailsUnitTestCase {
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
		assert page.hasTab("califications")
		def califications = page.getTab("califications")
		assert califications instanceof CalificationsPageFragment, "${califications.getClass()}"
		println califications.getCalificationsQuantity()
		if(	WebdriverUtils.getTestPhase(getClass()) != "unit"){
			int realcalif = Calification.countByItemId("MLA1")
			int actualcalif = califications.getCalificationsQuantity()
			assertEquals ("real: $realcalif, actual:$actualcalif" ,realcalif, actualcalif)
		}
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
