package di.service.generatorQR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class GeneratorQR {
    public static byte[] generateQRCodeImage(String barcodeText) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 350, 350);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    public static void saveQRCodeImage(String barcodeText, String filePath) throws WriterException, IOException {
        byte[] qrCodeImage = generateQRCodeImage(barcodeText);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(qrCodeImage);
        }
    }
}
