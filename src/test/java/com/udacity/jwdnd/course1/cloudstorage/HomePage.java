package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    private final JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void uploadFile() {
        js.executeScript("arguments[0].click();", fileUpload);
    }

    public void addNewNote() {
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }
}
