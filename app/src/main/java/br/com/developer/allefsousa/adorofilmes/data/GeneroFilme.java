package br.com.developer.allefsousa.adorofilmes.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by allef on 16/08/2018.
 */

public class GeneroFilme implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = -4634536975063899374L;


    public GeneroFilme(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public GeneroFilme() {
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

}
