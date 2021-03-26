package com.bradseg.seguros.exceptions;

public class ApiSiseIntegrationException extends Exception {
	private static final long serialVersionUID = -5132902474473087375L;

	public ApiSiseIntegrationException(String mensagem) {
		super(mensagem);
	}

	public ApiSiseIntegrationException(String mensagem, Throwable causa, String string) {
		super(mensagem, causa); //rever
	}

	public ApiSiseIntegrationException(Exception excecao) {
		super(excecao);
	}
}
