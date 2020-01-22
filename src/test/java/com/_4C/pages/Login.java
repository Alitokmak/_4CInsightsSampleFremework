package com._4C.pages;

import com._4C.utilities.ConfigurationReader;
import com._4C.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    public Login(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath="//div/li/a")
    public WebElement loginBox;

    @FindBy(id ="email")
    public WebElement username;

    @FindBy(id ="password")
    public WebElement password;

    @FindBy(xpath ="//input[@value='Log In']")
    public WebElement loginButton;

    @FindBy(xpath ="//span[@class='field-error']")
    public WebElement errorMessage;

    @FindBy(xpath="test")
    public WebElement verifyLogin;

    @FindBy(xpath="test")
    public WebElement LogoutButton;


    public void login(String user, String pass){
        //loginBox.click();
        username.clear();
        username.sendKeys(user);
        username.clear();
        password.sendKeys(pass);
        loginButton.click();
    }

    public void openUrl(){
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
    }


}
