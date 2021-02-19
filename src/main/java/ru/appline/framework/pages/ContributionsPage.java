package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.appline.framework.utils.CheckboxState;
import ru.appline.framework.utils.CurrencyEnum;
import ru.appline.framework.utils.TermEnum;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static ru.appline.framework.managers.DriverManager.getDriver;

public class ContributionsPage extends BasePage {

    /**
     * Поля выбора валюты вклада
     */
    @FindBy(xpath = "//label[@class='calculator__currency-field']")
    List<WebElement> currencyOptions;

    /**
     * Поля для заполнения при оформлении вклада
     */
    @FindBy(xpath = "//label[contains(@class,'currency-input-field')]//input")
    List<WebElement> inputFields;

    /**
     * Селектор продолжительности вклада
     */
    @FindBy(xpath = "//div[@id='period-styler']/select")
    WebElement termSelector;

    /**
     * Поля флажков выбора способа открытия вклада
     */
    @FindBy(xpath = "//div[@class='internet-bank']//label[@class='calculator__check-block']")
    List<WebElement> iBankCheckboxes;

    /**
     * Поля флажков выбора стратегии вывода средств
     */
    @FindBy(xpath = "//div[@class='calculator__content']//div[@class='calculator__check-row']/div[@class='calculator__check-row-field']/label[@class='calculator__check-block']")
    List<WebElement> withdrawCheckboxes;

    /**
     * Поля итогов вклада
     */
    @FindBy(xpath = "//tr[@class='calculator__dep-result-table-row']")
    List<WebElement> resultsFields;

    /**
     * Поле суммы к снятию по итогу вклада
     */
    @FindBy(xpath = "//div[@class='calculator__dep-result-value']//span[@class='js-calc-result']")
    WebElement sumResultField;

