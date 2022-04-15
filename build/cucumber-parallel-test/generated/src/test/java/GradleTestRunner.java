
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
public class GradleTestRunner {

    @RunWith(Cucumber.class)
    @CucumberOptions (
            glue = {"ru.alfabank.steps", "steps"},
            format = {"pretty", "json:build/cucumber/cucumber1.json"},
            features = {"/Users/osmovzh/IdeaProjects/testing/build/resources/test/features/Smoke.feature"},
            monochrome = false
    )
    public static class GradleTestRunner1 { }

    @RunWith(Cucumber.class)
    @CucumberOptions (
            glue = {"ru.alfabank.steps", "steps"},
            format = {"pretty", "json:build/cucumber/cucumber2.json"},
            features = {"/Users/osmovzh/IdeaProjects/testing/build/resources/test/features/Поиск репозиториев.feature"},
            monochrome = false
    )
    public static class GradleTestRunner2 { }

}
