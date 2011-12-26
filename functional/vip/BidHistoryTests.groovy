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
import vip.testpages.HistoryTendersFragment;
import vip.testpages.ReviewPageFragment;
import vip.testpages.VipPage;


class BidHistoryTests  extends GrailsUnitTestCase {
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
	void testShow(){
		
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA4")
		assert page.hasTab("historyTenders")
		def historyTenders = page.getTab("historyTenders")
		assert historyTenders instanceof HistoryTendersFragment, "${historyTenders.getClass()}"
		// TODO: falta checkeo de base vs la cantidad que se muestra
	}
}
