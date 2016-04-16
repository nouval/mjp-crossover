package com.journalpublication;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public final class Utils {

	public static byte[] convertPdfToImage(byte[] bufferOfPdf) throws IOException {

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
}
