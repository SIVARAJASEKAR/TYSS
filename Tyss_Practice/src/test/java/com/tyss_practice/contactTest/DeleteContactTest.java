package com.tyss_practice.contactTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.tyss_practice.genericUtils.PrimaryClass;

/**
 * 
 * @author Rajasekar
 * 
 *         This class is used for Delete Contact from Contact Page
 *
 */
public class DeleteContactTest extends PrimaryClass {
	/**
	 * This Method is used for Delete the Contact with Organization
	 * 
	 * @throws Throwable
	 */
	@Test
	public void deleteContactWithOrganization() throws Throwable {

		/* Fetch Test Script specific data */
		String orgName = excelLib.getExcelData("contact", 1, 2) + "_" + wLib.getRandomNumber();
		String org_Type = excelLib.getExcelData("contact", 1, 3);
		String org_industry = excelLib.getExcelData("contact", 1, 4);
		String contactName = excelLib.getExcelData("contact", 1, 5);

		/* step 3 : Navigate to Organization page */
		driver.findElement(By.linkText("Organizations")).click();

		/* step 4 : Navigate to create new Organization page */
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();

		/* step 5 : create Organization */
		driver.findElement(By.name("accountname")).sendKeys(orgName);

		WebElement swb1 = driver.findElement(By.name("accounttype"));
		wLib.select(swb1, org_Type);

		WebElement swb2 = driver.findElement(By.name("industry"));
		wLib.select(swb2, org_industry);

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		/* step 6 : Verify the Creation of Organization */
		String actOrgName = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();

		Assert.assertTrue(actOrgName.contains(orgName));

		/* step 7 : Navigate to Contact Page */
		driver.findElement(By.linkText("Contacts")).click();

		/* step 8 : Navigate to create new Contact Page */
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();

		/* step 9 : Create New Contact */
		driver.findElement(By.name("lastname")).sendKeys(contactName);
		driver.findElement(By.xpath("//input[@name='account_name']/following-sibling::img")).click();
		// Go to New window
		wLib.switchToNewTab(driver, "specific_contact_account_address");
		// Search the organization
		driver.findElement(By.name("search_text")).sendKeys(orgName);
		driver.findElement(By.name("search")).click();
		// Select the organization
		driver.findElement(By.linkText(orgName)).click();
		// come back to parent Window
		wLib.switchToNewTab(driver, "Administrator - Contacts");

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		/* step 10: Verify the Creation of Contact */
		String actconatct = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		Assert.assertTrue(actconatct.contains(contactName));
		System.out.println("Contact created Sucessfully");

		// Delete contact
		/* step 11 : Navigate to Contact Page */
		driver.findElement(By.linkText("Contacts")).click();

		/* step 12 : Search for the Contact */
		driver.findElement(By.name("search_text")).sendKeys(contactName);
		WebElement optionListBox = driver.findElement(By.id("bas_searchfield"));
		wLib.select(optionListBox, excelLib.getExcelData("org", 4, 5));
		driver.findElement(By.name("submit")).click();

		boolean waitFlag1 = wLib.customWait(driver);

		/* step 13 : Delete the Contact */
		if (waitFlag1)
			driver.findElement(By.xpath("//a[text()='" + contactName + "']/..//preceding-sibling::td[3]")).click();
		driver.findElement(By.cssSelector("input[value='Delete']")).click();
		wLib.alertOk(driver);

		/* step 14 : Verify the Deletion of Contact */
		String actConDelConformation = driver.findElement(By.xpath("//span[contains(text(),'No Contact Found')]"))
				.getText();
		String expConDelConformation = excelLib.getExcelData("org", 4, 4);
		boolean Conflag = actConDelConformation.contains(expConDelConformation);
		Assert.assertTrue(Conflag);
		System.out.println("Contact Deleted Sucessfully");

		// Delete organization
		/* step 15 : Navigate to the Organization Page */
		driver.findElement(By.linkText("Organizations")).click();

		/* step 16 : Search the Organization */
		driver.findElement(By.name("search_text")).sendKeys(orgName);
		WebElement optionListBox1 = driver.findElement(By.id("bas_searchfield"));
		wLib.select(optionListBox1, excelLib.getExcelData("org", 1, 6));
		driver.findElement(By.name("submit")).click();
		
		/* step 17 : Select the Organization */		
		driver.findElement(By.xpath("//tr[1]//a[text()='" + orgName + "']/..//preceding-sibling::td[2]")).click();

		/* step 18 : Delete the Organization */
		driver.findElement(By.cssSelector("input[value='Delete']")).click();
		wLib.alertOk(driver);
		driver.findElement(By.name("submit")).click();
		/* step 19 : Verify the Deletion of Organization */
		String actDelConformartion = driver.findElement(By.xpath("//span[contains(text(),'No Organization Found')]"))
				.getText();
		String expDelConformation = excelLib.getExcelData("org", 4, 3);
		boolean flag = actDelConformartion.contains(expDelConformation);
		Assert.assertTrue(flag);
	}
}
