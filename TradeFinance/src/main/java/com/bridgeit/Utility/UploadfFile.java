package com.bridgeit.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class UploadfFile {
	public static void main(String[] args) {

		String imagepath = "//home//bridgeit//BlockchainImage//bolloflading.jpg";
		System.out.println("encode image of base 64--------------");
		String base64imageString = encoder(imagepath);
		System.out.println("base64imagestring: " + base64imageString);
		System.out.println("decode image of base 64----------");
		decoder(base64imageString, "//home//bridgeit//BlockchainImage//bolloflading.jpg");
		

	}

	public static String encoder(String imagepath) {
		String base64image = "";
		File file = new File(imagepath);
		try (FileInputStream imageinfile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte[] image = new byte[(int) file.length()];
			imageinfile.read(image);
			base64image = Base64.getEncoder().encodeToString(image);
		} catch (FileNotFoundException e) {
			System.out.println("image not found: " + e);
		} catch (IOException e) {
			System.out.println("excpeiton while reading the data: " + e);
		}

		return base64image;

	}

	public static void decoder(String base64image, String imagepath) {
		try (FileOutputStream imageoutfile = new FileOutputStream(imagepath)) {
			byte[] imagebytearray = Base64.getDecoder().decode(base64image);
			System.out.println("image byte array: "+imagebytearray);
			imageoutfile.write(imagebytearray);
		} catch (FileNotFoundException e) {
			System.out.println("image not found: " + e);
		} catch (IOException e) {
			System.out.println("excpeiton while reading the data: " + e);
		}
	}
}
