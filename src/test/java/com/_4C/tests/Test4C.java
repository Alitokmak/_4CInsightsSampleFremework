package com._4C.tests;

import com._4C.pages.Login;
import com._4C.utilities.ConfigurationReader;
import com._4C.utilities.Driver;

import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class Test4C {

    Login login =new Login();
    String username=ConfigurationReader.getProperty("username");
    String password=ConfigurationReader.getProperty("password");

    @BeforeMethod
    public void setup(){
        //This method will run before each test and will do setup
        Driver.getDriver().manage().timeouts().implicitlyWait(Long.valueOf(ConfigurationReader.getProperty("implicitwait")), TimeUnit.SECONDS);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }

    @AfterMethod
    //This method will run after each test and will teardown browser
    public void teardown(){
        Driver.closeDriver();
    }

    //login with valid username and password
    @Test
    public void loginTest(){
        login.login(username,password);
        Assert.assertEquals(login.verifyLogin.getText(),"Welcome");
    }

    //login with invalid username and password
    @Test
    public void loginNegativeTest(){
        login.login(username,password);
        Assert.assertEquals(login.errorMessage.getText(),"Specified user does not exist");

    }

    //login with different users
    @Test
    public void loginWithMultibleUsers() throws Exception{
        String path ="/Users/alitokmak/IdeaProjects/_4CInsights/src/test/resources/Customers.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");

        for(int i=1; i<sheet.getLastRowNum(); i++){
            //I use for loop to read usernames and passwords from excel file
            String username=sheet.getRow(i).getCell(0).getStringCellValue();
            String password=sheet.getRow(i).getCell(1).getStringCellValue();
            login.login(username,password);
            //I only do negative login test because I don't have valid credentiol to do positive login test
            Assert.assertEquals(login.errorMessage.getText(),"Specified user does not exist");
            //login.LogoutButton.click();
        }
        workbook.close();
    }
}
