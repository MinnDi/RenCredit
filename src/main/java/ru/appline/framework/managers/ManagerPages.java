package ru.appline.framework.managers;

import ru.appline.framework.pages.*;

/**
 * @author Arkadiy_Alaverdyan
 * Класс для управления страничками
 */
public class ManagerPages {

    /**
     * Менеджер страничек
     */
    private static ManagerPages managerPages;

    /**
     * Стартовая страничка
     */
    StartPage startPage;

    /**
     * Страница вкладов
     */
    ContributionsPage contributionsPage;

    /**
     * Конструктор специально запривейтили (синглтон)
     * @see ManagerPages#getManagerPages()
     */
    private ManagerPages() {
    }

    /**
     * Ленивая инициализация ManagerPages
     *
     * @return ManagerPages
     */
    public static ManagerPages getManagerPages() {
        if (managerPages == null) {
            managerPages = new ManagerPages();
        }
        return managerPages;
    }

    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.StartPage}
     *
     * @return StartPage
     */
    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage.checkStartPageIsOpen();
    }

    /**
     * Ленивая инициализация {@link ru.appline.framework.pages.ContributionsPage}
     *
     * @return ContributionsPage - переходим на страницу вкладов
     */
    public ContributionsPage getContributionsPage() {
        if (contributionsPage == null) {
            contributionsPage = new ContributionsPage();
        }
        return contributionsPage.checkContributionsPageIsOpen();
    }

}
