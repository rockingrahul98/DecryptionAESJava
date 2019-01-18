package com.dmax.decryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DecryptApp extends JFrame {

    @Override
    protected void frameInit() {
        super.frameInit();
        try {
            JFrame f = new JFrame("Dmax Decryption Tool");

            Label jidLable = new Label();
            jidLable.setText("USER JID");
            jidLable.setBounds(50, 50, 100, 50);

            //empty label which will show event after button clicked
            //textfield to enter name
            TextField userNameField = new TextField();
            userNameField.setBounds(50, 100, 200, 50);

            //enter name label
            Label encryptLebel = new Label();
            encryptLebel.setText("Encrypted Text");
            encryptLebel.setBounds(50, 150, 100, 50);

            //empty label which will show event after button clicked
            //textfield to enter name
            TextField encryptTextField = new TextField();
            encryptTextField.setBounds(50, 200, 400, 200);


            Label decryptLebel = new Label();
            decryptLebel.setText("Decrypted Text");
            decryptLebel.setBounds(550, 150, 100, 50);

            TextArea decryptTextField = new TextArea();
            decryptTextField.setBounds(550, 200, 400, 200);


            //submit button
            JButton b = new JButton("DECRYPT");
            b.setBounds(450, 450, 100, 40);
            //add to frame
            f.add(jidLable);
            f.add(userNameField);
            f.add(encryptLebel);
            f.add(encryptTextField);

            f.add(decryptLebel);
            f.add(decryptTextField);
            f.add(b);
            f.setSize(1000, 600);
            f.setLayout(null);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //action listener
            b.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent event) {
                    String msg = encryptTextField.getText().toString();
                    String userName = userNameField.getText().toString();
                    if (msg != null) {
                        try {
                            String decrypt = decrypt(msg, userName);
                            decryptTextField.setText(decrypt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String decrypt(String encrypted, String username) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String secretKey = username + "srtgtgtgthtryhgytughtrtfgtryhfgu";
        String iv = username + "rtdefersdcvtyuhg";
        byte[] KEY = secretKey.getBytes("UTF8");
        byte[] ivx = iv.getBytes("UTF8");
        IvParameterSpec ivSpec = new IvParameterSpec(ivx);
        byte[] srcBuff = hexStringToByteArray(encrypted);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        ecipher.init(2, skeySpec, ivSpec);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decryptedBytes = ecipher.doFinal(srcBuff);
        return new String(decryptedBytes, "UTF8");
    }

    public static String encrypt(String message) {
        StringBuffer result = new StringBuffer();
        String secretKey = "srtgtgtgthtryhgytughtrtfgtryhfgu";
        try {
            byte[] KEY = secretKey.getBytes("UTF8");
            byte[] srcBuff = message.getBytes("UTF8");
            SecretKeySpec skeySpec = new SecretKeySpec(KEY, "AES");
            String iv = "rtdefersdcvtyuhg";
            byte[] ivx = iv.getBytes("UTF8");
            IvParameterSpec ivSpec = new IvParameterSpec(ivx);
            Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            ecipher.init(1, skeySpec, ivSpec);
            byte[] dstBuff = ecipher.doFinal(srcBuff);
            int encryptedByteLength = dstBuff.length;
            for (int i = 0; i < encryptedByteLength; ++i) {
                byte byt = dstBuff[i];
                result.append(Integer.toString((byt & 255) + 256, 16).substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];

        for (int i = 0; i < b.length; ++i) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }

        return b;
    }
}
