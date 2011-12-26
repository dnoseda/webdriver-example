package meli.component.paymentmethod

class ModalPaymentMethodTests extends functionaltestplugin.FunctionalTestCase {

	void testSomething() {
//		post('http://localhost:8080/vip/modalPaymentMethods/showQuotes?itemId=MLA1&creditCard=visa&amount=12&issuerOption=-1')
		post('http://localhost:8080/vip/modalPaymentMethods/showQuotes'){
			itemId="MLA1"
			creditCard="visa"
			amount="12"
			issuerOption="-1"
		}
//		assertStatus 200
//		assertContentContains "Medios de pago"
	}
}
