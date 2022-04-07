package br.senai.sp.caroba.clothesguide.util;

import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;


public class FirebaseUtil {
	// var para guardra as credenciais do FireBase
	private Credentials credenciais;
	
	// var para acessar o storage 
	private Storage storage;
	
	// constante para o nome do Bucket
	private final String BUCKET_NAME = "clothesguide.appspot.com";
	
	// const para o prefixo da url
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/"+BUCKET_NAME+"/o/";
	
	// const para o sufixo da url
	private final String SUFFIX = "?alt=media";
	
	// const para a url
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// buscar as credenciais (arquivo JSON)
		Resource resource = new ClassPathResource("chavefirebase.json");
		
		try {
			// ler o arquivo para obter as credenciais
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			
			// acessa o serviço de storage
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}	
	}
	
	public String uploadFile(MultipartFile arquivo) {
		// gera uma String aleatoria para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString() +
				getExtensao(arquivo.getOriginalFilename());
		return "";
	}
	
	private String getExtensao(String nomeArquivo) {
	// retorna o trecho da string que vai do ultimo ponto até o fim
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
}
