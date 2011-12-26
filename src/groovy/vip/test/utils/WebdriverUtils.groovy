package vip.test.utils


import org.openqa.selenium.firefox.FirefoxProfile;
import org.ho.yaml.Yaml;


import grails.util.GrailsUtil;
import groovy.lang.Closure;





// para remote
import org.openqa.selenium.remote.*
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.*
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.remote.DesiredCapabilities

import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.remote.*;


class WebdriverUtils {
	
	public static WebDriver getDriver(){
		// return new FirefoxDriver()
		/** /
		Capabilities capabilities = new DesiredCapabilities()
		capabilities.setBrowserName("firefox");
		CommandExecutor executor = new SeleneseCommandExecutor("http://localhost:4444/".toURL(), "http://www.google.com/".toURL(), capabilities);
		return new RemoteWebDriver(executor, capabilities);
		/**/
		
		def config = Yaml.loadType (new File("configFile.test.yaml"), HashMap.class)
		
		if(config["webdriver.useRemote"] && GrailsUtil.environment in config["webdriver.useRemote"] ){
			String remoteIp = config["webdriver.remote"]
			
			return new RemoteWebDriver("http://${remoteIp}:4444/wd/hub".toURL(), DesiredCapabilities.internetExplorer());
		}else{
			/*FirefoxProfile myProfile = new FirefoxProfile();
			myProfile.setPreference("network.proxy.http","10.12.0.251");
			myProfile.setPreference("network.proxy.http_port",3129);
			myProfile.setPreference("network.proxy.no_proxies_on","10.12.0.84, vip.mercadolibre.com.ar, db1.mongo.gz, 10.100.34.144, 10.10.42.47,git.ml.com, localhost, 127.0.0.1, *.gz, .gz,10.12.0.93,10.12.0.102");
			myProfile.setPreference("network.proxy.type",1);
			return new FirefoxDriver(myProfile)
			*/
			ProfilesIni allProfiles = new ProfilesIni();
			FirefoxProfile myProfile = allProfiles.getProfile("default");
			return new FirefoxDriver(myProfile)
		}
	}
	
	static String baseUrl = getBaseUrl()
	private static String getBaseUrl(){
		def output = "ifconfig".execute().text
		return (output =~ /\d+\.\d+\.\d+\.\d+/)[0]
	}
	
	public static String getMyUrl(){
		//return "http://${baseUrl}:8080"
		return "http://vipdesa.mercadolibre.com.ar:8080"
	}
	
	public static Selenium getSelelium(){
		//WebDriver driver = new FirefoxDriver();

		// A "base url", used by selenium to resolve relative URLs
		// String baseUrl = "http://www.google.com";
		
		// Create the Selenium implementation
		Selenium selenium = new WebDriverBackedSelenium(getDriver(), getMyUrl());
		
		// Perform actions with selenium
//		selenium.open("http://www.google.com");
//		selenium.type("name=q", "cheese");
//		selenium.click("name=btnG");
		return selenium
	}
	public static String getTestPhase(Class type){
		String path = type.getProtectionDomain().getCodeSource().getLocation().getPath()
		String aux = path.split("/").last()
		return aux
	}
	public static boolean isLoginPage(WebDriver browser){
		waitToClosure(browser, {bro ->
			if(!browser.getCurrentUrl().toUpperCase().contains("LOGIN") ){
				throw new Exception("Not yet; current ${browser.getCurrentUrl()}")
			}
			return browser.getCurrentUrl().toUpperCase().contains("LOGIN")
		},20)
	}
	
	def static waitToClosure(WebDriver browser, Closure clo, int to=3){
		for(i in 0..to){
			try{
				return clo(browser)
			}catch(Exception e){
				Thread.sleep(1000)
			}
		}
		clo(browser)
	}
	def static waitTo(WebDriver browser, String exp, int to=3){
		waitToClosure(browser, { bro -> browser.findElement(By.xpath(exp))} ,to)
	}
	
	def static waitToElements(WebDriver browser, String exp, int to=3){
		waitToClosure(browser, { bro ->
					def aux = browser.findElements(By.xpath(exp)) 
					if(aux?.isEmpty()){
						throw Exception("aux $aux is emtpy")
					}
				} ,to)
	}
}
