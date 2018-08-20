package br.com.developer.allefsousa.adorofilmes.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by allef on 17/08/2018.
 */

public class Season implements Serializable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private Object posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    private final static long serialVersionUID = 7703379268378302253L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Season() {
    }

    /**
     *
     * @param id
     * @param airDate
     * @param overview
     * @param posterPath
     * @param name
     * @param episodeCount
     * @param seasonNumber
     */
    public Season(String airDate, Integer episodeCount, Integer id, String name, String overview, Object posterPath, Integer seasonNumber) {
        super();
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Object getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(Object posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

}