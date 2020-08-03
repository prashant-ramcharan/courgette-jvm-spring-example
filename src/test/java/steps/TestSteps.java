package steps;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import support.TestSupport;

@CucumberContextConfiguration
@SpringBootTest(classes = TestSupport.class)
public class TestSteps {

    @Autowired
    private TestSupport testSupport;

    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.log("Scenario failed so capturing a screenshot");

            TakesScreenshot screenshot = (TakesScreenshot) testSupport.getDriver();
            scenario.attach(screenshot.getScreenshotAs(OutputType.BYTES), "image/png", scenario.getName());
        }
        if (testSupport.getDriver() != null) {
            testSupport.getDriver().quit();
        }
    }

    @Given("^I navigate to Stack Overflow$")
    public void iNavigateToStackOverflow() {
        testSupport.getDriver().navigate().to("https://stackoverflow.com/");
    }

    @When("I navigate to Stack Overflow question page {int}")
    public void i_navigate_to_Stack_Overflow_question_page(Integer page) {
        testSupport.getDriver().navigate().to("https://stackoverflow.com/questions?page=" + page);
    }

    @Then("I verify Stack Overflow question page {int} is opened")
    public void i_verify_Stack_Overflow_question_page_is_opened(Integer page) {
        if (!testSupport.getDriver().getTitle().contains("Page " + page)) {
            throw new RuntimeException("The Stack Overflow page title does not contain the page number: " + page);
        }
    }

    @When("I use the following data table to navigate to a Stack Overflow question page")
    public void i_use_the_following_data_table_to_navigate_to_a_Stack_Overflow_question_page(io.cucumber.datatable.DataTable dataTable) {
        int pageNumber = Integer.valueOf(dataTable.cell(1, 0));
        i_navigate_to_Stack_Overflow_question_page(pageNumber);
    }
}
