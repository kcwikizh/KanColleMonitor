/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author iHaru
 * http://www.baeldung.com/properties-with-spring#java
 */
@Configuration
//@PropertySource(value={"file:${user.dir}/configuration/appconfig/appconfig.properties"})
public class AppConfig {
    
    
    @Value("${user.dir}")
    private String system_user_dir;     
    @Value("${myprops.global.useproxy}")
    private boolean allow_use_proxy;
    @Value("${myprops.global.proxyhost}")
    private String proxy_host;
    @Value("${myprops.global.proxyport}")
    private int proxy_port;
    @Value("${myprops.global.debug}")
    private boolean debug;
    @Value("${myprops.global.folder.webroot}")
    private String folder_webroot;
    @Value("${myprops.global.folder.privatedata}")
    private String folder_privatedata;
    @Value("${myprops.global.folder.download}")
    private String folder_download;
    @Value("${myprops.global.folder.storage}")
    private String folder_storage;
    @Value("${myprops.global.folder.template}")
    private String folder_template;
    @Value("${myprops.global.folder.publish}")
    private String folder_publish;
    @Value("${myprops.global.folder.workspace}")
    private String folder_workspace;
    @Value("${myprops.global.file.filelist}")
    private String file_filelist;
    @Value("${myprops.application.superuser.username}")
    private String application_superuser_username;
    @Value("${myprops.application.superuser.password}")
    private String application_superuser_password;
    @Value("${myprops.kcwiki.api.servers}")
    private String kcwiki_api_servers;
    @Value("${myprops.kcwiki.api.upload.start2.url}")
    private String kcwiki_api_upload_start2_url;
    @Value("${myprops.kcwiki.api.upload.start2.token}")
    private String kcwiki_api_upload_start2_token;
//    @Value("${myprops.mail.serverhost}")
//    private String mail_server_host;
//    @Value("${myprops.mail.serverport}")
//    private int mail_server_port;
//    @Value("${myprops.mail.username}")
//    private String mail_server_username;
//    @Value("${myprops.mail.password}")
//    private String mail_server_password;
    @Value("${myprops.mail.from}")
    private String mail_sender;
    @Value("${myprops.mail.to}")
    private String[] mail_recipient;
    @Value("${myprops.mail.title}")
    private String mail_title;
    @Value("${myprops.message.notice}")
    private String message_notice;
    @Value("${myprops.kcserver.host}")
    private String kcserver_host;
    @Value("${myprops.kcserver.account.username}")
    private String kcserver_account_username;
    @Value("${myprops.kcserver.account.password}")
    private String kcserver_account_password;
    @Value("${myprops.database.name}")
    private String myprops_database_name;


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

    /**
     * @return the myprops_database_name
     */
    public String getMyprops_database_name() {
        return myprops_database_name;
    }

    /**
     * @param myprops_database_name the myprops_database_name to set
     */
    public void setMyprops_database_name(String myprops_database_name) {
        this.myprops_database_name = myprops_database_name;
    }

    /**
     * @return the folder_privatedata
     */
    public String getFolder_privatedata() {
        return folder_privatedata;
    }

    /**
     * @param folder_privatedata the folder_privatedata to set
     */
    public void setFolder_privatedata(String folder_privatedata) {
        this.folder_privatedata = folder_privatedata;
    }

    /**
     * @return the kcwiki_api_servers
     */
    public String getKcwiki_api_servers() {
        return kcwiki_api_servers;
    }

    /**
     * @param kcwiki_api_servers the kcwiki_api_servers to set
     */
    public void setKcwiki_api_servers(String kcwiki_api_servers) {
        this.kcwiki_api_servers = kcwiki_api_servers;
    }

    /**
     * @return the folder_download
     */
    public String getFolder_download() {
        return folder_download;
    }

    /**
     * @param folder_download the folder_download to set
     */
    public void setFolder_download(String folder_download) {
        this.folder_download = folder_download;
    }

