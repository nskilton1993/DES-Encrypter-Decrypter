import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Scanner;
import javax.crypto.*;
import javax.crypto.spec.*;

// "Phrase Based Key Encryption" or "Password Based Key Encryption."
// To encrypt: java Encrypt_Decrypt_DES -e "Phrase key" plaintextFile encryptedFile
// To decrypt: java Encrypt_Decrypt_DES -d "Phrase key" encryptedFile newPlaintextFile
// very weak encryption, only ~56 bits.

public class Encrypt_Decrypt_DES {
	public static void main( String[ ] args) throws Exception{

		Scanner scan = new Scanner(System.in);
		
		System.out.println("to encrypt type e, to decrypt type d");
		String options = scan.next();  //”-e” to encrypt, “-d” to decrypt
		System.out.println("Enter the passphase:");
	    String passphrase = scan.next();  // Phrase to base encryption on.
	    
		System.out.println("Enter or drag and drop the file:");
	    String filePath = scan.next();
	    File fin = new File(filePath);    // File of plaintext or encrypted text.
	    File fout = new File(filePath + ".des");  // File to hold result

	    String algorithm = "PBEWithMD5AndDES";
	    byte[ ] salt = new byte[8];  //Used with short keys.
	    int iterations = 20;

	    boolean encrypting = (options.indexOf("e") != -1);

	    //Create key from phrase to be used to create the encryption key..
	    char [ ] pbeKeyData = new char[passphrase.length( )];
	    passphrase.getChars(0,passphrase.length( ), pbeKeyData,0);

	    //System.out.println(":" + pbeKeyData + ":");  // Use to see key data.
	    KeySpec ks = new PBEKeySpec(pbeKeyData);

	    SecretKeyFactory skf = SecretKeyFactory.getInstance( algorithm );
	    SecretKey key = skf.generateSecret(ks);

	    //Process input.
	    FileInputStream in = new FileInputStream(fin);
	    int length = (int)fin.length( );
	    if(!encrypting) in.read( salt );
	    byte[ ] input = new byte[length - (encrypting ? 0:8)];
	    in.read(input);
	    in.close( );

	    if(encrypting){
	    	//Create the salt from 8 bytes of the digest of P || M.
	    	MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.update( passphrase.getBytes( ) );
			md.update(input);
			byte[ ] digest = md.digest();
			System.arraycopy(digest, 0, salt, 0, 8);
		}

	    // Create the PBE algorithm parameters.
	    AlgorithmParameterSpec aps = new PBEParameterSpec(salt, iterations);
	    
	    //Create the cipher.
	    Cipher cipher = Cipher.getInstance(algorithm);
	    int mode = encrypting ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
	    cipher.init(mode, key, aps);
	    
	    // Process the entire message at once.
	    byte[ ] output = cipher.doFinal(input);
	    
	    // Finish
	    OutputStream out = new FileOutputStream(fout);
	    if(encrypting) out.write( salt );  // Include salt if encrypting.
	    out.write(output);
	    out.close( );
	    scan.close();
	}
}
