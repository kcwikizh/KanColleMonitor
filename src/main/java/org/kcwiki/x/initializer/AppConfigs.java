/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author x5171
 * http://www.baeldung.com/properties-with-spring#java
 */
@Configuration
@PropertySource("classpath:configuration/appconfig/appconfig.properties")
public class AppConfigs {

    @Autowired
    private Environment env;
    @Value("${global.useproxy}")
    private boolean proxy_allow_use;
    @Value("${global.proxyhost}")
    private String proxy_host;
    @Value("${global.proxyport}")
    private int proxy_port;
    @Value("${global.debug}")
    private boolean debug;
    @Value("${kcwiki.api.servers}")
    private String kc_servers_url;
    @Value("${kcwiki.api.upload.start2.url}")
    private String kcwiki_api_upload_start2_url;
    @Value("${kcwiki.api.upload.start2.token}")
    private String kcwiki_api_upload_start2_token;
    @Value("${mail.serverhost}")
    private String mail_server_host;
    @Value("${mail.serverport}")
    private int mail_server_port;
    @Value("${mail.username}")
    private String mail_server_username;
    @Value("${mail.password}")
    private String mail_server_password;
    @Value("${mail.from}")
    private String mail_sender;
    @Value("${mail.to}")
    private String[] mail_recipient;
    @Value("${mail.title}")
    private String mail_title;
    @Value("${file.constantfolder}")
    private String file_constantfolder;
    @Value("${message.notice}")
    private String message_notice;

    /**
     * @return the env
     */
    public Environment getEnv() {
        return env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(Environment env) {
        this.env = env;
    }

    /**
     * @return the proxy_host
     */
    public String getProxy_host() {
        return proxy_host;
    }

    /**
     * @param proxy_host the proxy_host to set
     */
    public void setProxy_host(String proxy_host) {
        this.proxy_host = proxy_host;
    }

    /**
     * @return the proxy_port
     */
    public int getProxy_port() {
        return proxy_port;
    }

    /**
     * @param proxy_port the proxy_port to set
     */
    public void setProxy_port(int proxy_port) {
        this.proxy_port = proxy_port;
    }

    /**
     * @return the kc_servers_url
     */
    public String getKc_servers_url() {
        return kc_servers_url;
    }

    /**
     * @param kc_servers_url the kc_servers_url to set
     */
    public void setKc_servers_url(String kc_servers_url) {
        this.kc_servers_url = kc_servers_url;
    }

    /**
     * @return the mail_server_host
     */
    public String getMail_server_host() {
        return mail_server_host;
    }

    /**
     * @param mail_server_host the mail_server_host to set
     */
    public void setMail_server_host(String mail_server_host) {
        this.mail_server_host = mail_server_host;
    }

    /**
     * @return the mail_server_port
     */
    public int getMail_server_port() {
        return mail_server_port;
    }

    /**
     * @param mail_server_port the mail_server_port to set
     */
    public void setMail_server_port(int mail_server_port) {
        this.mail_server_port = mail_server_port;
    }

    /**
     * @return the mail_server_username
     */
    public String getMail_server_username() {
        return mail_server_username;
    }

    /**
     * @param mail_server_username the mail_server_username to set
     */
    public void setMail_server_username(String mail_server_username) {
        this.mail_server_username = mail_server_username;
    }

    /**
     * @return the mail_server_password
     */
    public String getMail_server_password() {
        return mail_server_password;
    }

    /**
     * @param mail_server_password the mail_server_password to set
     */
    public void setMail_server_password(String mail_server_password) {
        this.mail_server_password = mail_server_password;
    }

    /**
     * @return the mail_sender
     */
    public String getMail_sender() {
        return mail_sender;
    }

    /**
     * @param mail_sender the mail_sender to set
     */
    public void setMail_sender(String mail_sender) {
        this.mail_sender = mail_sender;
    }

    /**
     * @return the mail_recipient
     */
    public String[] getMail_recipient() {
        return mail_recipient;
    }

    /**
     * @param mail_recipient the mail_recipient to set
     */
    public void setMail_recipient(String[] mail_recipient) {
        this.mail_recipient = mail_recipient;
    }

    /**
     * @return the mail_title
     */
    public String getMail_title() {
        return mail_title;
    }

    /**
     * @param mail_title the mail_title to set
     */
    public void setMail_title(String mail_title) {
        this.mail_title = mail_title;
    }

    /**
     * @return the message_notice
     */
    public String getMessage_notice() {
        return message_notice;
    }

    /**
     * @param message_notice the message_notice to set
     */
    public void setMessage_notice(String message_notice) {
        this.message_notice = message_notice;
    }

    /**
     * @return the proxy_allow_use
     */
    public boolean isProxy_allow_use() {
        return proxy_allow_use;
    }

    /**
     * @param proxy_allow_use the proxy_allow_use to set
     */
    public void setProxy_allow_use(boolean proxy_allow_use) {
        this.proxy_allow_use = proxy_allow_use;
    }

    /**
     * @return the file_constantfolder
     */
    public String getFile_constantfolder() {
        return file_constantfolder;
    }

    /**
     * @param file_constantfolder the file_constantfolder to set
     */
    public void setFile_constantfolder(String file_constantfolder) {
        this.file_constantfolder = file_constantfolder;
    }

    /**
     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * @return the kcwiki_api_upload_start2_url
     */
    public String getKcwiki_api_upload_start2_url() {
        return kcwiki_api_upload_start2_url;
    }

    /**
     * @param kcwiki_api_upload_start2_url the kcwiki_api_upload_start2_url to set
     */
    public void setKcwiki_api_upload_start2_url(String kcwiki_api_upload_start2_url) {
        this.kcwiki_api_upload_start2_url = kcwiki_api_upload_start2_url;
    }

    /**
     * @return the kcwiki_api_upload_start2_token
     */
    public String getKcwiki_api_upload_start2_token() {
        return kcwiki_api_upload_start2_token;
    }

    /**
     * @param kcwiki_api_upload_start2_token the kcwiki_api_upload_start2_token to set
     */
    public void setKcwiki_api_upload_start2_token(String kcwiki_api_upload_start2_token) {
        this.kcwiki_api_upload_start2_token = kcwiki_api_upload_start2_token;
    }

}
