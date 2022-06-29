/* 
 *  Classe da execução da SpringApplication
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */
package br.com.unisc.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
