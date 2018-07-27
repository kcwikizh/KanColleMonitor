/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.processor.image;

import java.io.FileInputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author VEP
 */
public class GetHash {
        public String getNewHash(String filename) {
            try {
                return getMD5Checksum(filename);
            }
            catch (Exception e) {
                return null;
            }
        }
    
        public static String getMD5Checksum(String filename) throws Exception {
            return DigestUtils.md5Hex(new FileInputStream(filename));
        }
}
