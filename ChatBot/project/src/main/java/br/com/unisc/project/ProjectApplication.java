/*
 * Classe que trabalha a inicialização da aplicação desenvolvida
 * Autores @nicolasfischer @brunobolzan @lucasrodrigues 
 */

package br.com.unisc.project;

//importando bibliotecas utilizadas na classe
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

	/*
	 * main
	 * Objetivo: Métoto principal que trabalha com inicialização da aplicação chamando-a ao Telegram
	 * Retorno: void
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args); // chamada de método que executa a API
	}

}
