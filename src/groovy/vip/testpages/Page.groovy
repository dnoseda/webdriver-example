package vip.testpages

import vip.test.utils.WebdriverUtils;

import org.openqa.selenium.*

class Page {
	WebDriver browser
	
	def waitToClosure(WebDriver browser, Closure clo, int to=3){
		for(i in 0..to){
			try{
				def obj = clo(browser)
				if(obj){
					return obj 
				}
			}finally{
				Thread.sleep(1000)
			}
		}
		try{
			return clo(browser)
		}catch(Exception e){
			throw new Exception("attempt ${to}",e)
		}
	}
	
	def waitTo(WebDriver browser, String exp, int to=3){
		WebdriverUtils.waitTo (browser, exp, to)
	}
}