    /**
     * @return the kcserver_account_username
     */
    public String getKcserver_account_username() {
        return kcserver_account_username;
    }

    /**
     * @param kcserver_account_username the kcserver_account_username to set
     */
    public void setKcserver_account_username(String kcserver_account_username) {
        this.kcserver_account_username = kcserver_account_username;
    }

    /**
     * @return the kcserver_account_password
     */
    public String getKcserver_account_password() {
        return kcserver_account_password;
    }

    /**
     * @param kcserver_account_password the kcserver_account_password to set
     */
    public void setKcserver_account_password(String kcserver_account_password) {
        this.kcserver_account_password = kcserver_account_password;
    }

    /**
     * @return the folder_template
     */
    public String getFolder_template() {
        return folder_template;
    }

    /**
     * @param folder_template the folder_template to set
     */
    public void setFolder_template(String folder_template) {
        this.folder_template = folder_template;
    }

    /**
     * @return the application_superuser_username
     */
    public String getApplication_superuser_username() {
        return application_superuser_username;
    }

    /**
     * @param application_superuser_username the application_superuser_username to set
     */
    public void setApplication_superuser_username(String application_superuser_username) {
        this.application_superuser_username = application_superuser_username;
    }

    /**
     * @return the application_superuser_password
     */
    public String getApplication_superuser_password() {
        return application_superuser_password;
    }

    /**
     * @param application_superuser_password the application_superuser_password to set
     */
    public void setApplication_superuser_password(String application_superuser_password) {
        this.application_superuser_password = application_superuser_password;
    }

    /**
     * @return the file_filelist
     */
    public String getFile_filelist() {
        return file_filelist;
    }

    /**
     * @param file_filelist the file_filelist to set
     */
    public void setFile_filelist(String file_filelist) {
        this.file_filelist = file_filelist;
    }

    /**
     * @return the folder_publish
     */
    public String getFolder_publish() {
        return folder_publish;
    }

    /**
     * @param folder_publish the folder_publish to set
     */
    public void setFolder_publish(String folder_publish) {
        this.folder_publish = folder_publish;
    }

    /**
     * @return the folder_workspace
     */
    public String getFolder_workspace() {
        return folder_workspace;
    }

    /**
     * @param folder_workspace the folder_workspace to set
     */
    public void setFolder_workspace(String folder_workspace) {
        this.folder_workspace = folder_workspace;
    }

    /**
     * @return the folder_storage
     */
    public String getFolder_storage() {
        return folder_storage;
    }

    /**
     * @param folder_storage the folder_storage to set
     */
    public void setFolder_storage(String folder_storage) {
        this.folder_storage = folder_storage;
    }

    /**
     * @return the allow_use_proxy
     */
    public boolean isAllow_use_proxy() {
        return allow_use_proxy;
    }

    /**
     * @param allow_use_proxy the allow_use_proxy to set
     */
    public void setAllow_use_proxy(boolean allow_use_proxy) {
        this.allow_use_proxy = allow_use_proxy;
    }

    /**
     * @return the kcserver_host
     */
    public String getKcserver_host() {
        return kcserver_host;
    }

    /**
     * @param kcserver_host the kcserver_host to set
     */
    public void setKcserver_host(String kcserver_host) {
        this.kcserver_host = kcserver_host;
    }

    /**
     * @return the folder_webroot
     */
    public String getFolder_webroot() {
        return folder_webroot;
    }

    /**
     * @param folder_webroot the folder_webroot to set
     */
    public void setFolder_webroot(String folder_webroot) {
        this.folder_webroot = folder_webroot;
    }

    /**
     * @return the system_user_dir
     */
    public String getSystem_user_dir() {
        return system_user_dir;
    }

    /**
     * @param system_user_dir the system_user_dir to set
     */
    public void setSystem_user_dir(String system_user_dir) {
        this.system_user_dir = system_user_dir;
    }

}
