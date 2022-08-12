package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryNegativeTest {
    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void notShouldCardDeliveryWrongCity() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Тольятти");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void notShouldCardDeliveryEmptyCity() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void notShouldCardDeliveryNameEng() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Vasilievich Vasiliy");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void notShouldCardDeliveryNameWithSymbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич 1Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void notShouldCardDeliveryShortName() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("В");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void notShouldCardDeliveryEmptyName() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void notShouldCardDelivery4DaysAfterDate() {
        String planningDate = date(4);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }

    @Test
    void notShouldCardDelivery2DaysAfterDate() {
        String planningDate = date(2);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void notShouldCardTodayDate() {
        String planningDate = date(0);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void notShouldCardYesterdayDate() {
        String planningDate = date(-1);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void notShouldCardDeliveryEmptyDate() {
        String planningDate = date(1);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void notShouldWithoutCheckBox() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=agreement].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void notShouldCardDeliveryWrongPhoneSymbol() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("!78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notShouldCardDeliveryWrongPhoneOver11() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+788888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notShouldCardDeliveryWrongPhoneLess11() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+7888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notShouldCardDeliveryWithoutPlus() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Васильевич Василий");
        $("[data-test-id=phone] input").setValue("78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone] .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void notShouldCardDeliveryNameWithRareLetter() {
        String planningDate = date(3);
        SelenideElement form = $("form");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE, (planningDate));
        $("[data-test-id=name] input").setValue("Семён Петров");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }
}