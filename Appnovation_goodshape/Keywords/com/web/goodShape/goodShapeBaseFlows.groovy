package com.web.goodShape

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.apache.commons.lang.RandomStringUtils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

import internal.GlobalVariable
import sun.awt.datatransfer.ClipboardTransferable.DataFactory

public class goodShapeBaseFlows {

	TestObject pageLabel = findTestObject('Object Repository/goodShape_OR/genObj_link',['desc':'pageLabel','text':'Good Shape'])
	TestObject afterSubmitMessage = new TestObject("afterSubmitMessage").addProperty("xpath",ConditionType.EQUALS,'//span[contains(text(),"Thanks for submitting!")]')
	
	TestData testdata

	public goodShapeBaseFlows() {
		if(RunConfiguration.getExecutionProfile()=='INT') {
			this.testdata=findTestData("Data Files/goodShape_INT")
		}
		else {
			this.testdata=findTestData("Data Files/goodShape_SYS")
		}
	}
	def void launchGoodShape() {
		WebUI.openBrowser(GlobalVariable.URL)
		WebUI.maximizeWindow()
		WebUI.waitForElementVisible(pageLabel, GlobalVariable.pageWaitTime)
	}
	def void addToCart() {
		TestObject ProductClick = new TestObject("product1Add").addProperty("xpath",ConditionType.EQUALS,'//h3[contains(text(),"I\'m a product")]')
		TestObject addToCart =  findTestObject('Object Repository/goodShape_OR/genObj_SubmitButton',['desc':'addToCartButton','text':'Add to Cart'])
		TestObject selectColor = new TestObject("selectColorErrorLabel").addProperty("xpath",ConditionType.EQUALS,'//div[contains(text(),"Select Color")]')
		TestObject colorSelect = new TestObject("selectColor").addProperty("xpath",ConditionType.EQUALS,'(//ul[@class="UlPgZ _2-zrV"])/li/label/input[@aria-label="'+testdata.getValue("Color",1)+'"]/following-sibling::span')
		TestObject cartIcon = new TestObject("cartIcon").addProperty("xpath",ConditionType.EQUALS,'//img[@class="product-media"]')
		TestObject frame = new TestObject("cartIcon").addProperty("xpath",ConditionType.EQUALS,'//iframe[@class="_2QAyo"]')
		By colorSelectionAvailable = By.xpath("//div[@class='Popover827139189__popoverElement']/div/ul")
		TestObject nextLink = findTestObject("Object Repository/goodShape_OR/genObj_link",['desc':'NextLink','text':'Next'])

		WebDriver driver = DriverFactory.webDriver
		WebUI.click(ProductClick)
		for(int i=0;i<=2;i++) {
			WebUI.delay(3)
			if(driver.findElements(colorSelectionAvailable).size()==1) {
				WebUI.click(colorSelect)
			}
			else {
				assert true
			}
			WebUI.click(addToCart)
			WebUI.switchToFrame(frame,GlobalVariable.timeOut)
			WebUI.click(cartIcon)
			WebUI.switchToDefaultContent()
			WebUI.click(nextLink)
		}
	}
	def void subscribeForm() {
		TestObject emailInput = findTestObject('Object Repository/goodShape_OR/genObj_textBox',['desc':'enterEmail','id':'input_comp-kiaj7o5x'])
		TestObject submitButton = new TestObject("submitButton").addProperty("xpath",ConditionType.EQUALS,'//button[@class="_1_kc0"]')
		
		WebUI.setText(emailInput,RandomStringUtils.randomAlphabetic(4)+"@gmail.com")
		WebUI.click(submitButton)
		WebUI.verifyElementVisible(afterSubmitMessage)
	}
	def void getInTouch()
	{
		TestObject submitButton = findTestObject('Object Repository/goodShape_OR/genObj_SubmitButton',['desc':'submitButton','text':'Submit'])
		TestObject name = findTestObject('Object Repository/goodShape_OR/genObj_textBox',['desc':'nameInput','id':'input_comp-kia8grux'])
		TestObject address = findTestObject('Object Repository/goodShape_OR/genObj_textBox',['desc':'addressInput','id':'input_comp-kia8grv2'])
		TestObject email = findTestObject('Object Repository/goodShape_OR/genObj_textBox',['desc':'email','id':'input_comp-kia8grv62'])
		TestObject phone = findTestObject('Object Repository/goodShape_OR/genObj_textBox',['desc':'addressInput','id':'input_comp-kia8grva'])
		
		WebUI.click(pageLabel)
		WebUI.click(submitButton)
		WebUI.setText(name,RandomStringUtils.randomNumeric(4)+"@!*")
		WebUI.setText(address,RandomStringUtils.randomNumeric(4)+"@!*")
		WebUI.setText(email, RandomStringUtils.randomNumeric(4)+"@!*")
		WebUI.setText(phone, RandomStringUtils.randomAlphabetic(7))
		WebUI.click(submitButton)
		
		WebUI.clearText(name)
		WebUI.setText(name, RandomStringUtils.randomAlphabetic(5))
		WebUI.clearText(address)
		WebUI.setText(address, RandomStringUtils.randomAlphanumeric(7))
		WebUI.clearText(email)
		WebUI.setText(email, RandomStringUtils.randomAlphabetic(4)+"@gmail.com")
		WebUI.clearText(phone)
		WebUI.setText(phone,"6"+RandomStringUtils.randomNumeric(9))
		WebUI.click(submitButton)
		WebUI.verifyElementVisible(afterSubmitMessage)
		
	}
}
