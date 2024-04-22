package ua.foxminded.yevhenpiven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import service.MenuDisplayService;

@Component
public class MyApplicationRunner implements ApplicationRunner {
    private final MenuDisplayService menuDisplayService;

    @Autowired
    public MyApplicationRunner(MenuDisplayService menuDisplayService) {
        this.menuDisplayService = menuDisplayService;
    }

    @Override
    public void run(ApplicationArguments args) {
        menuDisplayService.displayMenu();
    }
}