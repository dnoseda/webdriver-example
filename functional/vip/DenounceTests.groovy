package vip

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import api.login.LoginMockService;

import vip.test.utils.WebdriverUtils;
import vip.testpages.ReviewPageFragment;
import vip.testpages.VipPage;

import grails.plugins.i18n_gettext.T9nService;
import grails.test.GrailsUnitTestCase;
import groovy.lang.Closure;
import groovy.lang.MetaClass;

class DenounceTests extends GrailsUnitTestCase {

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
	
	void doFormTest(){
		// given: no estoy logueado
		(new LoginMockService()).setLoggedFalse()
				
		// given: voy a un item activo
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		
		// tiene denunciar
		WebElement denounceButton = WebdriverUtils.waitTo(browser, "//a[@id='denounce']")
		assert denounceButton
		
		// and: click denunciar
		denounceButton.click()
		
		// then: muestra denunciar
		WebElement denounceModal = WebdriverUtils.waitTo (browser, "//h2[@class='typo']")
		assert denounceModal
		assertEquals "actual:${denounceModal.getText()}, real:'Denunciar'",denounceModal.getText(), "Denunciar"
		
		

		WebElement denounceLogged = WebdriverUtils.waitTo (browser, "//form[@id='denounceForm']//input[@id='loginButton']")
		assert denounceLogged, "no existe boton para denunciar"
		
		/** /
		// when: voto vacio
	
		denounceLogged.click()
		
		/** /
		// then: cartel voto vacio
		WebElement error = WebdriverUtils.waitTo (browser, "//p[@class='errorDenounce']")
		assert error, "no muestra error"
		assert error.getText().contains("Elige una opci"), "texto error '${error.getText()}'"
		/**/
		
		// when: selecciono una opción
		WebElement reason = WebdriverUtils.waitTo (browser, "//form[@id='denounceForm']//li[1]//input")
		// when: elijo una opción
		reason.click()
		
		// when: captcha incorrecto
		//(new JcaptchaMockService()).setCaptchaInvalid()
		// when: denuncio
		denounceLogged.click()
		// then: cartel captcha incorrecto
		/** /
		WebElement error = WebdriverUtils.waitTo(browser, "//form[@id='denounceForm']//p[@class='errorCaptcha']")
		assert error, "error no existe"
		assert error.getText().contains("Escribe qué ves en la imagen."), "texto error '${error.getText()}'"
		/**/
		// when: completo captcha
		//(new JcaptchaMockService()).setCaptchaValid()
	}
	void testNotLogged(){
		doFormTest()
		// when: not loged
		(new LoginMockService()).setLoggedFalse()
		// then: denuncio
		WebElement denounceLogged = WebdriverUtils.waitTo (browser, "//form[@id='denounceForm']//input[@id='loginButton']")
		denounceLogged.click()
		// then: redirect login
		assert WebdriverUtils.isLoginPage(browser)	 
	}
	
	void testLogged(){
		doFormTest()
		// when: is logged
		(new LoginMockService()).setLoggedTrue()
		// then: denuncio
		WebElement denounceLogged = WebdriverUtils.waitTo (browser, "//form[@id='denounceForm']//input[@id='loginButton']")
		denounceLogged.click()
		// then: denuncia exitosa
		WebElement success = WebdriverUtils.waitTo(browser,"//div[@id='denounceModalWindowContent']/span")
		assert success.getText().contains("Gracias por tu denuncia.")
	}
	

}
