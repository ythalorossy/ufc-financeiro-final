package br.ufc.uteis;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.struts.actions.DispatchAction;

public class ClassLoaderProperties extends DispatchAction{

	private static Properties props;
	private static ServletContext context;
	
	public ClassLoaderProperties() {
		try {
			String properties = context.getRealPath("utilidades.properties");
			props = new Properties();
			props.load(new FileInputStream(properties));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erro ao carregar o arquivo de properties");
		}
		 
	}
	
	public static String loader(ServletContext contextServer, String propriedade){
		context = contextServer;
		new ClassLoaderProperties();
		String valor = String.valueOf(props.get(propriedade));
		return valor;
	}

}
