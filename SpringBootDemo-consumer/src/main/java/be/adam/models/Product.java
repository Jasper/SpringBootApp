package be.adam.models;

import javax.persistence.*;

    @Entity
    @Table(name = "products")
    public class Product {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long id;
        private String name;
        private String description;
        private long price;
        private long stock;

        public Product(){

        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public long getStock() {
            return stock;
        }

        public void setStock(long stock) {
            this.stock = stock;
        }
    }

