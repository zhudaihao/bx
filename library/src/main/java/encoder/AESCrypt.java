package encoder;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCrypt {

	private final Cipher cipher;
	private final SecretKeySpec key;
	private AlgorithmParameterSpec spec;
	public static final String SEED_16_CHARACTER = "8f31ffc9848dcfcb0f7a6d4802b86c2d";

	public AESCrypt() throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));

		byte[] keyBytes = new byte[32];

		System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

		cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

		key = new SecretKeySpec(keyBytes, "AES");

		spec = getIV();

	}

	public AlgorithmParameterSpec getIV() {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
		IvParameterSpec ivParameterSpec;
		ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}

	/*
	 * 加密
	 */
	public String encrypt(String plainText) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key, spec);

		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

		String encryptedText = new String(Base64.encode(encrypted,
				Base64.DEFAULT), "UTF-8");
		String new_need_cash = encryptedText.replace("/", "_a")
				.replace("+", "_b").replace("=", "_c");
		return new_need_cash.trim();
	}
	
	
	

	/*
	 * 解密
	 */
	public String decrypt(String cryptedText) throws Exception {
		String new_need_cash = cryptedText.replace("_a", "/")
				.replace("_b", "+").replace("_c", "=");  
		cipher.init(Cipher.DECRYPT_MODE, key, spec);
		byte[] bytes = Base64.decode(new_need_cash, Base64.DEFAULT);
		byte[] decrypted = cipher.doFinal(bytes);
		String decryptedText = new String(decrypted, "UTF-8");
		return decryptedText;
	}

	public static void main(String[] args) {

		try {
			AESCrypt a = new AESCrypt();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}