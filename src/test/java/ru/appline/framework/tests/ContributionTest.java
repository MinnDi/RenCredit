package ru.appline.framework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.appline.framework.basetestsclass.BaseTests;
import ru.appline.framework.utils.CheckboxState;
import ru.appline.framework.utils.CurrencyEnum;
import ru.appline.framework.utils.TermEnum;

import java.util.stream.Stream;

public class ContributionTest extends BaseTests {

    @ParameterizedTest(name = "Оформление вклада в {1}")
    //@Test
    @MethodSource("ms")
    @Tag("SmokeTest")
    @DisplayName("Оформление вклада")
    public void startContributionTestDollars(String menuOptionName, CurrencyEnum curr, String contributionSumFieldName, int contributionSumValue,
                                             TermEnum contributionTerm, String monthlyAccrualFieldName, int monthlyAccrualValue,
                                             String checkboxFieldName, CheckboxState checkboxState, String percentAccruedFieldName,
                                             double percentAccruedValue, String contributionReplenishmentFieldName,
                                             int contributionReplenishmentValue) {
        app.getStartPage()
                .selectMenuOption(menuOptionName)
                .selectCurrencyOfContribution(curr)
                .fillInputFields(contributionSumFieldName, String.valueOf(contributionSumValue))
                .selectContributionTerm(contributionTerm)
                .fillInputFields(monthlyAccrualFieldName, String.valueOf(monthlyAccrualValue))
                .checkboxToState(checkboxFieldName, checkboxState)
                .assertResults(percentAccruedFieldName, String.valueOf(percentAccruedValue))
                .assertResults(contributionReplenishmentFieldName, String.valueOf(contributionReplenishmentValue))
                .assertSumResult(contributionSumValue + contributionReplenishmentValue + percentAccruedValue);
    }

    private static Stream<Arguments> ms() {
        return Stream.of(
                Arguments.of("Вклады", CurrencyEnum.USD, "Сумма вклада", 500000,
                        TermEnum.TWELVE_MONTH,"Ежемесячное пополнение", 25000,
                        "Ежемесячная капитализация", CheckboxState.ON,
                        "Начислено %", 962.10, "Пополнение за 12 месяцев", 275000),
                Arguments.of("Вклады", CurrencyEnum.RUB, "Сумма вклада", 300000,
                        TermEnum.SIX_MONTH,"Ежемесячное пополнение", 50000,
                        "Ежемесячная капитализация", CheckboxState.ON,
                        "Начислено %", 9132.17, "Пополнение за 6 месяцев", 250000));
    }
}