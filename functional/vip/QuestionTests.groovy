package vip

import grails.test.GrailsUnitTestCase;
import grails.util.GrailsUtil;
import groovy.lang.Closure;

import meli.component.calification.Calification;
import meli.vip.CookieManagerMockService;
import meli.vip.Item;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import api.login.LoginMockService;

import vip.test.utils.WebdriverUtils;
import vip.testpages.CalificationsPageFragment;
import vip.testpages.QuestionModalPage;
import vip.testpages.ReviewPageFragment;
import vip.testpages.VipPage;


class QuestionTests  extends GrailsUnitTestCase {
	
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
	
	//Preguntar sin estar logueado
	void testNotLogged(){
		// given: no estoy logueado
		(new LoginMockService()).setLoggedFalse()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// chequeos al modal
		assert askModal.checkModal()
		// pregunto
		assert askModal.doQuestion("Pregunta webdriver1")
		// redirige?
		assert WebdriverUtils.isLoginPage(browser)
	}
	
	//Preguntar logueado
	void testLogged(){
		// given: no estoy logueado
		(new LoginMockService()).setLoggedTrue()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// chequeos al modal
		assert askModal.checkModal()
		// pregunto
		assert askModal.doQuestion("Pregunta webdriver1")
		// chequeo cartel de pregunta OK
		assert askModal.checkQuestionOk()
		page.goTo("MLA1")
		// chequeo si la pregunta se inserto
		assert page.findQuestion("Pregunta webdriver1")
		// click Preguntar al final de las preguntas
		askModal = page.openModal("btnQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// pregunto
		assert askModal.doQuestion("Pregunta webdriver2")
		// chequeo cartel de pregunta OK
		assert askModal.checkQuestionOk()
		page.goTo("MLA1")
		// chequeo si la pregunta se inserto
		assert page.findQuestion("Pregunta webdriver2")
	}
	
	//Preguntar con usuario bloqueado sin estar logueado
	void testBlockedNotLogged(){
		// given: no estoy logueado
		(new LoginMockService()).setLoggedFalse()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// pregunto
		assert askModal.doQuestion("not_accepting_questions")
		// redirige?
		assert WebdriverUtils.isLoginPage(browser)
		
		//Simulo login
		String encodeParams= "?itemId=MLA1&cookieName=TEST"
		Map value= [questionText: "not_accepting_questions", enLaMira: false]
		(new LoginMockService()).setLoggedTrue()
		(new CookieManagerMockService()).setMockCookie(value)
		
		//voy a return login
		browser.get("${WebdriverUtils.getMyUrl()}/question/returnLogin${encodeParams}")
		
		//chequeo texto
		assert askModal.checkError("not_accepting_questions")
		
		// recargo la pagina
		page.goTo("MLA1")
		
		// chequeo si la pregunta no se inserto
		assert !page.findQuestion("not_accepting_questions")
	}
	
	//Preguntar con usuario bloqueado logueado
	void testBlocked(){
		
		// given: no estoy logueado
		(new LoginMockService()).setLoggedTrue()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// pregunto
		assert askModal.doQuestion("not_accepting_questions")
		// chequeo texto
		assert askModal.checkError("not_accepting_questions")
		// cierro el modal
		assert askModal.close()
		// recargo la pagina
		page.goTo("MLA1")
		// chequeo si la pregunta no se inserto
		assert !page.findQuestion("not_accepting_questions")
	} 
	
	//Preguntar con resultado error
	void testError() {(new LoginMockService()).setLoggedTrue()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA1")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// pregunto
		assert askModal.doQuestion("error")
		assert askModal.checkError("error")
		assert askModal.close()
		// recargo la pagina
		page.goTo("MLA1")
		// chequeo si la pregunta no se inserto
		assert !page.findQuestion("error")
	}
	
	// Preguntar con el vendedor del item
	void testSellerQuestion() {
		// given: no estoy logueado
		(new LoginMockService()).setLoggedTrue()
		// abro vip
		VipPage page = new VipPage()
		page.setBrowser(browser)
		page.goTo("MLA91621272")
		// click Preguntar
		def askModal = page.openModal("makeQuest")
		// modal de preguntar
		assert askModal instanceof QuestionModalPage, "${askModal.getClass()}"
		// pregunto
		assert askModal.doQuestion("asker_eq_seller")
		assert askModal.checkError("asker_eq_seller")
		assert askModal.close()
		// recargo la pagina
		page.goTo("MLA1")
		// chequeo si la pregunta no se inserto
		assert !page.findQuestion("error")
	}
}
