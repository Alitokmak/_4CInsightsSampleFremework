package com._4C.tests;

import com._4C.pages.Login;
import com._4C.utilities.ConfigurationReader;
import com._4C.utilities.Driver;
import com.sun.tools.javac.util.Assert;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

public class Test4C {

    Login login =new Login();
    Actions action;

    @BeforeMethod
    public void setup(){
        action = new Actions(Driver.getDriver());
        Driver.getDriver().manage().timeouts().implicitlyWait(Long.valueOf(ConfigurationReader.getProperty("implicitwait")), TimeUnit.SECONDS);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }

    @AfterMethod
    public void teardown(){
        Driver.closeDriver();
    }

    //login with valid username and password
    @Test
    public void loginTest(){
        login.open();
        login.login(ConfigurationReader.getProperty("username"),ConfigurationReader.getProperty("password"));
        Assert.check(login.verifyLogin.getText().contains("Welcome"));

    }

    //login with invalid username and password
    @Test
    public void loginNegativeTest(){
        login.open();
        login.login(ConfigurationReader.getProperty("username"),ConfigurationReader.getProperty("password"));
        Assert.check(login.errorMessage.getText().contains("Specified user does not exis"));
    }

    //login with different users
    @Test
    public void loginWithMultibleUsers() throws Exception{
        String path ="Customers.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");

        for(int i=1; i<sheet.getLastRowNum(); i++){
            login.open();
            login.login(sheet.getRow(i).getCell(1).toString(),sheet.getRow(i).getCell(2).toString());
            Assert.check(login.verifyLogin.getText().contains("Welcome"));
            login.LogoutButton.click();
        }
        workbook.close();
    }
}
