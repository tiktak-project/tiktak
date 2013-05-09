package br.org.tiktak.dashboard.pages.importacao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jmine.tec.web.wicket.pages.Template;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

import com.google.gson.reflect.TypeToken;

import bancosys.tec.exception.MessageCreator;
import br.org.tiktak.core.Eventv2;
import br.org.tiktak.core.GsonFactory;
import br.org.tiktak.dashboard.core.BDfuncionalidades;

public class UploadDeArquivo extends Template {

	List<BDfuncionalidades> listaFuncionalidades = new ArrayList<BDfuncionalidades>();
	Set<UUID> listaDeIds = new HashSet<UUID>();
	HashMap<String, Integer> mapa = new HashMap<String, Integer>();
	Integer totalDeEventos = 0;
	Form<Void> form = new Form<Void>("form");
	
	File bdDashboard = new File("dashboard.bd");

	@Override
	protected MessageCreator getHelpTextCreator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		
		final FileUploadField fileUploadField = new FileUploadField("upload");
		form.add(fileUploadField);
		
		Button button = new Button("botao"){
			@Override
			public void onSubmit() {
				super.onSubmit();
				FileUpload fileUpload = fileUploadField.getFileUpload();
				if(fileUpload == null){
					warn("é necessário fornecer o arquivo");
				}
				else{
					try {
						processarArquivo(fileUpload);
					} catch (IOException e) {
						error("erro ao importar arquivo: "+e.getMessage());
					}
				}
			}
		};
		form.add(button);
		this.add(form);
	}
	
	private void processarArquivo(FileUpload file) throws IOException{
		
		boolean ehPrimeiroBD = criaArquivoSeNaoExistir(file);
		if(!ehPrimeiroBD){
			FileReader reader = new FileReader(file.writeToTempFile());
			List<Eventv2> lista = GsonFactory.getGson().fromJson(reader, new TypeToken<List<Eventv2>>() {
			}.getType());
			
			String json = GsonFactory.getGson().toJson(lista.get(0));
			try{
				RandomAccessFile raf = new RandomAccessFile(bdDashboard, "rw");
				raf.seek(raf.length() - 3);
				raf.write((",\n" + json).getBytes());
				raf.write("\n]".getBytes());
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}	
	
	private boolean criaArquivoSeNaoExistir(FileUpload file){
		if(!bdDashboard.exists()){
			try {
				bdDashboard.createNewFile();
				RandomAccessFile writer = new RandomAccessFile(bdDashboard, "rw");
				writer.write(file.getBytes());
				writer.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private void deletaBD(){
		File bdDashboard = new File("dashboard.bd");
		if(bdDashboard.exists()){
			bdDashboard.delete();
		}
	}
}