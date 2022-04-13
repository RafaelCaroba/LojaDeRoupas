package br.senai.sp.caroba.clothesguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

// componente que ajuda em algum processo
@Service
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
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		// gera uma String aleatoria para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString() +
				getExtensao(arquivo.getOriginalFilename());
		// crias um BlobId
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		
		// criar um BlobInfo a partir do BlobId
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		
		// mandar BlobInfo para o storage, passando os bytes para ele
		storage.create(blobInfo, arquivo.getBytes());
		
		// retornar url para acessar o arquivo
		return String.format(DOWNLOAD_URL, nomeArquivo);
	}
	
	private String getExtensao(String nomeArquivo) {
	// retorna o trecho da string que vai do ultimo ponto até o fim
		return nomeArquivo.substring(nomeArquivo.lastIndexOf('.'));
	}
	
	// metodo para escluir a foto do firebase
	public void deletar(String nomeArquivo) {
		nomeArquivo = nomeArquivo.replace(PREFIX, "").replace(SUFFIX, "");
		// pega um blob através do nome do arquivo
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeArquivo));
		storage.delete(blob.getBlobId());
	}
	
}
