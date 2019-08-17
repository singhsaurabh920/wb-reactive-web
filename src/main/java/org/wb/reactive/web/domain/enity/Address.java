package org.wb.reactive.web.domain.enity;

import java.util.stream.Stream;

public class Address {

     private String street;
     private String suite;
     private String city;
     private String state;
     private String zipcode;
     private String country;
     private Geo geo;

     public String getStreet() {
          return street;
     }

     public void setStreet(String street) {
          this.street = street;
     }

     public String getSuite() {
          return suite;
     }

     public void setSuite(String suite) {
          this.suite = suite;
     }

     public String getCity() {
          return city;
     }

     public void setCity(String city) {
          this.city = city;
     }

     public String getState() {
          return state;
     }

     public void setState(String state) {
          this.state = state;
     }

     public String getZipcode() {
          return zipcode;
     }

     public void setZipcode(String zipcode) {
          this.zipcode = zipcode;
     }

     public String getCountry() {
          return country;
     }

     public void setCountry(String country) {
          this.country = country;
     }

     public Geo getGeo() {
          return geo;
     }

     public void setGeo(Geo geo) {
          this.geo = geo;
     }
}
