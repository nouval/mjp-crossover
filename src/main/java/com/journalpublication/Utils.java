package com.journalpublication;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public final class Utils {
	
    private static String algorithm = "SHA-256";


	public static byte[] convertPdfToImage(byte[] bufferOfPdf) throws IOException {

		if (bufferOfPdf == null) 
			return null;

		ByteBuffer buf = ByteBuffer.wrap(bufferOfPdf);
		PDFFile pdfFile = new PDFFile (buf);

		int numpages = pdfFile.getNumPages (), newImageWidth = 0, newImageHeight = 0;
		Image[] images = new Image[numpages];

		for (int pagenbr=1; pagenbr<=numpages; pagenbr++) {

			PDFPage page = pdfFile.getPage (pagenbr);
			Rectangle2D r2d = page.getBBox ();
			double width = r2d.getWidth ();
			double height = r2d.getHeight ();
			width /= 72.0;
			height /= 72.0;
			int res = 120;
			width *= res;
			height *= res;		

			images[pagenbr-1] = page.getImage ((int) width, (int) height, r2d, null, true, true);
			
			newImageWidth = (int)width;
			newImageHeight += height;
		}

		BufferedImage newImage = new BufferedImage((int)newImageWidth, (int)newImageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = newImage.createGraphics();
		int offset = 0;
		for (int i=0; i<images.length; i++) {
			g.drawImage(images[i], 0, offset, null);
			offset += images[i].getHeight(null);		
		}	
		g.dispose();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( newImage, "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();		
		
        return imageInByte;
	}
	
	public static byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }	

	public static String hashPasswordAsBase64(String password, byte[] salt) {
		try {
	        MessageDigest digest;
			digest = MessageDigest.getInstance(algorithm);
	        digest.reset();
	        digest.update(salt);

	        return Base64.encodeBase64String(digest.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;
		}
	}

	public static boolean validatePassword(String hashedPassword, String salt, String passwordToValidate) {

		return hashedPassword.equals(Utils.hashPasswordAsBase64(passwordToValidate, Utils.stringToByte(salt)));
	}

	public static String generateSaltAsBase64() {
        byte[] salt = new byte[20];
		SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
		
        return Base64.encodeBase64String(salt);
	}
}
