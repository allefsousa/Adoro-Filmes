package br.com.developer.allefsousa.adorofilmes.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Trailer implements Serializable
{

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<TrailerDetalhes> trailerDetalhesList = null;
    private final static long serialVersionUID = 3353269686734377709L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Trailer() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public Trailer(Integer id, List<TrailerDetalhes> results) {
        this.id = id;
        this.trailerDetalhesList = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailerDetalhes> getResults() {
        return trailerDetalhesList;
    }

    public void setResults(List<TrailerDetalhes> results) {
        this.trailerDetalhesList = results;
    }

}
