package br.com.unisc.project.controller.viewController;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.unisc.project.view.MainView;

@Component
public class MainViewController implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.setProperty("java.awt.headless", "false");
		try {
			new MainView("Sistema de produtos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
