/**
 * Copyright 2017 Alfa Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.ru.Тогда;
import entities.Repositories;
import helpers.GitHubSearchPageHelper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;

@Slf4j
public class TemplateSteps {
    AkitaScenario akitaScenario = AkitaScenario.getInstance();
    GitHubSearchPageHelper gitHubSearchPageHelper = new GitHubSearchPageHelper();

    @Тогда("^список репозиториев на странице соответствует ответу сервиса из переменной \"([^\"]*)\"$")
    public void checkReposListOnPageCorrespondsToResponse(String response) throws Throwable {
        String responseAsString = akitaScenario.getVar(response).toString();
        Repositories repositories = gitHubSearchPageHelper.getRepositoriesFromResponse(responseAsString);
        List<String> repoNamesFromPage = gitHubSearchPageHelper.getRepoListNamesFromPage();
        List<String> repoNamesFromResponse = gitHubSearchPageHelper.getRepoListNamesFromResponse(repositories);
        assertThat(format("список со страницы [%s] не соответствует списку из ответа сервиса [%s]", repoNamesFromPage, repoNamesFromResponse), repoNamesFromPage.toArray(), arrayContainingInAnyOrder(repoNamesFromResponse.toArray()));
    }

    @When("I open Event Management Page from sidebar")
    public void iOpenEventManagementPageFromSidebar() {
        $("div.fake-clickable-sidebar").click();

        $(byText("Events")).scrollIntoView(true).click();
        $(byText("Event Management")).scrollIntoView(true).click();
        sleep(5000);
    }

    @Given("^I logged in SnowIQ using \"([^\"]*)\" as \"([^\"]*)\" with password \"([^\"]*)\"$")
    public void iLoggedInSnowIQUsingAsWithPassword(String entryPoint, String login, String password) throws Throwable {
        open("https://st-wa-qa-adminportal.azurewebsites.net/mc/login");
        sleep(3000);
        $(By.xpath("//button[(text()='" + entryPoint + "')]")).click();
        sleep(4000);
        $(By.xpath("//input[@name='loginfmt']")).setValue(login);
        $("input#idSIButton9").click();
        sleep(1000);
        $(By.xpath("//input[@name='passwd']")).setValue(password);
        $("input#idSIButton9").click();
        sleep(1000);
        System.out.println("Clicked button sign in");
        $("input#idSIButton9").click();
        sleep(3000);
        $(".snow-tabs__header div").shouldHave(text("Routes"));
    }

    @And("^I click button \"([^\"]*)\"$")
    public void iClickButton(String buttonText) throws Throwable {
        $(byXpath("//button[text()='" + buttonText + "']")).click();
    }

    @Then("^New Event form is shown$")
    public void newEventFormIsShown() {
        $(".ng-star-inserted").shouldHave(text("New Event"));
    }

    @When("^I submit form New Event with parameters$")
    public void iSubmitNewEventWithParameters(DataTable form) {
        List<Map<String, String>> table = form.asMaps(String.class, String.class);
        System.out.println(table.get(0).get("fieldName"));
        System.out.println(table.get(0).get("value"));

        $(byXpath("//label[text()='"+table.get(0).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(0).get("value"));
        String dayToday = Integer.toString(LocalDateTime.now().getDayOfMonth());
        String dayTomorrow = Integer.toString(LocalDateTime.now().plusDays(3).getDayOfMonth());

        $("div.snow-popup__body input.datepicker__control").click();
      //  sleep(10000);
        $(byXpath("//div[@class='snow-popup__body']//div[@class='calendar__day'][text()="+dayToday+"]")).click();
       // sleep(1000);
        $(byXpath("//label[text()='"+table.get(0).get("fieldName")+"']/following-sibling::div/input")).click();
        $(byXpath("//label[text()='"+table.get(2).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(2).get("value"));
        $(byXpath("//label[text()='"+table.get(3).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(3).get("value"));
        $(byXpath("//label[text()='"+table.get(4).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(4).get("value"));
        $(byXpath("//label[text()='"+table.get(5).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(5).get("value"));
        //$(byXpath("//label[text()='"+table.get(6).get("fieldName")+"']/following-sibling::div/input")).setValue(table.get(6).get("value"));

        $("div.snow-popup__body div.checkbox").click();
        $(byXpath("//button[text()='Create']")).scrollIntoView(true).click();


        sleep(100000);

    }

    @And("^I click to Create New Storm Event$")
    public void iClickToCreateNewStormEvent() {
        $("#createNewEventButton").click();
    }

    @And("^I filter event list with training including$")
    public void iFilterEventListWithTrainingIncluding() {
        $(".checkbox__status");
        $(".toolbar").$(byXpath("//*[@class='checkbox__label'][text()=Include Training]"));

    }

    @Then("^I see \"([^\"]*)\" in list of storm events$")
    public void iSeeInListOfStormEvents(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /*
    @Given("an open browser with SnowIQ")
    public void an_open_browser_with_snow_iq() {
        open("https://st-wa-qa-adminportal.azurewebsites.net/mc/login");
    }

    @And("I enter {string} as e-mail in microsoftonline login form")
    public void iEnterAsEMailInMicrosoftonlineLoginForm(String login) {
        // sleep(2000);
        $(By.xpath("//input[@name='loginfmt']")).setValue(login);
        $("input#idSIButton9").click();
    }

    @And("I enter {string} as password in microsoftonline login form")
    public void iEnterAsPasswordInMicrosoftonlineLoginForm(String password) {
        //sleep(2000);
        $(By.xpath("//input[@name='passwd']")).setValue(password);
        //  sleep(2000);
        // $(byText("Sign In")).click();
        $("input#idSIButton9").click();
        System.out.println("Clicked button sign in");
    }

    @And("I submit Stay Connected in microsoftonline login form")
    public void iSubmitStayConnectedInMicrosoftonlineLoginForm() {
        //sleep(2000);
        $("input#idSIButton9").click();
        //sleep(500000);
    }

    @Then("I see the Routes Page")
    public void iSeeTheRoutesPage() {
        sleep(3000);
        $(".snow-tabs__header div").shouldHave(text("Routes"));
        sleep(5000);
    }

    @When("^I click button \"([^\"]*)\"$")
    public void iClickButton(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }*/
}