    /**
     * Метод для проверки открытия страницы вклада
     *
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage checkContributionsPageIsOpen() {
        log.info("Проверка, что страница вкладов открыта");
        assertThat(getDriver().getTitle(), is("Вклады"));
        return this;
    }

    /**
     * Метод для выбора валюты вклада
     *
     * @param currency - необходимая валюта
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage selectCurrencyOfContribution(CurrencyEnum currency) {
        scrollToElementJs(getDriver().findElement(By.xpath("//h1")));
        String curr = null;
        switch (currency) {
            case RUB: {
                curr = "Рубли";
                break;
            }
            case USD: {
                curr = "Доллары США";
                break;
            }
            default:
                Assertions.fail("Вид валюты" + currency + " не предусмотрен!");
        }
        for (WebElement currencyOption :
                currencyOptions) {
            if (curr.equals(currencyOption.findElement(By.xpath(".//span[@class='calculator__currency-field-text']")).getAttribute("textContent"))) {
                currencyOption = currencyOption.findElement(By.xpath(".//input[@type='radio']"));
                action.moveToElement(currencyOption).click().build().perform();
                assertThat(currencyOption.getAttribute("checked"), is("true"));
                break;
            }
        }
        log.info("Выбран тип валюты \"" + curr + "\".");
        return this;
    }

    /**
     * Метод для заполнения полей при оформлении вклада
     *
     * @param field - Имя поля
     * @param value - Значение, которое необходимо предать полю
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage fillInputFields(String field, String value) {
        for (WebElement inputField :
                inputFields) {
            if (inputField.findElement(By.xpath("./../../../label[@class='calculator__slide-input-label']")).getAttribute("textContent").equals(field)) {
                scrollToElementJs(currencyOptions.get(0));
                fillInputField(inputField, value.replaceAll("\\D", ""));
                assertThat(inputField.getAttribute("value").replaceAll("\\D", ""), is(value.replaceAll("\\D", "")));
            }
        }
        log.info("Поле " + field + " заполнено значением " + value);
        return this;
    }

    /**
     * Метод для выбора срока вклада
     *
     * @param termEnum - значение, которое необходимо установить
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage selectContributionTerm(TermEnum termEnum) {
        Select selector = new Select(termSelector);
        String term = null;
        switch (termEnum) {
            case THREE_MONTH: {
                selector.selectByValue("3");
                term = "3";
                break;
            }
            case SIX_MONTH: {
                selector.selectByValue("6");
                term = "6";
                break;
            }
            case NINE_MONTH: {
                selector.selectByValue("9");
                term = "9";
                break;
            }
            case TWELVE_MONTH: {
                selector.selectByValue("12");
                term = "12";
                break;
            }
            case THIRTEEN_MONTH: {
                selector.selectByValue("13");
                term = "13";
                break;
            }
            case EIGHTEEN_MONTH: {
                selector.selectByValue("18");
                term = "18";
                break;
            }
        }
        assertThat(termSelector.getAttribute("value"), is(term));
        log.info("Установлен срок вклада на " + term + " м.");
        return this;
    }

    /**
     * Метод для установки флажка в определенное значение
     *
     * @param checkboxName - Имя флажка
     * @param state        - Состояние, которое необходимо передать флажку
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage checkboxToState(String checkboxName, CheckboxState state) {
        if (checkboxName.equals("В отделении банка") || checkboxName.equals("В интернет-банке")) {
            setCheckboxes(iBankCheckboxes, checkboxName, state);
        } else if (checkboxName.equals("Ежемесячная капитализация") || checkboxName.equals("Частичное снятие")) {
            setCheckboxes(withdrawCheckboxes, checkboxName, state);
//            if (state.equals(CheckboxState.OFF)) assertThat(withdrawCheckboxes.size(), is(2));
//            else assertThat(withdrawCheckboxes.size(), is(1));
        } else Assertions.fail("Флажка " + checkboxName + " на странице нет!");
        return this;
    }

    /**
     * Метод для проверки результатов вклада
     *
     * @param fieldName - Имя поля
     * @param value     - Значение, которое должно быть на поле
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage assertResults(String fieldName, String value) {
        String titleXPath = "./td[@class='calculator__dep-result-table-cell calculator__dep-result-table-cell_title']";
        String resultXPath = ".//td[@class='calculator__dep-result-table-cell calculator__dep-result-table-cell_val']//span[contains(@class, 'js-calc') and not (contains(@class, 'currency'))]";
        for (WebElement resultField :
                resultsFields) {
            if (resultField.findElement(By.xpath(titleXPath))
                    .getAttribute("textContent").equals(fieldName)) {
                assertThat(resultField.findElement(By.xpath(resultXPath))
                        .getAttribute("textContent")
                        .replaceAll("[^,.0-9]+", "").replaceAll(",", "."), is(value.replaceAll("[^,.0-9]+", "")));
                log.info("Проверка поля " + fieldName + " по значению " + value + " успешно пройдена.");
            }
        }
        return this;
    }

    /**
     * Метод для проверки суммы вклада
     *
     * @param sumResult - Сумма при выводе средств, котоое должно быть отражено
     * @return ContributionsPage - остаёмся на странице {@link ContributionsPage}
     */
    public ContributionsPage assertSumResult(double sumResult) {
        assertThat(sumResultField.getAttribute("textContent").replaceAll("[^,.0-9]+", ""),
                is(String.format("%.2f", sumResult).replaceAll("[^,.0-9]+", "")));
        log.info("Проверка поля суммы к снятию по итогу вклада на сумму " + sumResult + " успешно пройдена");
        return this;
    }

    /**
     * Внутренний метод для установки флажка
     *
     * @param checkboxes   - Массив флажков определенной группы, в которой состоит необходимый флажок
     * @param checkboxName - Название флажка
     * @param state        - Состояние, которое необходимо передать флажку
     */
    private void setCheckboxes(List<WebElement> checkboxes, String checkboxName, CheckboxState state) {
        for (WebElement checkbox :
                checkboxes) {
            if (checkbox.findElement(By.xpath(".//span[@class='calculator__check-text']"))
                    .getAttribute("textContent").equals(checkboxName)) {
                checkbox = checkbox.findElement(By.xpath(".//input"));
                if (checkbox.isSelected() != state.equals(CheckboxState.ON)) {
                    action.moveToElement(checkbox).click().build().perform();
                    assertThat(checkbox.isSelected(), is(state.equals(CheckboxState.ON)));
                }
            } else {
                checkbox = checkbox.findElement(By.xpath(".//input"));
                if (checkbox.isSelected() == state.equals(CheckboxState.ON)) {
                    action.moveToElement(checkbox).click().build().perform();
                    assertThat(checkbox.isSelected(), is(!state.equals(CheckboxState.ON)));
                }
            }
        }
        log.info("Установили значени флажка " + checkboxName + " в состояние" + state);
    }
}
