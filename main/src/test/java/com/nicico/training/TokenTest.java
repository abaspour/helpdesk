package com.nicico.training;

import org.apache.tomcat.util.codec.binary.Base64;

public class TokenTest {

	public static void main(String[] args) {
		String jwtToken = "";

		System.out.println("--------------- Decode JWT ---------------");
		System.out.println("JWT: " + jwtToken);

		String[] jwtTokenSplit = jwtToken.split("\\.");
		String base64EncodedHeader = jwtTokenSplit[0];
		String base64EncodedBody = jwtTokenSplit[1];
		String base64EncodedSignature = jwtTokenSplit[2];

		Base64 base64Url = new Base64(true);

		System.out.println("--------------- JWT Header ---------------");
		String header = new String(base64Url.decode(base64EncodedHeader));
		System.out.println("JWT Header: " + header);


		System.out.println("--------------- JWT Body ---------------");
		String body = new String(base64Url.decode(base64EncodedBody));
		System.out.println("JWT Body: " + body);

		System.out.println("--------------- JWT Signature ---------------");
		System.out.println("JWT Signature: " + base64EncodedSignature);
	}
}
