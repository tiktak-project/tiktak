package br.org.tiktak.core;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class Api {
	public static void registrarEvento(String usuario, String evento){
		Dados dados = new Dados(usuario, evento);
		Gson gson = new Gson();
		String json = gson.toJson(dados);
		System.out.println(json);
		
        try {
            FileWriter writer = new FileWriter("tik.tak");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args){
		Api.registrarEvento("albert", "cadastro");
	}
}
