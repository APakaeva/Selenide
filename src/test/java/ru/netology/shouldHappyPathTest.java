package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class appCardDeliveryTest {
    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldHappyPath() {
        String planningDate = date(3);
        //Configuration.holdBrowserOpen = true;
        SelenideElement form = $("form");
        //$x("//*[contains(text(), 'Город')]").setValue("Санкт-Петербург");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        //*[@data-test-id="date"]
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Петров-Васильевич Василий");
        $("[data-test-id=phone] input").setValue("+78888888888");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).should(exactText("Встреча успешно забронирована на " + planningDate));
    }
}

//void shouldRegisterByAccountNumberDOMModification() {
//Configuration.holdBrowserOpen = true;
//open("http://localhost:9999");
//$x("//*[contains(text(),'номеру счёта')]").click();
//$$("[type='text']").filter(visible).first().setValue("4055 0100 0123 4613 8564");
//$$("[name='phone']").exclude(hidden).get(0).setValue("+7 999 999 99 99");
//$$(By.className("button__content")).last().click();
//$x("//*[contains(text(),'Успешная авторизация')]").shouldBe(visible, Duration.ofSeconds(10));
//$(By.tagName("h2")).shouldHave(exactText("Личный кабинет"), Duration.ofMillis(8000));
