package net.sg.tiger.m2469215.newsfeedtiger;

/**
 * Created by M2469215 on 30/05/2017.
 */

public class M_News {
    /**
     * Article's title
     */
    private String title;

    /**
     * Publication date
     */
    private String publication;

    /**
     * English body of the article
     */
    private String body;

    /**
     * Link to the article on the web
     */
    private String link;


    /**
     * Complete constructor for an article
     * @param title Article's title
     * @param publication Article's publication date
     * @param body Article's body
     * @param link Article's link
     */
    public M_News(String title, String publication, String body, String link) {
        this.title = title;
        this.publication = publication;
        this.body = body;
        this.link = link;
    }

    /**
     * Constructor for an article without title
     * @param publication Article's publication date
     * @param body Article's body
     * @param link Article's link
     */
    public M_News(String publication, String body, String link) {
        this.publication = publication;
        this.body = body;
        this.link = link;
        this.title = "";
    }

    /**
     * Getter for the title
     * @return Article's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the publication's date (format mm/dd/yyyy)
     * @return Article's publication date
     */
    public String getPublication() {
        return publication;
    }

    /**
     * Getter for the article's body
     * @return Article's body
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter for the article's link
     * @return Article's link
     */
    public String getLink() {
        return link;
    }
}
