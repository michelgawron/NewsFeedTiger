package net.sg.tiger.m2469215.newsfeedtiger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model representing the data we use
 * Created by M2469215 on 30/05/2017.
 */

public class M_News implements Parcelable {
    //region Variables
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
    //endregion

    //region Constructors

    /**
     * Complete constructor for an article
     *
     * @param title       Article's title
     * @param publication Article's publication date
     * @param body        Article's body
     * @param link        Article's link
     */
    public M_News(String title, String publication, String body, String link) {
        this.title = title;
        this.publication = publication;
        this.body = body;
        this.link = link;
    }

    /**
     * Constructor called when creating the object from a Parcel
     *
     * @param in Parcel giving the data
     */
    public M_News(Parcel in) {
        readFromParcel(in);
    }
    //endregion

    //region Getters/Setters

    /**
     * Getter for the title
     *
     * @return Article's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the publication's date (format mm/dd/yyyy)
     *
     * @return Article's publication date
     */
    public String getPublication() {
        return publication;
    }

    /**
     * Getter for the article's body
     *
     * @return Article's body
     */
    public String getBody() {
        return body;
    }

    /**
     * Getter for the article's link
     *
     * @return Article's link
     */
    public String getLink() {
        return link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writing objects to the parcel
     *
     * @param dest  Parcel in which we need to write
     * @param flags Flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.publication);
    }

    /**
     * Function used when constructing an object from a Parcel
     *
     * @param in
     */
    private void readFromParcel(Parcel in) {
        this.body = in.readString();
        this.title = in.readString();
        this.link = in.readString();
        this.publication = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        /**
         * Method called when reading Parcellable data from an intent
         * @param source Parcel source
         * @return An instance of {M_News}
         */
        @Override
        public M_News createFromParcel(Parcel source) {
            return new M_News(source);
        }

        /**
         * Method called when reading an array of Parcellable data
         * @param size Siez of the array
         * @return An array of {M_News}
         */
        @Override
        public M_News[] newArray(int size) {
            return new M_News[size];
        }
    };
    //endregion
}
