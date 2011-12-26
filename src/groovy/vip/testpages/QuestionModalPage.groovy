package vip.testpages


import org.openqa.selenium.*

class QuestionModalPage extends ModalPage {
	
	/**
	 * Chequea que el modal este levantado, el contador en 1000, check de follow
	 * y validacion en caso de pregunta vacia
	 */
	boolean checkModal() {
		
		// Modal levantado?
		WebElement askModal = waitTo(browser,"//h2[@class='typo']",10)
		assert askModal
		
		// Contador en 1000?
		WebElement countChars = waitTo(browser,"//span[@id='display']",10)
		assert countChars.getText().contains("1000"), "cantidad de caracteres ${countChars.getText()}"
		
		// check de seguir item?
		WebElement followCheck = waitTo(browser,"//input[@id='enLaMira']",10)
		assert followCheck
		
		// pregunta vacia
		WebElement sendAskButton = waitTo(browser,"//input[@id='saveQuest']",10)
		assert sendAskButton
		sendAskButton.click()
		
		// muestra error?
		WebElement errorMsg = waitTo(browser,"//p[@class='validationTip downTip']",10)
		assert errorMsg
		
		return true
	}
	
	/**
	 * Completa el texto de una pregunta y la envia
	 */
	boolean doQuestion(String question){
		WebElement askModal = waitTo(browser,"//h2[@class='typo']",10)
		assert askModal
		
		WebElement askText =  waitTo(browser, "//textarea[@id='questionText']",10)
		askText.sendKeys(question)
		
		WebElement sendAskButton = waitTo(browser, "//input[@id='saveQuest']",10)
		sendAskButton.click()
		
		return true
	}
	
	
	/**
	 * Comprueba que se obtenga el ok al enviar pregunta
	 */
	boolean checkQuestionOk(){
		waitTo(browser, "//span[@class='statusOk']",10)
		return true
	}
	
	boolean checkError(String error) {
		WebElement message = waitTo(browser,"//div[@class='messagesVip']//h2",10) 
		if (error == "not_accepting_questions") {
			return ("El vendedor no acepta preguntas" == message.getText())
		}else if (error == "error") {
			return ("Error al preguntar" == message.getText())
		}else if (error == "asker_eq_seller"){
			
			return ("Esta publicación es tuya" == message.getText())
		}
		return false
	}
	
	boolean close(){
		WebElement button = waitTo(browser,"//p[@class='btn close']",10)
		assert button
		button.click()
		return true
	}
}
