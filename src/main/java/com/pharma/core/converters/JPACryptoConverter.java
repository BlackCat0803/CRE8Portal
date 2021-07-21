package com.pharma.core.converters;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.crypto.codec.Base64;
/**
 * This class is a JPA Crypto Converter that stores the confidential information such as Patient and Physician CreditCard entity in the encrypted form
 *
 */
@Converter
@Configuration
@PropertySource("classpath:encryption.properties")
public class JPACryptoConverter implements AttributeConverter<String, String> {

	static Logger logger = LoggerFactory.getLogger(JPACryptoConverter.class);

	private static String ALGORITHM = null;
	private static byte[] KEY = null;

	public static final String algorithm_property_key = "encryption.algorithm";
	public static final String secret_property_key = "encryption.key";
	
	
			
	//static final Properties properties = new Properties();
	
	
	static {
		
		try {
			
			 String algorithm_property_key_value="";
			 String secret_property_key_value="";  
			
			
			AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(JPACryptoConverter.class);
			ConfigurableEnvironment env = context.getEnvironment();
			 //System.out.println(env.getPropertySources());
			 algorithm_property_key_value=env.getProperty(algorithm_property_key).toString();
			 secret_property_key_value=env.getProperty(secret_property_key).toString();
			 //System.out.println(env.getProperty(algorithm_property_key));
			 //System.out.println(env.getProperty(secret_property_key));
			 
			 ALGORITHM = algorithm_property_key_value;
			 KEY = secret_property_key_value.getBytes();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*try {
			properties.load(JPACryptoConverter.class.getClassLoader()
					.getResourceAsStream("persistence.properties"));
			
			
			
		} catch (Exception e) {
			logger.warn("Could not load properties file 'persistence.properties' using unsecure encryption key.");
			properties.put(algorithm_property_key, "AES/ECB/PKCS5Padding");
			properties.put(secret_property_key, "MySuperSecretKey");
		}
		ALGORITHM = (String) properties.get(algorithm_property_key);
		KEY = ((String) properties.get(secret_property_key)).getBytes();*/
	}

	public String convertToDatabaseColumn(String sensitive) {
		if(sensitive!=null){
			Key key = new SecretKeySpec(KEY, "AES");
			try {
				final Cipher c = Cipher.getInstance(ALGORITHM);
				c.init(Cipher.ENCRYPT_MODE, key);
				final String encrypted = new String(Base64.encode(c
						.doFinal(sensitive.getBytes())), "UTF-8");
				//System.out.println("encrypttext===="+encrypted);
				return encrypted;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else
			return "";
	}

	public String convertToEntityAttribute(String sensitive) {
		if(sensitive!=null){
			Key key = new SecretKeySpec(KEY, "AES");
			try {
				final Cipher c = Cipher.getInstance(ALGORITHM);
				c.init(Cipher.DECRYPT_MODE, key);
				final String decrypted = new String(c.doFinal(Base64
						.decode(sensitive.getBytes("UTF-8"))));
				//System.out.println("decrypttext===="+decrypted);
				return decrypted;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else
			return "";
	}
	
}