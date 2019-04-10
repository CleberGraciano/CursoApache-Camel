package br.com.caelum.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	public static void main(String[] args) throws Exception {

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:pedidos?delay=5s&noop=true").
				split(). //Comando usado para quebrar a mensagem Xml e apos isso usa o filter para filtrar as mensagens que contenham EBOOK
					xpath("/pedido/itens/item").
					log("${body}").
				filter().
					xpath("/item/formato[text()='EBOOK']").
				log("${id}").
				marshal().xmljson().
				log("${body}").
				setHeader("CamelFileName", simple("${file:name.noext}.json")).
				to("file:saida");
				
			}
			
		});
		
		context.start();
		Thread.sleep(20000);
		context.stop();

	}	
}
