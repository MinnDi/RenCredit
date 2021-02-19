package ru.appline.framework.basetestsclass;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.appline.framework.managers.InitManager;
import ru.appline.framework.managers.ManagerPages;
import ru.appline.framework.managers.TestPropManager;

import static ru.appline.framework.managers.DriverManager.getDriver;
import static ru.appline.framework.utils.AllureListener.addScreenshot;
import static ru.appline.framework.utils.PropConst.APP_URL;

public class BaseTests {

    /**
     * Менеджер страничек
     * @see ManagerPages#getManagerPages()
     */
    protected ManagerPages app = ManagerPages.getManagerPages();

    /**
     * Метод, выполняющийся перед всеми тестами каждого класса
     */
    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    /**
     * Метод, выполняющийся перед каждым тестом
     */
    @BeforeEach
    public void beforeEaach(){
        getDriver().get(TestPropManager.getTestPropManager().getProperty(APP_URL));
    }

    /**
     * Метод, выполняющиийся после всех тестов каждого класса
     */
    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
