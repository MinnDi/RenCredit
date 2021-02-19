package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.appline.framework.managers.DriverManager.getDriver;

public class StartPage extends BasePage {

    /**
     * Элементы меню
     */
    @FindBy(xpath = "//div[@class='service']/div[@class='service__title']")
    List<WebElement> menuOptions;

    /**
     * Метод для проверки открытия старовой страницы
     *
     * @return StartPage - Остаёмся на странице {@link StartPage}
     */
    public StartPage checkStartPageIsOpen() {
        log.info("Проверка, что стартовая страница открыта");
        assertThat(getDriver().getTitle(), is("Банк «Ренессанс Кредит»"));
        return this;
    }

    /**
     * Метод для перехода на страницу из меню
     *
     * @param menuOption - Имя страницы
     * @return ContributionsPage - переходим на страницу вкладов {@link ContributionsPage}
     */
    public ContributionsPage selectMenuOption(String menuOption) {
        for (WebElement option :
                menuOptions) {
            if (option.findElement(By.xpath("./div")).getAttribute("textContent").contains(menuOption)) {
                option = option.findElement(By.xpath("./a"));
                log.info("Найден элемент с текстом " + option.getAttribute("textContent"));
                scrollToElementJs(option);
                elementToBeClickable(option);
                action.moveToElement(option).click().build().perform();
                log.info("Переходим на страницу");
                break;
            }
        }
        return app.getContributionsPage();
    }
}
