package br.com.developer.allefsousa.adorofilmes.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrailerDetalhes implements Serializable {


        @SerializedName("id")
        private String id;
        @SerializedName("iso_639_1")
        private String iso6391;
        @SerializedName("iso_3166_1")
        private String iso31661;
        @SerializedName("key")
        private String key;
        @SerializedName("name")
        private String name;
        @SerializedName("site")
        private String site;
        @SerializedName("size")
        private Integer size;
        @SerializedName("type")
        private String type;
        private final static long serialVersionUID = 8963071909933511406L;

        /**
         * No args constructor for use in serialization
         *
         */
        public TrailerDetalhes() {
        }

        /**
         *
         * @param site
         * @param iso6391
         * @param id
         * @param iso31661
         * @param name
         * @param type
         * @param key
         * @param size
         */
        public TrailerDetalhes(String id, String iso6391, String iso31661, String key, String name, String site, Integer size, String type) {
            this.id = id;
            this.iso6391 = iso6391;
            this.iso31661 = iso31661;
            this.key = key;
            this.name = name;
            this.site = site;
            this.size = size;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso6391() {
            return iso6391;
        }

        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }

        public String getIso31661() {
            return iso31661;
        }

        public void setIso31661(String iso31661) {
            this.iso31661 = iso31661;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

