package com.example.ecomadventure;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PdfDownload extends AppCompatActivity {

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    private int pageHeight = 1120;
    private int pagewidth = 792;
    private Bitmap logo, logobmp,adventureImg,adventureImgbmp;
    private Button download;
    private PdfDocument pdfDocument;
    private File file;
    private String dateStr;
    private int noOfTickets;
    private int amount;
    private long billAmount;
    private String transactionId;
    private int imageId;
    private Bundle bundle;
    private String titleStr;
    private String username;
    private String mobile;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);
        bundle = getIntent().getExtras();

        imageId = bundle.getInt("image");
        amount = bundle.getInt("amount");
        titleStr = bundle.getString("title");
        dateStr = bundle.getString("Date");
        noOfTickets = bundle.getInt("NumberOfTicket");
        billAmount = amount * noOfTickets;
        transactionId = bundle.getString("TransactionId");
        username = bundle.getString("username");
        mobile = bundle.getString("mobile");
        email = bundle.getString("email");


        logo = BitmapFactory.decodeResource(getResources(),imageId);
        download = findViewById(R.id.download_pdf);
        logobmp = Bitmap.createScaledBitmap(logo, 300, 270, false);

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePdf();
            }
        });
    }
    private void generatePdf(){
        pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint title = new Paint();
        Paint line = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(logobmp, 209, 110, paint);

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(20);
        title.setColor(ContextCompat.getColor(this, R.color.black));


        canvas.drawText("EcomAdventure Imperious-Tours", 209, 80, title);
        canvas.drawText(titleStr, 209, 100, title);

        line.setStrokeWidth(3);
        canvas.drawLine(170,400,622,400,line);
        canvas.drawLine(170,440,622,440,line);
        canvas.drawLine(170,480,622,480,line);
        canvas.drawLine(170,520,622,520,line);
        canvas.drawLine(170,560,622,560,line);
        canvas.drawLine(170,600,622,600,line);
        canvas.drawLine(170,640,622,640,line);

        canvas.drawLine(170,400,170,640,line);
        canvas.drawLine(340,400,340,640,line);
        canvas.drawLine(622,400,622,640,line);

        canvas.drawText("Name", 180, 420, title);
        canvas.drawText("Mobile", 180, 460, title);
        canvas.drawText("Date", 180, 500, title);
        canvas.drawText("No. of Ticket", 180, 540, title);
        canvas.drawText("Bill Amount", 180, 580, title);
        canvas.drawText("Transaction Id", 180, 620, title);

        canvas.drawText(username,350,420,title);
        canvas.drawText(mobile,350,460,title);
        canvas.drawText(dateStr,350,500,title);
        canvas.drawText(String.valueOf(noOfTickets),350,540,title);
        canvas.drawText(String.valueOf(billAmount),350,580,title);
        canvas.drawText(transactionId,350,620,title);

        line.setColor(ContextCompat.getColor(this,R.color.black));
        pdfDocument.finishPage(myPage);
        System.out.println("Reached 1");

        //file = new File(String.valueOf(getApplicationContext().getExternalFilesDir(null)),"Ticket.pdf");
        String pathstr = Environment.getExternalStorageDirectory().getPath() + "/Download/EcomAdventureTicket2"+transactionId+".pdf";
        //Environment.getExternalStorageDirectory().getPath() + "/Download/EcomAdventureTicket2.pdf"
        file = new File(pathstr);
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(PdfDownload.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
            //Toast.makeText(PdfDownload.this, "wait for while you will be redirected to Home Screen", Toast.LENGTH_SHORT).show();
            sendPdfToEmail();
            callHomeScreen();
        }catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }

    private void sendPdfToEmail() {
        String senderEmail = "ecomadventureapp@gmail.com";
        String password ="Ecom@2022";
        String receiverEmail = email;

        String host ="smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable",true);
        properties.put("mail.smtp.auth",true);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(receiverEmail));
            mimeMessage.setSubject("Ticket is Booked ");

            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText("Ecom Adventure ticket is booked ");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource dataSource = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName("invoice.pdf");

            multipart.addBodyPart(messageBodyPart);

            mimeMessage.setContent(multipart);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            thread.start();
        } catch (MessagingException e) {
            //e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void callHomeScreen() {
        Intent intent = new Intent(getApplicationContext(),HomeScreen.class);
        intent.putExtra("username",username);
        startActivity(intent);
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED ;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..from onrequest", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}