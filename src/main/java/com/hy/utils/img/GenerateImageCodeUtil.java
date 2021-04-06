package com.hy.utils.img;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * 生成二维码
 *
 */
public class GenerateImageCodeUtil {
	
	private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;   

	/**
	 * 图片默认宽度
	 */
	public static final int DEFAULT_BAR_WIDTH = 650;
	/**
	 * 图片默认高度
	 */
	public static final int DEFAULT_BAR_HEIGHT = 154;
	/**
	 * 图片默认宽度
	 */
	public static final int DEFAULT_QR_WIDTH = 500;
	/**
	 * 图片默认高度
	 */
	public static final int DEFAULT_QR_HEIGHT = 500;
	/**
	 * 图片默认格式
	 */
	public static final String DEFAULT_PIC_FORMAT = "png";
	

	/**
	 * 生成二维码(默认配置)
	 * @param content
	 * @return 返回二维码码图片输出流
	 * @throws Exception
	 */
	public static OutputStream generateQRCode(String content) throws Exception{
		return generateQRCode(content, DEFAULT_PIC_FORMAT, DEFAULT_QR_WIDTH, DEFAULT_QR_HEIGHT);
	}

	/**
	 * 生成二维码(默认配置)
	 * @param content
	 * @return 返回二维码码图片输出流
	 * @throws Exception
	 */
	public static void generateQRCode(String content, HttpServletResponse response) throws Exception{
		//生成二维码
		BitMatrix bitMatrix = generateQRCode(content, DEFAULT_BAR_WIDTH, DEFAULT_BAR_HEIGHT);
		ServletOutputStream servletOutputStream = null;
		FileInputStream fis = null;
		File file = null;
		try {
			file = File.createTempFile(UUID.randomUUID().toString(),".png");
			OutputStream stream = new FileOutputStream(file);
			MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_PIC_FORMAT, stream);
			servletOutputStream = response.getOutputStream();
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) != -1) {
				servletOutputStream.write(b, 0, b.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (file != null) {
				file.delete();
			}
		}
	}

	/**
	 * 生成二维码
	 * @param content
	 * @param format
	 * @param width
	 * @param height
	 * @param storePath
	 * @return 该二维码图片地址URL
	 * @throws Exception
	 */
	public static String generateQRCode(String content,String format,int width,int height,String storePath) throws Exception{
		//生成二维码
		BitMatrix bitMatrix = generateQRCode(content, width, height);
		//保存图片
		String absolutePath = storePath+"/"+getQRCodeName(format);
		Path path = (new File(absolutePath)).toPath();
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);
		return absolutePath;
	}
	/**
	 * 生成二维码(不存储在本地)
	 * @param content
	 * @param format
	 * @param width
	 * @param height
	 * @return 返回二维码码图片输出流
	 * @throws Exception
	 */
	public static OutputStream generateQRCode(String content,String format,int width,int height) throws Exception{
		//生成二维码
		BitMatrix bitMatrix = generateQRCode(content, width, height);
		File file = File.createTempFile(UUID.randomUUID().toString(),".png");
		OutputStream stream = new FileOutputStream(file);
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		return stream;
	}


	/**
	 * 生成条形码(默认配置)
	 * @param content
	 * @return 返回条形码图片输出流
	 * @throws Exception
	 */
	public static OutputStream generateBarCode(String content)throws Exception{
		return generateBarCode(content, DEFAULT_PIC_FORMAT, DEFAULT_BAR_WIDTH, DEFAULT_BAR_HEIGHT);
	}
	
	/**
	 * 生成条形码
	 * @param content
	 * @param format
	 * @param width
	 * @param height
	 * @param storePath
	 * @return 该条形码图片地址URL
	 * @throws Exception
	 */
	public static String generateBarCode(String content,String format,int width,int height,String storePath) throws Exception{
		//生成二维码
		BitMatrix bitMatrix = generateBarCode(content, width, height);
		//保存图片
		String absolutePath = storePath+"/"+getBarCodeName(format);
		Path path = (new File(absolutePath)).toPath();
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);
		return absolutePath;
	}
	/**
	 * 生成条形码(不存储在本地)
	 * @param content
	 * @param format
	 * @param width
	 * @param height
	 * @return 返回条形码图片输出流
	 * @throws Exception
	 */
	public static OutputStream generateBarCode(String content,String format,int width,int height) throws Exception{
		//生成二维码
		BitMatrix bitMatrix = generateBarCode(content, width, height);
		OutputStream stream = new FileOutputStream(new File(""));
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		return stream;
	}
	
	
	/**
	 * 生成条形码
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	private static BitMatrix generateBarCode(String content,int width,int height) throws Exception{
		Map<EncodeHintType, Object> map = new HashMap<EncodeHintType,Object>();
		map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODABAR, width, height,map);
		return deleteWhite(bitMatrix);
	}
	/**
	 * 生成二维码
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	private static BitMatrix generateQRCode(String content,int width,int height) throws Exception{
		Map<EncodeHintType, Object> map = new HashMap<EncodeHintType,Object>();
		map.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,map);
		return deleteWhite(bitMatrix);
	}
	/**
	 * 生成条形码图片名称
	 * @return
	 */
	private static String getBarCodeName(String format){
		StringBuffer strBuffer = new StringBuffer("BR-");
		strBuffer.append(getTimestamp());
		strBuffer.append("."+format);
		return strBuffer.toString();
	}
	/**
	 * 生成二维码图片名称
	 * @return
	 */
	private static String getQRCodeName(String format){
		StringBuffer strBuffer = new StringBuffer("QR-");
		strBuffer.append(getTimestamp());
		strBuffer.append("."+format);
		return strBuffer.toString();
	}
	/**
	 * 获取时间戳
	 * @return
	 */
	private static String getTimestamp(){
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return formater.format(new Date());
	}
	/**
	 * 
	 * @param matrix
	 * @param format
	 * @param file
	 * @param logoPath
	 * @throws IOException
	 */
	private static void writeToFile(BitMatrix matrix,String format,File file,String logoPath) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        Graphics2D gs = image.createGraphics();  
          
        //载入logo  
        Image img = ImageIO.read(new File(logoPath));  
        gs.drawImage(img, 125, 125, null);  
        gs.dispose();  
        img.flush();  
        if(!ImageIO.write(image, format, file)){  
            throw new IOException("Could not write an image of format " + format + " to " + file);    
        }  
    }  
	/**
	 * 
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix){  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
          
        for(int x=0;x<width;x++){  
            for(int y=0;y<height;y++){  
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
            }  
        }  
        return image;     
    }  
	/**
	 * 去白边
	 * @param matrix
	 * @return
	 */
	public static BitMatrix deleteWhite(BitMatrix matrix){  
	    int[] rec = matrix.getEnclosingRectangle();  
	    int resWidth = rec[2] + 1;  
	    int resHeight = rec[3] + 1;  
	  
	    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
	    resMatrix.clear();  
	    for (int i = 0; i < resWidth; i++) {  
	        for (int j = 0; j < resHeight; j++) {  
	            if (matrix.get(i + rec[0], j + rec[1]))  
	                resMatrix.set(i, j);  
	        }  
	    }  
	    return resMatrix;  
	}  

}
