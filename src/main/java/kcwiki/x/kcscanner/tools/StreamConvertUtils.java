/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

/**
 *
 * @author iHaru
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamConvertUtils {
    //inputStream转outputStream
    public static ByteArrayOutputStream parse(InputStream in) throws Exception
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {   
            swapStream.write(ch);   
        }
        return swapStream;
    }
    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }
    //inputStream转String
    public static String parse_String(InputStream in) throws Exception
    {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {   
            swapStream.write(ch);   
        }
        return swapStream.toString();
    }
    //OutputStream 转String
    public static String parse_String(OutputStream out)throws Exception
    {
        ByteArrayOutputStream   baos=new   ByteArrayOutputStream();
        baos=(ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream.toString();
    }
    //String转inputStream
    public static ByteArrayInputStream parse_inputStream(String in)throws Exception
    {
        ByteArrayInputStream input=new ByteArrayInputStream(in.getBytes());
        return input;
    }
    //String 转outputStream
    public static ByteArrayOutputStream parse_outputStream(String in)throws Exception
    {
        return parse(parse_inputStream(in));
    }
}
