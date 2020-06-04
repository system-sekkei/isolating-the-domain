package integration;

import example.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class SmokeScenario {

    @AfterEach
    void after(@Autowired WebDriver webDriver) {
        System.out.println(webDriver.getPageSource());
    }

    @Test
    void happyPath(@Autowired WebDriver webDriver) {
        webDriver.get("/");
        assertEquals("ダッシュボード", webDriver.getTitle());

        webDriver.findElement(By.linkText("従業員の一覧")).click();
        assertEquals("従業員の一覧", webDriver.getTitle());

        webDriver.findElement(By.linkText("従業員の新規登録")).click();
        assertEquals("従業員情報の新規登録", webDriver.getTitle());

        // 何も入れず確認ボタンを押してエラーが表示される
        webDriver.findElement(By.cssSelector("button.is-primary")).click();
        List<WebElement> エラーメッセージ = webDriver.findElements(By.cssSelector("p.is-danger"));
        assertEquals(3, エラーメッセージ.size());

        // 入力して確認ボタンを押すと確認画面
        webDriver.findElement(By.id("name.value")).sendKeys("テスト太郎");
        webDriver.findElement(By.id("phoneNumber.value")).sendKeys("090-1234-5678");
        webDriver.findElement(By.id("mailAddress.value")).sendKeys("test-taro@example.com");
        webDriver.findElement(By.cssSelector("button.is-primary")).click();
        assertEquals("従業員情報の登録の確認", webDriver.getTitle());

        // 登録ボタンを押すと完了
        webDriver.findElement(By.cssSelector(".button.is-primary")).click();
        assertEquals("従業員情報の登録の完了", webDriver.getTitle());

        // ナビバーから従業員一覧に移動
        webDriver.findElement(By.tagName("nav"))
                .findElement(By.linkText("従業員の一覧")).click();

        // 詳細から変更
        webDriver.findElement(By.linkText("テスト太郎")).click();
        assertEquals("詳細", webDriver.getTitle());
        webDriver.findElement(By.linkText("変更")).click();
        assertEquals("従業員情報の変更", webDriver.getTitle());

        webDriver.findElement(By.id("name")).clear();
        webDriver.findElement(By.id("name")).sendKeys("テスト次郎");
        webDriver.findElement(By.cssSelector(".button.is-primary")).click();
        assertEquals("詳細", webDriver.getTitle());

        //WebDriverWait wait = new WebDriverWait(webDriver, 5);
        //wait.until(wd -> wd.findElement(By.id("close")).isDisplayed());
        //assertTrue(webDriver.findElement(By.id("updateCompletedModal")).getText()
        //        .contains("従業員情報を変更しました。"));
        //webDriver.findElement(By.id("close")).click();

        // 時給
        webDriver.findElement(By.linkText("時給の履歴")).click();
        assertEquals("時給の履歴", webDriver.getTitle());

        // 時給の登録
        webDriver.findElement(By.cssSelector(".button.is-primary")).click();
        assertEquals("時給の登録", webDriver.getTitle());
        // TODO: 実際登録する

        // ダッシュボードに戻って勤務時間入力画面に遷移
        webDriver.findElement(By.tagName("nav"))
                .findElement(By.linkText("ダッシュボード")).click();
        webDriver.findElement(By.tagName("main"))
                .findElement(By.linkText("勤務時間の入力")).click();
        assertEquals("勤務時間の入力", webDriver.getTitle());

        // 勤務時間登録
        webDriver.findElement(By.cssSelector(".button.is-primary")).click();

        // ダッシュボードに戻って給与の一覧画面に遷移
        webDriver.findElement(By.tagName("nav"))
                .findElement(By.linkText("ダッシュボード")).click();
        webDriver.findElement(By.tagName("main"))
                .findElement(By.linkText("給与の一覧")).click();
        assertEquals("給与の一覧", webDriver.getTitle());

        // ダッシュボードに戻って給与の一覧画面に遷移
        webDriver.findElement(By.tagName("nav"))
                .findElement(By.linkText("ダッシュボード")).click();
        webDriver.findElement(By.tagName("main"))
                .findElement(By.linkText("従業員の一覧")).click();

        // 従業員削除
        webDriver.findElement(By.linkText("テスト次郎")).click();
        webDriver.findElement(By.id("deleteConfirmButton")).click();
        webDriver.findElement(By.id("deleteButton")).click();

        // ダッシュボードに戻って給与の一覧画面に遷移
        webDriver.findElement(By.tagName("nav"))
                .findElement(By.linkText("ダッシュボード")).click();
        webDriver.findElement(By.tagName("main"))
                .findElement(By.linkText("従業員の一覧")).click();
        // 削除したのがいない
        assertEquals(0, webDriver.findElements(By.linkText("テスト次郎")).size());
    }
}
